package com.mindlinksoft.recruitment.mychat;

import java.util.Collection;

import com.mindlinksoft.recruitment.mychat.commands.IConversationExportCommand;

/**
 * Represents the configuration for the exporter.
 */
public final class ConversationExporterConfiguration {
    /**
     * The input file path.
     */
    private String inputFilePath;

    /**
     * The output file path.
     */
    private String outputFilePath;
    
    /**
     * The list of extra commands to carry out for the export
     */
    private Collection<IConversationExportCommand> listOfCommands;

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, Collection<IConversationExportCommand> listOfCommands) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.listOfCommands = listOfCommands;
    }

    /**
     * Gets the input file path
     */
	public String getInputFilePath() {
		return inputFilePath;
	}

	/**
	 * Gets the output file path
	 */
	public String getOutputFilePath() {
		return outputFilePath;
	}

	public Collection<IConversationExportCommand> getListOfCommands() {
		return listOfCommands;
	}
}
