package com.mindlinksoft.recruitment.mychat.main;

import com.mindlinksoft.recruitment.mychat.exporter.ConversationExporter;
import com.mindlinksoft.recruitment.mychat.exporter.ConversationExporterService;

/**
 * Represents the application's entry point
 */
public class Main {
    /**
     * Application's entry point. Calls the parser, retrieves the configuration,
     * then starts the ConversationExporterService with the supplied configuration.
     * 
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        ConversationExporterConfiguration configuration = parser.parse(args);
        ConversationExporterService exporter = new ConversationExporter(configuration);
        exporter.export();
    }
}