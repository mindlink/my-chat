package com.mindlinksoft.recruitment.mychat;

/**
 * Exception thrown whenever the CLI command to run the exporter on a set 
 * of parameters is invalid.*/
public class MalformedOptionalCLIParameterException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5789420747354804293L;

	public MalformedOptionalCLIParameterException() {
		super();
	}
	
	public MalformedOptionalCLIParameterException(String message) {
		super(message);
	}
}
