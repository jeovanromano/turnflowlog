package br.com.tivit.turnflowlog.control;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadFile {

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
	// String path = "logs/HM_DI_EC_CLR_MRLD_IP2916_output_20161021213400_00001.txt";
	public static String getContentFile(Charset charset, File path) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get(path.getPath()), charset);
		String outputFile = path.getName();
		String jobNameSplit = (outputFile.split("_output_")[0]);
		System.out.println(jobNameSplit);

		String dateTimeAbend = null;
		String workflowAbend = null;

		
		for (int i = lines.size() - 1; i > 0; i--) { 
			String[] listWords = lines.get(i).split(" ");
			
			// Verifica data e hora do erro
			if(dateTimeAbend == null){
				if (Arrays.asList(listWords).contains(TokenWordEnum.COMPLETED.keyValue) && Arrays.asList(listWords).contains(TokenWordEnum.AT.keyValue)) {
					dateTimeAbend = lines.get(i).split(TokenWordEnum.COMPLETED_AT.toString())[1].replaceAll("^\\s+", "");
					System.out.println(dateTimeAbend);
					continue;					
				}
				
				if (Arrays.asList(listWords).contains(TokenWordEnum.FINAL.keyValue) && Arrays.asList(listWords).contains(TokenWordEnum.EXECUCAO.keyValue)) {
					dateTimeAbend = lines.get(i).split(TokenWordEnum.FINAL_EXECUCAO.toString())[1].replaceAll("^\\s+", "");
					System.out.println(dateTimeAbend);
					continue;					
				}
			}
			
			// Verifica workflow
			if(workflowAbend == null){
				if (Arrays.asList(listWords).contains(TokenWordEnum.ERROR.keyValue) && Arrays.asList(listWords).contains(TokenWordEnum.WORKFLOW.keyValue)) {
					String line = lines.get(i);
					
					String[] wordSplits = line.split(" ");
					for (String word : wordSplits) {
						if(word.startsWith("[") && workflowAbend == null){
							workflowAbend = word.substring(word.indexOf("[") + 1, word.indexOf("]"));
							System.out.println(workflowAbend);
							continue;
						}
					}	
				}
				
				if (Arrays.asList(listWords).contains(TokenWordEnum.WAITING.keyValue) && Arrays.asList(listWords).contains(TokenWordEnum.TO.keyValue) && 
						Arrays.asList(listWords).contains(TokenWordEnum.COMPLETE.keyValue)) {
					
					String line = lines.get(i);
					
					String[] wordSplits = line.split(" ");
					for (String word : wordSplits) {
						if(word.startsWith("[") && workflowAbend == null){
							workflowAbend = word.substring(word.indexOf("[") + 1, word.indexOf("]"));
							System.out.println(workflowAbend);
							continue;
						}
					}	
					
//					workflowAbend = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
//					
//					System.out.println(workflowAbend);
//					continue;					
				}
			}
		}
		return null;
	}
	
	
	
	
	public static void main(String[] args) {

		ReadFile rf = new ReadFile();
		
		// Caminho por parâmetro
		List<File> jobs = rf.listFilesForFolder(new File("logs"));

		Charset charset = Charset.forName("US-ASCII");
		//String path = "logs/HM_DI_EC_CLR_MRLD_IP2916_output_20161021213400_00001.txt";
		//String path = "logs/HM_DI_EI_FUN_RCCO_IL0341_output_20161020114300_00001.txt";
		//String pathOther = "logs/HM_DI_EI_CLR_IDIN_IS0001_output_20161020133800_00001.txt";

		try {
			for (File path : jobs) {
				String a = getContentFile(charset, path);
			}
			
//			getContentFile(charset, pathOther);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("----");
	}

}
