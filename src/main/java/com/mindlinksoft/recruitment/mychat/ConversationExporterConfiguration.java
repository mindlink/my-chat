package com.mindlinksoft.recruitment.mychat;

/**
 * Represents the configuration for the exporter.
 */
public final class ConversationExporterConfiguration {
	/**
	 * Gets the input file path.
	 */
	public String inputFilePath;

	/**
	 * Gets the output file path.
	 */
	public String outputFilePath;

	/**
	 * Gets the filter arguments.
	 */
	public String[] option;

	/**
	 * Initialises a new instance of the {@link ConversationExporterConfiguration}
	 * class.
	 * 
	 * @param inputFilePath  The input file path.
	 * @param outputFilePath The output file path.
	 * @param filter         The filter option chosen by the user.
	 */
	public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, String[] option) {
		this.inputFilePath = inputFilePath;
		this.outputFilePath = outputFilePath;
		this.option = option;
	}
}
