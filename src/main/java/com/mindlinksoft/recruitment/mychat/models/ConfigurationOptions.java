package com.mindlinksoft.recruitment.mychat.models;

/**
 * Structure to hold the different configuration option {@link String}s.
 */
public enum ConfigurationOptions {
	HELP("help"),
	INPUT("i"),
	OUTPUT("o"),
	USER("u"),
	KEYWORD("k"),
	BLACKLIST("bl");
	
	private String value;
	
	private ConfigurationOptions(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
