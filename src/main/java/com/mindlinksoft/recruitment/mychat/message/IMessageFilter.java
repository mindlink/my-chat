package com.mindlinksoft.recruitment.mychat.message;

/**
 * Interface for a Message Filter which indicates if a given message should
 * be filtered or not.
 * 
 */
public interface IMessageFilter {
	
	/**
	 * Indicates if the given {@link IMessage} should be filtered.
	 * @param message
	 * @return {@link boolean} indicating if the message should be filtered.
	 */
	boolean filterMessage(IMessage message);
	
}
