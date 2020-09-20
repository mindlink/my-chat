package com.mindlinksoft.recruitment.mychat.exceptions;

/**
 * Custom  exception for incorrect flags or arguments
 * 
 * @author Mohamed Yusuf
 */
@SuppressWarnings("serial")
public class IllegalFlagException extends Exception {
	public IllegalFlagException(String errorMessage) {
		super(errorMessage);
	}
}
