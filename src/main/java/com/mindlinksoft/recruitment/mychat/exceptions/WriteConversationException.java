package com.mindlinksoft.recruitment.mychat.exceptions;

/**
 * Exception to occur when there was a problem writing a conversation.
 */
public class WriteConversationException extends RuntimeException {

	private static final long serialVersionUID = 5550237157649893353L;
	
	public WriteConversationException() {		
	}
	
	public WriteConversationException(String message) {
		super(message);
	}
	
	public WriteConversationException(Throwable cause) {
		super(cause);
	}
	
	public WriteConversationException(String message, Throwable cause) {
		super(message, cause);
	}
}
