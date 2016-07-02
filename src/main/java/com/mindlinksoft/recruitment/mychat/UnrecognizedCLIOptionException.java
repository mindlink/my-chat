package com.mindlinksoft.recruitment.mychat;

/**
 * Exception thrown when an additional input from the command line is not 
 * recognized.*/
public class UnrecognizedCLIOptionException extends MalformedOptionalCLIParameterException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1526993552600731677L;

	public UnrecognizedCLIOptionException() {
		super();
	}
	
	public UnrecognizedCLIOptionException(String message) {
		super(message);
	}
}
