package com.mindlinksoft.recruitment.mychat.conversation.exporter;

import com.mindlinksoft.recruitment.mychat.CommandLineArgumentParser;
import com.mindlinksoft.recruitment.mychat.conversation.serialization.JSONSerializer;

public final class ConversationExporterApplication {

    /**
     * The application entry point. Available command line options:
     * -u  filter messages by user
     * -k  filter message by keyword
     * -b  blacklisted keywords
     * -n  redact credit card and phone numbers
     * -a  obfuscate userIDs using the aliases defined in the properties
     * -p  properties file path
     * 
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception {
        ConversationExporter exporter = new ConversationExporter(new JSONSerializer());
        ConversationImporter importer = new ConversationImporter();
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(
        		importer.importConversation(configuration.getInputFilePath()), 
        		configuration.getOutputFilePath(), 
        		configuration.getConversationFormatter());
    }
}
