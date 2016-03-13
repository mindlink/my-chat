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
    
    /**
     * Return help text so the user knows how to effectively utilize the application.
     * 
     * @return Help text describing the application
     */
    public static String help() {
    	StringBuilder stringBuilder = new StringBuilder();
    	stringBuilder.append("CONVERSATION EXPORTER : HOW TO USE \n");
    	stringBuilder.append("------------------------------------------------------------------------- \n\n");
    	stringBuilder.append("REQUIRED \n");
    	stringBuilder.append("Input file path: The path of the file you wish to read from. \n");
    	stringBuilder.append("Output file path: A path to where the JSON conversation will be exported. \n\n");
    	
    	return stringBuilder.toString();
    }

}
