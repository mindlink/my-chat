package com.mindlinksoft.recruitment.mychat;

public class ConversationExporter {

    public static void main(String[] args) throws Exception {

        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        Model model = new Model(configuration.inputFilePath, configuration.outputFilePath);
        Controller controller = new Controller(parser, model);
        controller.run();

    }
}
