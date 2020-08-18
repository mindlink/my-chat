package com.mindlinksoft.recruitment.mychat;


/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser{
	
	/**
     * Parses the given {@code arguments} into the exporter configuration.
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line arguments.
     */
    public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) {
    		int argumentThreshold = 2;
    		ConversationExporterConfiguration exporterConfiguration = new ConversationExporterConfiguration(arguments[0], arguments[1]);
    		
    		// Checks if the are command arguments after the inputpath and outputpath 
    		
    		if(arguments.length > argumentThreshold) {
    			// Object created to handle the commands specified by the user
    			CommandProcesser commandProcesser = new CommandProcesser();
    			for(int i = argumentThreshold; i<arguments.length; i++) {
    				String[] command = arguments[i].split("=");
    				commandProcesser.setCommandName(command[0]); 
    				commandProcesser.setCommandArguments(command[1]);
    				commandProcesser.commandActivation(exporterConfiguration);
    			}
    		}
    		return exporterConfiguration;
    }
    
}
