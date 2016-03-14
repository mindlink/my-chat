package com.mindlinksoft.recruitment.mychat;

import java.io.IOException;

import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.services.ConfigurationService;
import com.mindlinksoft.recruitment.mychat.services.FileIOService;
import com.mindlinksoft.recruitment.mychat.services.FilterService;
import com.mindlinksoft.recruitment.mychat.services.LogService;

/**
 * {@link ConversationExporter} is a tool that can be used to convert a conversation
 * to JSON. It reads an input file from disk that contains a conversation, and outputs
 * a JSON file. You can apply filters and privacy to the conversation to make sure the
 * output is suitable to be consumed by any service or user. 
 * 
 * For full documentation refer to https://github.com/nilroyp/my-chat
 */
public class ConversationExporter {

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * 
     * @param configuration Configuration specifying the details of the export
     * @throws IOException When there was a problem reading or writing to the file.
     * @throws IllegalArgumentException When there was a problem with the configuration parameter.
     */
    public void export(String[] configuration) throws IllegalArgumentException, IOException {
    	
    	// Parse the arguments
    	ConversationExporterConfiguration config = _parseConfig(configuration);	
    	
    	// Read the file
    	Conversation conversation = _readFile(config);
    	
    	// Apply any filters
    	conversation = _applyFilters(conversation, config);
    	
    	// Write the output
    	_writeFile(conversation, config);
        
    }
    
    /**
     * Print help information informing the user how to use the {@link ConversationExporter}.
     */
    public static void printHelp() {
    	ConfigurationService configService = new ConfigurationService();
    	configService.printHelp();
    }
    
    /**
     * Parse the configuration supplied and create a {@link ConversationExporterConfiguration}
     * object.
     * 
     * @param configuration The raw configuration for the export.
     * @return Structured {@link ConversationExporterConfiguration} object representing
     * 		   the supplied {@code configuration}.
     */
    private ConversationExporterConfiguration _parseConfig(String[] configuration) {
    	ConfigurationService configService = new ConfigurationService();
    	ConversationExporterConfiguration config = configService.parseConfiguration(configuration);
    	
    	if (config == null) {
    		LogService.logError("An input file path and output file path are required.");
    		configService.printHelp();
    		return null;
    	}
    	
    	LogService.logMessage("Analysing the export requirements ...");
    	return config;
    }
    
    /**
     * Read the conversation from the specified file path
     * 
     * @param config Configuration for the export.
     * @throws IllegalArgumentException When it cannot find the specified file.
     * @throws IOException When there was a problem reading the specified file.
     */
    private Conversation _readFile(ConversationExporterConfiguration config) throws IllegalArgumentException, IOException {
    	
    	FileIOService fileIOService = new FileIOService();
        Conversation conversation = fileIOService.readConversation(config.getInputFilePath());
        
        LogService.logMessage("Reading conversation from '" + config.getInputFilePath() + "' ...");
        
        return conversation;
    }
    
    /**
     * Apply any supplied filters to the conversation.
     * 
     * @param conversation The conversation to be written.
     * @param config Configuration for the export.
     */
    private Conversation _applyFilters(Conversation conversation, ConversationExporterConfiguration config) {
    	
    	FilterService filterService = new FilterService();
    	Conversation filteredConversation = conversation;
    	
        if (config.getUser() != null) {
        	LogService.logMessage("Getting messages from '" + config.getUser() + "' ...");
        	filteredConversation = filterService.filterUser(conversation, config.getUser());
        }
        
        // TODO: Add other filters in here...
        
        return filteredConversation;
    }
    
    /**
     * Write the conversation to the specified file path
     * 
     * @param conversation The conversation to be written.
     * @param config Configuration for the export.
     * @throws IllegalArgumentException When it cannot find the specified file.
     * @throws IOException When there was a problem writing to the specified file.
     */
    private void _writeFile(Conversation conversation, ConversationExporterConfiguration config) throws IllegalArgumentException, IOException {
    	
    	FileIOService fileIOService = new FileIOService();
    	fileIOService.writeConversation(conversation, config.getOutputFilePath());

    	LogService.logMessage("Successfully exported the conversation '"
        		+ conversation.getName() + "' to '"
        		+ config.getOutputFilePath() + "'.");
    }

}
