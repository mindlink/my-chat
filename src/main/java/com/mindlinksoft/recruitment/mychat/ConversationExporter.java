package com.mindlinksoft.recruitment.mychat;

/**
 * Represents a conversation exporter that can read a conversation and write it
 * out in JSON.
 */
public class ConversationExporter {

    /**
     * The application entry point.
     *
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception {

        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        Model model = new Model(configuration.inputFilePath, configuration.outputFilePath);
        Controller controller = new Controller(parser, model);
        controller.run();

    }
}
