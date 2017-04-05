package com.mindlinksoft.recruitment.mychat.message;

import java.time.Instant;

/**
 * Interface for a chat message
 */
public interface IMessage {
	
	/**
	 * Gets the message timestamp.
	 * @return Message timestamp.
	 */
	Instant getTimestamp();
	
	/**
	 * Gets the sender Id
	 * @return Sender Id
	 */
	String getSenderId();
	
	/**
	 * Sets the sender Id
	 * @param senderId
	 */
	void setSenderId(String senderId);
	
	/**
	 * Gets the message content.
	 * @return Message content.
	 */
	String getContent();
	
	
	/**
	 * Sets the content of the message.
	 * @param contents
	 */
	void setContent(String content);
}