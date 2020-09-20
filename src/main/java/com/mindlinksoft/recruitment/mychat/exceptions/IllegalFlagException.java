package com.mindlinksoft.recruitment.mychat.exceptions;

/**
 * Custom  exception for incorrect flags or arguments
 * 
 * @author Mohamed Yusuf
 */
@SuppressWarnings("serial")
public class IllegalFlagException extends Exception {
	
	public IllegalFlagException() {
		super();
	}
	
	public IllegalFlagException(String errorMessage) {
		super(errorMessage);
	}
	
	public IllegalFlagException(Throwable error) {
		super(error);
	}
	
	public IllegalFlagException(String errorMessage, Throwable error) {
		super(errorMessage, error);
	}
}
