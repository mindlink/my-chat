package com.mindlinksoft.recruitment.mychat.main;

import com.mindlinksoft.recruitment.mychat.exporter.ConversationExporter;
import com.mindlinksoft.recruitment.mychat.exporter.ConversationExporterService;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents the application's entry point
 */
public class Main {

    private final static Logger LOGGER = Logger.getLogger(Main.class.getName());

    /**
     * Application's entry point. Calls the parser, retrieves the configuration,
     * then starts the ConversationExporterService with the supplied configuration.
     *
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        LOGGER.log(Level.INFO, "Starting parser");
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        ConversationExporterConfiguration configuration = parser.parse(args);
        LOGGER.log(Level.INFO, "Parser created");

        LOGGER.log(Level.INFO, "Starting exporter...");
        ConversationExporterService exporter = new ConversationExporter(configuration);
        exporter.export();
        LOGGER.log(Level.INFO, "Exporter completed...");

        LOGGER.log(Level.INFO, "Program terminating...");
    }
}