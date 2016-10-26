package br.com.tivit.turnflowlog.model;

public class Job {
	
	/**
	 * Job's name
	 */
	private String jobName;
	
	/**
	 * Job's error
	 */
	private String jobData;
	
	/**
	 * Job's workflow
	 */
	private String jobWorkflow;

	/**
	 * File's path
	 */
	private String jobOutputPath;

	/**
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}

	/**
	 * @param jobName the jobName to set
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	/**
	 * @return the jobData
	 */
	public String getJobData() {
		return jobData;
	}

	/**
	 * @param jobData the jobData to set
	 */
	public void setJobData(String jobData) {
		this.jobData = jobData;
	}

	/**
	 * @return the jobWorkflow
	 */
	public String getJobWorkflow() {
		return jobWorkflow;
	}

	/**
	 * @param jobWorkflow the jobWorkflow to set
	 */
	public void setJobWorkflow(String jobWorkflow) {
		this.jobWorkflow = jobWorkflow;
	}

	/**
	 * @return the jobOutputPath
	 */
	public String getJobOutputPath() {
		return jobOutputPath;
	}

	/**
	 * @param jobOutputPath the jobOutputPath to set
	 */
	public void setJobOutputPath(String jobOutputPath) {
		this.jobOutputPath = jobOutputPath;
	} 
	
	
	public String toAppend(){
		return getJobName() + " | " 
				+ getJobData() + " | " 
				+ getJobWorkflow() + " | " 
				+ getJobOutputPath() + " ; ";
	}

}
