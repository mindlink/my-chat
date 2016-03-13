package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.services.ConfigurationService;
import com.mindlinksoft.recruitment.mychat.services.FileIOService;

public class ConversationExporter {

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when something bad happens.
     */
    public void export(String[] configuration) throws Exception {
    	
    	// Set up the configuration object based on the options the user has chosen
    	ConfigurationService configService = new ConfigurationService();
    	ConversationExporterConfiguration config = configService.parseConfiguration(configuration);
    	
    	// Read the conversation from the input file
    	FileIOService fileIOService = new FileIOService();
        Conversation conversation = fileIOService.readConversation(config.getInputFilePath());

        // Write the JSON to the output file
        fileIOService.writeConversation(conversation, config.getOutputFilePath());

        // TODO: Add more logging...
        System.out.println("Conversation exported from '" + config.getInputFilePath() + "' to '" + config.getOutputFilePath());
    }

}
