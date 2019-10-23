package com.mindlinksoft.recruitment.mychat;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ConversationExporterMain {
	
	/**
     * The application entry point.
     * @param args The command line arguments.
	 * @throws IOException If the input file is already opened by another reader
	 * @throws FileNotFoundException When the input file does not exist
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
    	
    	/**
    	 * Create the exporter, parser and get the configuration. We then use the configuration to export a chat.
    	 */
    	ConversationExporter exporter = new ConversationExporter();
    	CommandLineArgumentParser parser = new CommandLineArgumentParser();
    	ConversationExporterConfiguration configuration = parser.generateExportOptions(args);
            	
    	exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, configuration.filter);
   
    }
    
}
