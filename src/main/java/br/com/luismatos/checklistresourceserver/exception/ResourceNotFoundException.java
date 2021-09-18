package br.com.luismatos.checklistresourceserver.exception;

public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3323262064384239546L;

	public ResourceNotFoundException(String message) {
		super(message);
	}

}
