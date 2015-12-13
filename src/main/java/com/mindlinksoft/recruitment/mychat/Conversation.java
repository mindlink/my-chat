package com.mindlinksoft.recruitment.mychat;

import java.util.Collection;
import java.util.HashMap;

/**
 * Represents the model of a conversation.
 */
public final class Conversation {
  /**
   * The name of the conversation.
   */
  private String name;

  /**
   * The messages in the conversation.
   */
  private Collection<Message> messages;

  /**
   * Initializes a new instance of the {@link Conversation} class.
   *
   * @param name     The name of the conversation.
   * @param messages The messages in the conversation.
   */
  public Conversation(String name, Collection<Message> messages) {
    this.name = name;
    this.messages = messages;
  }

  //region Getters

  /**
   * Getter method for the conversation name.
   *
   * @return The instance's conversation name.
   */
  public String getName() {
    return name;
  }

  /**
   * Getter method for the list of messages.
   *
   * @return The instance's list of messages.
   */
  public Collection<Message> getMessages() {
    return messages;
  }
  //endregion
}
