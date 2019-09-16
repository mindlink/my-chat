package com.mindlinksoft.recruitment.mychat;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when something bad happens.
     */
    public static void exportConversation(ConversationExporterConfiguration configuration) throws Exception {
        Conversation conversation = ConversationReader.readConversation(configuration.inputFilePath, configuration.optionalArgumentManager);

        ConversationWriter writer = new ConversationWriter();
        writer.writeConversation(conversation, configuration.outputFilePath);

        System.out.println("Conversation exported from '" + configuration.inputFilePath + "' to '" + configuration.outputFilePath);
    }

    
}
