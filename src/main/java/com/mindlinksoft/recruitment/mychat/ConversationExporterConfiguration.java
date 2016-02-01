package com.mindlinksoft.recruitment.mychat;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the configuration for the exporter.
 */
public final class ConversationExporterConfiguration {

  private Map<String, String> configuration = new HashMap<>();

  /**
   * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
   *
   * @param configuration  The configuration defined by the list of arguments.
   */
  public ConversationExporterConfiguration(Map configuration) {
    this.configuration = configuration;
  }

  //region Getters

  /**
   * Getter method for the input file path
   *
   * @return The instance's configuration parameter for the input file path
   */
  public String getInputFilePath() {
    return configuration.get("-i");
  }

  /**
   * Getter method for the output file path
   *
   * @return The instance's configuration parameter for the output file path
   */
  public String getOutputFilePath() {
    return configuration.get("-o");
  }

  /**
   * Getter method for the excluded sender ID from the conversation
   *
   * @return The instance's configuration parameter for the sender ID
   */
  public String getExcludedSenderID() {
    return configuration.get("-x");
  }

  /**
   * Getter method for the keyword used to filter the conversation
   *
   * @return The instance's configuration parameter for the filtering keyword
   */
  public String getFilteringKeyword() {
    return configuration.get("-k");
  }

  /**
   * Getter method for the blacklisted keywords for the conversation
   *
   * @return The instance's configuration parameter for the blacklisted keywords
   */
  public String getBlacklist() {
    return configuration.get("-b");
  }

  /**
   * Getter method for the parameter of a user-defined flag
   *
   * @return The instance's configuration parameter for the user-defined flag
   */
  public String getParameterFromFlag(String flag) {
    return configuration.get(flag);
  }

  //endregion
}
