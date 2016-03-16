package com.mindlinksoft.recruitment.mychat.exceptions;

/**
 * Exception to occur when there was a problem reading a conversation.
 */
public class ReadConversationException extends RuntimeException {

	private static final long serialVersionUID = -2791310748369688181L;

	public ReadConversationException() {		
	}
	
	public ReadConversationException(String message) {
		super(message);
	}
	
	public ReadConversationException(Throwable cause) {
		super(cause);
	}
	
	public ReadConversationException(String message, Throwable cause) {
		super(message, cause);
	}
}
