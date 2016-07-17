package com.mindlinksoft.recruitment.mychat.model;

/**
 * An exception to denote an invalid message input
 */
public class InvalidMessageException extends RuntimeException {

	public InvalidMessageException(String input) {
		super("Invalid Message " + input);
	}
}
