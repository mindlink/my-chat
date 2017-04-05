package com.mindlinksoft.recruitment.mychat.message;


/**
 * Interface for a Message formatter which performs formatting operations
 * on an {@link IMessage}. 
 *
 */
public interface IMessageFormatter {

	/**
	 * Formats a {@link IMessage}.
	 * @param message
	 * @return Formatted message.
	 */
	IMessage format(IMessage message);
	
}
