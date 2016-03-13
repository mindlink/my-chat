package com.mindlinksoft.recruitment.mychat;

/**
 * Custom class to throw if there are illegal number of command line arguments
 * @author Jimmy
 *
 */
public class InvalidArgumentsException extends Exception {
	  public InvalidArgumentsException() { super(); }
	  public InvalidArgumentsException(String message) { super(message); }
	  public InvalidArgumentsException(String message, Throwable cause) { super(message, cause); }
	  public InvalidArgumentsException(Throwable cause) { super(cause); }
}
