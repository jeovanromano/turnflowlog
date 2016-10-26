package br.com.tivit.turnflowlog.control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.tivit.turnflowlog.model.Job;

public class ReadFile {
	
	private static final Charset charset = Charset.forName("US-ASCII");

	// Listar arquivos do diretório.
	public List<File> listFilesForFolder(final File folder) {
		List<File> outputFile = new ArrayList<>();

		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				outputFile.add(fileEntry);
				System.out.println(fileEntry.getName());
			}
		}
		System.out.println("-----");
		return outputFile;
	}

	// Charset charset = Charset.forName("US-ASCII");
	// String path =
	// "logs/HM_DI_EC_CLR_MRLD_IP2916_output_20161021213400_00001.txt";
	public static Job getContentFile(Charset charset, File path) throws IOException {

		Job job = new Job();

		job.setJobOutputPath(path.getAbsolutePath());

		List<String> lines = Files.readAllLines(Paths.get(path.getPath()), charset);
		String outputFile = path.getName();
		String jobNameSplit = (outputFile.split("_output_")[0]);

		job.setJobName(jobNameSplit);
		System.out.println(jobNameSplit);

		String dateTimeAbend = null;
		String workflowAbend = null;

		for (int i = lines.size() - 1; i > 0; i--) {
			String[] listWords = lines.get(i).split(" ");

			// Verifica data e hora do erro
			if (dateTimeAbend == null) {
				// verifica se na linha existe "Completed at" para capturar a
				// data e hora
				if (Arrays.asList(listWords).contains(TokenWordEnum.COMPLETED.getToken)
						&& Arrays.asList(listWords).contains(TokenWordEnum.AT.getToken)) {

					dateTimeAbend = lines.get(i).split(TokenWordEnum.COMPLETED_AT.getToken)[1].replaceAll("^\\s+", "");
					job.setJobData(dateTimeAbend);
					System.out.println(dateTimeAbend);
					continue;
				}
				// Verifica se na linha existe "Final Execução" para capturar a
				// data e hora
				if (Arrays.asList(listWords).contains(TokenWordEnum.FINAL.getToken)
						&& Arrays.asList(listWords).contains(TokenWordEnum.EXECUCAO.getToken)) {

					dateTimeAbend = lines.get(i).split(TokenWordEnum.FINAL_EXECUCAO.getToken)[1].replaceAll("^\\s+",
							"");
					job.setJobData(dateTimeAbend);
					System.out.println(dateTimeAbend);
					continue;
				}
			}

			// Verifica workflow
			if (workflowAbend == null) {
				// Verifica se na linha exite "Error: Workflow" para capturar o
				// Workflow
				if (Arrays.asList(listWords).contains(TokenWordEnum.ERROR.getToken)
						&& Arrays.asList(listWords).contains(TokenWordEnum.WORKFLOW.getToken)) {
					String line = lines.get(i);
					String[] wordSplits = line.split(" ");
					for (String word : wordSplits) {
						if (word.startsWith("[") && (word.endsWith("]") || word.endsWith(":"))) {
							// Captura o nome WF entre os colchetes
							workflowAbend = word.substring(word.indexOf("[") + 1, word.indexOf("]"));
							job.setJobWorkflow(workflowAbend);
							System.out.println(workflowAbend);
							break;
						}
					}
				}

				// Verifica se a linha existe "Waiting to complete" para
				// capturar o Workflow
				if (Arrays.asList(listWords).contains(TokenWordEnum.WAITING.getToken)
						&& Arrays.asList(listWords).contains(TokenWordEnum.TO.getToken)
						&& Arrays.asList(listWords).contains(TokenWordEnum.COMPLETE.getToken)) {

					String line = lines.get(i);

					String[] wordSplits = line.split(" ");
					for (String word : wordSplits) {
						if (word.startsWith("[") && workflowAbend == null) {
							workflowAbend = word.substring(word.indexOf("[") + 1, word.indexOf("]"));
							job.setJobWorkflow(workflowAbend);
							System.out.println(workflowAbend);
							continue;
						}
					}
				}
			}
		}
		return job;
	}

	public static void main(String[] args) {

		ReadFile rf = new ReadFile();

		// Caminho por parâmetro
		List<File> jobsFiles = rf.listFilesForFolder(new File("logs"));
		String dir = Paths.get(jobsFiles.get(0).getAbsolutePath()).getParent().toString() + "\\";
		File file = new File(dir + "Jobs.txt");
		
		try {
			List<Job> jobs = new ArrayList<>();
			for (File path : jobsFiles) {
				jobs.add(getContentFile(charset, path));
			}
			writeFile(file, jobs);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param file
	 * @param jobs
	 */
	private static void writeFile(File file, List<Job> jobs) {
		for (Job job : jobs) {
			System.out.println("\n");
			System.out.println("--------- RESUMO DO JOB ---------");
			System.out.println("Nome do Job:        " + job.getJobName());
			System.out.println("Data e Hora do Job: " + job.getJobData());
			System.out.println("Workflow do Job:    " + job.getJobWorkflow());
			System.out.println("Caminho do arquivo: " + job.getJobOutputPath());
			System.out.println("-------------- FIM --------------");
			System.out.println("\n");

			try {
				if (!file.exists()) {
					System.out.println("Criando arquivo");
					file.createNewFile();
				}
				
				List<String> lines = Files.readAllLines(Paths.get(file.getPath()), charset);
				if(lines != null){
					for (String line : lines) {
//						for (Job jobNew : jobs) {
//							if (jobNew.getJobName().equals(Converts.toObjJob(line).getJobName())){
//								jobs.remove(jobNew);
//							}
//						}
						if(jobs.contains(Converts.toObjJob(line))){
							jobs.remove(Converts.toObjJob(line));
						}
					}
					
					
				}
				
				PrintWriter out = new PrintWriter(new FileWriter(file, true));
				out.append(job.toAppend() + "\n");
				out.close();
			} catch (IOException e) {
				System.out.println("Não foi gerado LOG");
			}
		}
	}

}
