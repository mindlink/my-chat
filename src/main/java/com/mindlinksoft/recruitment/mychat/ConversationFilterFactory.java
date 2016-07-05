package com.mindlinksoft.recruitment.mychat;

/**
 * Factory that chooses which concrete conversation filter to create based on 
 * the parameter option and the number of string values passed in. Responsible
 * for the creation of appropriate filters<p>
 * This factory class is responsible for the creation of the concrete filters.
 * It has explicit dependencies to each concrete {@link ConversationFilter} implementations.
 * In this respect, its role differ from that of the parser because the way to
 * instantiate filters (or the hierarchy of concrete filters) may change
 * without changing the way to parse conversation filtering options from the 
 * command line. This has the drawback of creating explicit dependencies to the
 * {@link Options} class for both this factory class and the argument parsing
 * logic.<p>
 * Some concrete filters take a single value, whereas other may take a list of
 * many. Some others may not need any values (flags). This class exposes methods
 * to accommodate the creation of these types of filters.*/
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
		case Options.FILTER_KEYWORD_ABBREVIATED:
			return new FilterKeyword(value);
			
		case Options.FILTER_USERNAME:
		case Options.FILTER_USERNAME_ABBREVIATED:
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
		case Options.FILTER_BLACKLIST_ABBREVIATED:
			return new FilterBlacklist(value);
		default:
			throw new UnrecognizedCLIOptionException("Additional CLI parameter "
					+ "not recognized: '" + option + "'");

		}
		
	}
	
	/**
	 * @return an unvalued conversation filter
	 * @param option the option value (defined in {@link Options}) that decides
	 * which unvalued concrete filter to instantiate and return
	 * @throws UnrecognizedCLIOptionException */
	static ConversationFilter createFilter(String option) throws UnrecognizedCLIOptionException {
		
		switch(option) {
		case Options.FLAG_REPORT:
		case Options.FLAG_REPORT_ABBREVIATED:
			return new FilterReport();
			
		case Options.FLAG_OBFUSCATE_NAMES:
		case Options.FLAG_OBFUSCATE_NAMES_ABBREVIATED:
			return new FilterObfuscateUsernames();
			
		default:
			throw new UnrecognizedCLIOptionException("Additional CLI parameter "
					+ "not recognized: '" + option + "'");
		}
	}
}
