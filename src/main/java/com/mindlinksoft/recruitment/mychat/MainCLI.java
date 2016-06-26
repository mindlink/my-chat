package com.mindlinksoft.recruitment.mychat;

import java.io.IOException;

public class MainCLI {

	/**
	 * The CLI application entry point.
	 * @param args The command line arguments.
	 */
	public static void main(String[] args) {
		
		try {	
			//create configuration instance
			ConversationExporterConfiguration config = 
					CommandLineArgumentParser.parseCommandLineArguments(args);
			//create exporter instance
			ConversationExporter exporter = new ConversationExporter(config);

			exporter.exportConversation(config.get("inputFilePath"), 
										config.get("outputFilePath"));
			
		} catch (IOException e) {
			// TODO Log errors before exiting
			e.printStackTrace();
		} catch(IllegalArgumentException e) {
			//TODO log to error: 
			//"usage: input_file_path output_file_path
			//[-u userid]
			//[-k content_keyword]
			//[-b word]"
			e.printStackTrace();
		}
	}

}
