package com.mindlinksoft.recruitment.mychat;

import java.util.Arrays;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
	
	//The index at which any optional arguments begin
	private final int optionalArgumentStartIndex = 2;
	private final String filterByUserKeyword = "/fuser";
	private final String filterByPhraseKeyword = "/fword";
	private final String blacklistKeyword = "/blist";
	
	private final String[] allValidArguments = {filterByUserKeyword, filterByPhraseKeyword, blacklistKeyword};
	
    /**
     * Parses the given {@code arguments} into the exporter configuration.
     * @param arguments The command line arguments.
     * 		Argument order as follows:
     * 			[targetConversation.txt, nameForJSONFile, <extra argument>, <extra argument value e.g. filter name>...]
     * @return The exporter configuration representing the command line arguments.
     */
    public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) {
    	
    	ConversationExporterConfiguration exporterConfiguration = new ConversationExporterConfiguration(arguments[0], arguments[1]);
    	
    	//If optional arguments have been provided
    	if (arguments.length > optionalArgumentStartIndex) {
    		
    		//Creates an array of just the optional arguments
    		String[] optionalArguments = Arrays.copyOfRange(arguments, optionalArgumentStartIndex, arguments.length);
    		
    		//Cycle through every optional value
    		for (int currentIndex = 0; currentIndex < optionalArguments.length; currentIndex++) {
    			switch (optionalArguments[currentIndex]) {
    				case filterByUserKeyword:
    					if (exporterConfiguration.getUserFilterSetting() == false) {
	    					try {
		    					//Enables user filter setting
		    					exporterConfiguration.setUserFilterSetting(true);
		    					currentIndex++;
		    					
		    					//If part of an argument has been missed, the following will detect the presence of 
		    					//the second keyword and alter the flow to process the keyword correctly
		    					if (checkForValidArgument(optionalArguments[currentIndex])) {
	
			    					exporterConfiguration.setUserFilterValue(optionalArguments[currentIndex]);
		    					} else {
		    						currentIndex--;
		    						exporterConfiguration.setUserFilterSetting(false);
		    					}
		    					
	    					} catch (ArrayIndexOutOfBoundsException e) {
	    						System.out.println("User filter specified yet no name to filter by provided");
	    						//Disable filter after error encountered
	    						exporterConfiguration.setUserFilterSetting(false);
	    						currentIndex--;
	    					}
    					}
    					break;
    				case filterByPhraseKeyword:
    					if (exporterConfiguration.getKeywordFilterSetting() == false) {
	    					try {
	    						//Enables keyword filter setting
	    						exporterConfiguration.setKeywordFilterSetting(true);
	    						
	    						currentIndex++;
	    						
	    						//If part of an argument has been missed, the following will detect the presence of 
		    					//the second keyword and alter the flow to process the keyword correctly
		    					if (checkForValidArgument(optionalArguments[currentIndex])) {
	
		    						exporterConfiguration.setKeywordFilterValue(optionalArguments[currentIndex]);
		    					} else {
		    						currentIndex--;
		    						exporterConfiguration.setKeywordFilterSetting(false);
		    					}
		    					
	    					} catch (ArrayIndexOutOfBoundsException e) {
	    						System.out.println("Keyword filter specified yet no name");
	    						exporterConfiguration.setKeywordFilterSetting(false);
	    						currentIndex--;
	    					}
    					}
    					break;
    				case blacklistKeyword:
    					if (exporterConfiguration.getBlacklistSetting() == false) {
	    					try {
	    						exporterConfiguration.setBlacklistSetting(true);
	
	    						currentIndex++;
	
	    						if (checkForValidArgument(optionalArguments[currentIndex])) {
	    							while (currentIndex < optionalArguments.length) {
	        							if (checkForValidArgument(optionalArguments[currentIndex])) {
		        							exporterConfiguration.setBlacklistValue(optionalArguments[currentIndex]);
		        							currentIndex++;
	        							} else {
	        								break;
	        							}
	        						}
	        						currentIndex--;
	        						
		    					} else {
		    						currentIndex--;
		    						exporterConfiguration.setKeywordFilterSetting(false);
		    					}
	    						
	    					} catch (ArrayIndexOutOfBoundsException e) {
	    						System.out.println("Blacklist enabled yet no words specified");
	    						exporterConfiguration.setBlacklistSetting(false);
	    						currentIndex--;
	    					}
    					}
    					break;
    			}
    		}
    		
    	}
    	if (exporterConfiguration.getUserFilterSetting()) {
    		System.out.println("Only exporting messages from user " + exporterConfiguration.getUserFilterValue());
    	}
    	
    	if (exporterConfiguration.getKeywordFilterSetting()) {
    		System.out.println("Only exporting messages containing the following phrase: " + exporterConfiguration.getKeywordFilterValue());
    	}
    	
    	if (exporterConfiguration.getBlacklistSetting()) {
    		for (String value : exporterConfiguration.getBlacklistedWords()) {
    			System.out.println("Exporting with " + value + " redacted");
    		}
    	}
    	
        return exporterConfiguration;
    }
    
    /**
     * Checks that the arguments passed isnt a keyword in it's own rights
     * @param argument The passed argument to be tested
     * @return A boolean - true if valid, false if not
     */
    private boolean checkForValidArgument(String argument) {
    	if (Arrays.stream(allValidArguments).anyMatch(argument::equals)) {
    		return false;
    	} else {
    		return true;
    	}
    }
}
