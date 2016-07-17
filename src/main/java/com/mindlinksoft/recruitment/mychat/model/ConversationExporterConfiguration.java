package com.mindlinksoft.recruitment.mychat.model;

import java.util.HashMap;
import java.util.Map;

import com.mindlinksoft.recruitment.mychat.enums.CommandLineArgument;

/**
 * Represents the configuration for the exporter.
 */
public class ConversationExporterConfiguration {

	private Map<CommandLineArgument, String> map_;

	public ConversationExporterConfiguration(Map<CommandLineArgument, String> map) {
		map_ = map;
	}

	public ConversationExporterConfiguration() {
		map_ = new HashMap<>();
	}

	private String get(CommandLineArgument argument) {
		return map_.get(argument);
	}

	public String getInputFile() {
		return get(CommandLineArgument.INPUT_FILE);
	}

	public String getOutputFile() {
		return get(CommandLineArgument.OUTPUT_FILE);
	}

	public String getFilterByUser() {
		return get(CommandLineArgument.FILTER_USER);
	}

	public String getFilterByKeyword() {
		return get(CommandLineArgument.FILTER_KEYWORD);
	}

	public String getBlacklist() {
		return get(CommandLineArgument.BLACKLIST);
	}

	public boolean isHideCreditCardAndPhone() {
		return get(CommandLineArgument.HIDE_CREDIT_CARD_AND_PHONE) != null;
	}

	public boolean isObfuscateSender() {
		return get(CommandLineArgument.OBFUSCATE_SENDER) != null;
	}

	public int getSize() {
		return map_.size();
	}

	protected void set(CommandLineArgument argument, String value) {
		map_.put(argument, value);
	}

}
