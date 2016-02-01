package com.mindlinksoft.recruitment.mychat;

import java.time.Instant;

/**
 * Represents a chat message.
 */
public final class Message {
  /**
   * The message content.
   */
  private String content;

  /**
   * The message timestamp.
   */
  private Instant timestamp;

  /**
   * The message sender.
   */
  private String senderId;

  /**
   * Initializes a new instance of the {@link Message} class.
   *
   * @param timestamp The timestamp at which the message was sent.
   * @param senderId  The ID of the sender.
   * @param content   The message content.
   */
  public Message(Instant timestamp, String senderId, String content) {
    this.content = content;
    this.timestamp = timestamp;
    this.senderId = senderId;
  }

  /**
   * Getter method for the message content.
   *
   * @return The message content.
   */
  public String getContent() {
    return content;
  }

  /**
   * Getter method for the message timestamp.
   *
   * @return The message timestamp.
   */
  public Instant getTimestamp() {
    return timestamp;
  }

  /**
   * Getter method for the message sender ID.
   *
   * @return The message sender.
   */
  public String getSenderId() {
    return senderId;
  }
}
