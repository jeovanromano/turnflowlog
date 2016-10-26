/**
 * 
 */
package br.com.tivit.turnflowlog.control;

import br.com.tivit.turnflowlog.model.Job;

/**
 * @author Jeovan Romano
 *
 */
public class Converts {

	public static Job toObjJob(String lineKeywords) {

		String[] lines = lineKeywords.replace(";", "").split(" \\| ");
		Job job = new Job();
		job.setJobName(lines[0]);
		job.setJobData(lines[1]);
		job.setJobWorkflow(lines[2]);
		job.setJobOutputPath(lines[3]);
		return job;
	}

}
