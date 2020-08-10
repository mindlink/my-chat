package com.mindlinksoft.recruitment.mychat.main;

import com.mindlinksoft.recruitment.mychat.exporter.ConversationExporter;
import com.mindlinksoft.recruitment.mychat.exporter.ConversationExporterService;

public class Main {
    public static void main(String[] args) {
        CommandLineArgumentParser parser = new CommandLineArgumentParser();
        ConversationExporterConfiguration configuration = 
                parser.parseCommandLineArguments(args);
        ConversationExporterService exporter = new ConversationExporter(configuration);
        exporter.export();
    }
}