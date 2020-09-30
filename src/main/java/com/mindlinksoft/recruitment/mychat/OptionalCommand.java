package com.mindlinksoft.recruitment.mychat;

/**
 * Contains all valid command line options
 */
public enum OptionalCommand {
	FilterByUser("u"),
	FilterByKeyword("k"),
	HideNumbers("h"),
	HideBlackListWords("b"),
	ObfuscateUsernames("o");

	
	private String cmdLabel;
	OptionalCommand(String cmd) {
		cmdLabel = cmd;
	}
	
	public String opt() {
		return cmdLabel;
	}
	
}
