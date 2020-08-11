package com.mindlinksoft.recruitment.mychat;


/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser{
	
    private final String userCommand = "USER";
    private final String keywordCommand = "KEYWORD";
    private final String blacklist = "BLACKLIST";
	/**
     * Parses the given {@code arguments} into the exporter configuration.
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line arguments.
     */
    public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) {
    		ConversationExporterConfiguration exporterConfiguration = new ConversationExporterConfiguration(arguments[0], arguments[1]);
    		
    		//Checks if the command exists, if yes, create an object that implements that command
    		
    		if(arguments.length > 2) {
    			for(int i = 2; i<arguments.length; i++) {
    				String[] command = arguments[i].split("=");
    				switch(command[0].toUpperCase()) {
    					case userCommand:
       					FilterByUser filterByUser = new FilterByUser();
       					
    						if(filterByUser.getRequireParameters()) {      			//Checks if the command requires parameters  
   							filterByUser.processParameters(command[1]);			// Assign parameters to object
    						}
    						exporterConfiguration.addFunctionality(filterByUser);    // Commands to be performed are included in the exporter configurations
    						break;
    					case keywordCommand:
    						FilterByKeyword filterByKeyword = new FilterByKeyword();
    						if(filterByKeyword.getRequireParameters()) {
    							filterByKeyword.processParameters(command[1]);
    						}
    						exporterConfiguration.addFunctionality(filterByKeyword);
    						break;
    					case blacklist:
    						Blacklist blacklist = new Blacklist();
    						if(blacklist.getRequireParameters()) {
    							blacklist.processParameters(command[1]);
    						}
    						exporterConfiguration.addFunctionality(blacklist);
    						break;
    					default:
    						// If command not recognized 
    						System.out.println("Command not found!");
    				}	
    			
    			}
    		}
    		return exporterConfiguration;
    }
    
}
