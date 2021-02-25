package com.mindlinksoft.recruitment.mychat;

import java.time.Instant;

/**
 * Represents a chat message.
 */
public final class Message {

    //Note - I've made these properties final as I believe that there should be no reason to change.

    /**
     * The message content.
     */
    public final String content;

    /**
     * The message timestamp.
     */
    public final Instant timestamp;

    /**
     * The message sender.
     */
    public final String senderId;

    /**
     * Initializes a new instance of the {@link Message} class.
     * @param timestamp The timestamp at which the message was sent.
     * @param senderId The ID of the sender.
     * @param content The message content.
     */
    public Message(Instant timestamp, String senderId, String content) {
        this.content = content;
        this.timestamp = timestamp;
        this.senderId = senderId;
    }

	public boolean contains(String keyword) {
		return content.contains(keyword);
	}
}
