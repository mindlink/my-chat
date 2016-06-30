package com.mindlinksoft.recruitment.mychat;

class ConversationFilterFactory {

	static ConversationFilter createFilter(char option, String value) 
										throws UnrecognizedCLIOptionException {
		switch(option) {
		case Options.FILTER_KEYWORD:
			return new FilterKeyword(value);
		case Options.FILTER_USERNAME:
			return new FilterUsername(value);
		default:
			throw new UnrecognizedCLIOptionException("Additional CLI parameter "
					+ "not recognized: '" + option + "'");
		}
		
	}
	
	static ConversationFilter createFilter(char option, String[] value) 
										throws UnrecognizedCLIOptionException {
		
		switch(option) {
		case Options.FILTER_BLACKLIST:
			return new FilterBlacklist(value);
		default:
			throw new UnrecognizedCLIOptionException("Additional CLI parameter "
					+ "not recognized: '" + option + "'");

		}
		
	}
	
	static ConversationFilter createFilter(char option) {
		return null;
		
	}
}
