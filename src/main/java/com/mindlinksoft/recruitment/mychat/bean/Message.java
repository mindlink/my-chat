package com.mindlinksoft.recruitment.mychat.bean;

import java.time.Instant;

/**
 * Represents a chat message.
 */
public final class Message {

	private final String content_;
	private final Instant timestamp_;
	private final String senderId_;

	public Message(Instant timestamp, String senderId, String content) {
		content_ = content;
		timestamp_ = timestamp;
		senderId_ = senderId;
	}

	public String getContent() {
		return content_;
	}

	public Instant getTimestamp() {
		return timestamp_;
	}

	public String getSenderId() {
		return senderId_;
	}
}
