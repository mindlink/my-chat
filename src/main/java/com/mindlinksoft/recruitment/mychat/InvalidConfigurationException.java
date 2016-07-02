package com.mindlinksoft.recruitment.mychat;

/**
 * Exception thrown when the configuration of the CLI application is invalid.*/
class InvalidConfigurationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3338754525121760586L;

	public InvalidConfigurationException() {
		super();
	}
	
	public InvalidConfigurationException(String string) {
		super(string);
	}
}
