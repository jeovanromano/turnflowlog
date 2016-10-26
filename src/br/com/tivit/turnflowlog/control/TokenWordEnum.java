package br.com.tivit.turnflowlog.control;

public enum TokenWordEnum {
	/**
	 * Palavra chave para buscar data e hora
	 * Ex: "Completed" at Thu Oct 20 21:36:44 2016
	 */
	COMPLETED("Completed"),
	
	/**
	 * Palavra chave para buscar data e hora
	 * Ex: Completed "at" Thu Oct 20 21:36:44 2016
	 */
	AT("at"),
	
	/**
	 * Completed at
	 */
	COMPLETED_AT("Completed at"),
	
	/**
	 * Palavra chave para buscar data e hora.
	 * Ex: Final de Execucao :  20/10/2016 11:43:41
	 */
	FINAL("Final"),
	
	/**
	 * Palavra chave para buscar data e hora.
	 * Ex: Final de "Execucao" :  20/10/2016 11:43:41
	 */
	EXECUCAO("Execucao"),
	
	/**
	 * Final de Execucao :
	 */
	FINAL_EXECUCAO("Final de Execucao :"),
	
	/**
	 * Palavra chave para buscar o workflow
	 * Ex: ERROR: Workflow [wf_CIELO_COR_CLR_OUTGOING_REL_RECONC_MAESTRO]
	 */
	ERROR("ERROR:"),
	
	/**
	 * Palavra chave para buscar o workflow
	 * Ex: ERROR: "Workflow" [wf_CIELO_COR_CLR_OUTGOING_REL_RECONC_MAESTRO]
	 */
	WORKFLOW("Workflow"),
	
	/**
	 * Palavra chave para buscar o workflow
	 * Ex: ERROR: "Workflow" [wf_CIELO_COR_CLR_OUTGOING_REL_RECONC_MAESTRO]
	 */
	WAITING("Waiting"),
	
	/**
	 * Palavra chave para buscar o workflow
	 * Ex: ERROR: "Workflow" [wf_CIELO_COR_CLR_OUTGOING_REL_RECONC_MAESTRO]
	 */
	TO("to"),
	
	/**
	 * Palavra chave para buscar o workflow
	 * Ex: ERROR: "Workflow" [wf_CIELO_COR_CLR_OUTGOING_REL_RECONC_MAESTRO]
	 */
	COMPLETE("complete");
	
	
	public String getToken;

	TokenWordEnum(String value) {
		getToken = value;
	}

}
