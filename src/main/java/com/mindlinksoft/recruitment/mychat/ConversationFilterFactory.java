package com.mindlinksoft.recruitment.mychat;

/**
 * Factory that chooses which concrete conversation filter to create based on 
 * the parameter option and the number of string values passed in.<p>
 * Some concrete filters take a single value, whereas other may take a list of
 * many. Some others may not need any values (flags).*/
class ConversationFilterFactory {

	/**
	 * @return a single valued conversation filter
	 * @param option the option value (defined in {@link Options}) that decides
	 * which single valued concrete filter to instantiate and return
	 * @param value the value of the filter to create*/
	static ConversationFilter createFilter(String option, String value) 
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

	/**
	 * @return a multi-valued conversation filter
	 * @param option the option value (defined in {@link Options}) that decides
	 * which many valued concrete filter to instantiate and return
	 * @param value the array representing the many values of the filter*/
	static ConversationFilter createFilter(String option, String[] value) 
										throws UnrecognizedCLIOptionException {
		
		switch(option) {
		case Options.FILTER_BLACKLIST:
			return new FilterBlacklist(value);
		default:
			throw new UnrecognizedCLIOptionException("Additional CLI parameter "
					+ "not recognized: '" + option + "'");

		}
		
	}
	
	/**
	 * @return an unvalued conversation filter
	 * @param option the option value (defined in {@link Options}) that decides
	 * which unvalued concrete filter to instantiate and return*/
	static ConversationFilter createFilter(String option) {
		return null;
		
	}
}
