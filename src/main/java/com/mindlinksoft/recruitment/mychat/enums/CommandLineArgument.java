package com.mindlinksoft.recruitment.mychat.enums;

public enum CommandLineArgument {

	INPUT_FILE("Input file", 'i'),

	OUTPUT_FILE("Output file", 'o'),

	FILTER_USER("Filter by user", 'u', false),

	FILTER_KEYWORD("Filter by keyword", 'k', false),

	BLACKLIST("Blacklisted words", 'b', false),

	HIDE_CREDIT_CARD_AND_PHONE("Hide credit cards and phone numbers", 'h', false, false),

	OBFUSCATE_SENDER("Obfuscate sender id", 's', false, false),

	;

	private final Character char_;
	private final boolean valueRequired_;
	private final String description_;
	private final boolean required_;

	CommandLineArgument(String description, char character) {
		this(description, character, true, true);
	}

	CommandLineArgument(String description, char character, boolean required) {
		this(description, character, required, true);
	}

	CommandLineArgument(String description, char character, boolean required, boolean valueRequired) {
		description_ = description;
		required_ = required;
		valueRequired_ = valueRequired;
		char_ = character;
	}

	public Character getChar() {
		return char_;
	}

	public boolean isRequired() {
		return required_;
	}

	public boolean isValueRequired() {
		return valueRequired_;
	}

	public String getDescription() {
		return description_;
	}

	@Override
	public String toString() {
		return description_;
	}

}
