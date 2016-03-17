package com.mindlinksoft.recruitment.mychat.models;

/**
 * Enumeration to hold the different configuration options.
 */
public enum ConfigurationOption {
	
	/** Get help about the configuration options available. */
	HELP("help"),
	/** Input file path. */
	INPUT("i"),
	/** Output file path. */
	OUTPUT("o"),
	/** Only return messages sent by this user. */
	USER("u"),
	/** Only return messages with this keyword in it. */
	KEYWORD("k"),
	/** Redact the following words from the conversation. */
	BLACKLIST("bl");
	
	private String value;
	
	/**
	 * Creates a new instance of {@link ConfigurationOption}.
	 */
	private ConfigurationOption(String value) {
		this.value = value;
	}

	/**
	 * Gets the string value associated with the configuration option.
	 * 
	 * @return The {@link String} associated with the configuration option.
	 */
	public String getValue() {
		return value;
	}
}
