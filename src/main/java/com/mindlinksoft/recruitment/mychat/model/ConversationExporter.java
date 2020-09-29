package com.mindlinksoft.recruitment.mychat.model;

/**
 * Represents a conversation exporter that can read a conversation and write it
 * out in JSON.
 */
public class ConversationExporter {

	/**
	 * Exports the conversation at {@code inputFilePath} as JSON to
	 * {@code outputFilePath}.
	 * 
	 * @param inputFilePath  The input file path.
	 * @param outputFilePath The output file path.
	 * @throws Exception Thrown when something bad happens.
	 */
	public void exportConversation(ConversationExporterConfiguration configuration) throws Exception {
		String inputFilePath = configuration.getInputFilePath();
		String outputFilePath = configuration.getOutputFilePath();
		ConversationReader conversationReader = new ConversationReaderTextFiles();
		Conversation conversation = conversationReader.readConversation(configuration);
		ConversationWriter conversationWriter = new ConversationWriterJsonFiles();
		conversationWriter.writeConversation(conversation, outputFilePath);
		System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
	}
}
