package com.mindlinksoft.recruitment.mychat;

public class Main {
	/**
     * The application entry point.
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception {
        ConversationExporterConfiguration configuration = CommandLineArgumentParser.parseCommandLineArguments(args);

        ConversationExporter.exportConversation(configuration);
    }
}
