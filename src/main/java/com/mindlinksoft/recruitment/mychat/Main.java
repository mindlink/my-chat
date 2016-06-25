package com.mindlinksoft.recruitment.mychat;

import java.io.IOException;

public class Main {

	/**
	 * The application entry point.
	 * @param args The command line arguments.
	 * @throws IOException
	 */
	public static void main(String[] args) {
		ConversationExporter exporter = new ConversationExporter();
		ConversationExporterConfiguration configuration = 
				CommandLineArgumentParser.parseCommandLineArguments(args);

		try {
			exporter.exportConversation(configuration.inputFilePath, 
										configuration.outputFilePath);
		} catch (IOException e) {
			// TODO Log errors before exiting
			e.printStackTrace();
		}
	}

}
