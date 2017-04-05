package com.mindlinksoft.recruitment.mychat.conversation.exporter;

import com.mindlinksoft.recruitment.mychat.conversation.IConversationFormatter;

/**
 * Represents the configuration for the exporter.
 */
public final class ConversationExporterConfiguration {

    private final String inputFilePath;
    private final String outputFilePath;    
    private final IConversationFormatter conversationFormatter;

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     */
    public ConversationExporterConfiguration(
    		String inputFilePath, 
    		String outputFilePath, 
    		IConversationFormatter conversationFormatter) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.conversationFormatter = conversationFormatter;
    }

	/**
	 * Gets the input file path.
	 * @return Input file path.
	 */
	public String getInputFilePath() {
		return inputFilePath;
	}

	/**
	 * Gets the output file path.
	 * @return Output file path.
	 */
	public String getOutputFilePath() {
		return outputFilePath;
	}

	/**
	 * Gets the conversation formatter.
	 * @return Conversation Formatter.
	 */
	public IConversationFormatter getConversationFormatter() {
		return conversationFormatter;
	}
   
}
