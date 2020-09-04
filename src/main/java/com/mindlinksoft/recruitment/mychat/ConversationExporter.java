package com.mindlinksoft.recruitment.mychat;


import com.mindlinksoft.recruitment.mychat.Objects.Conversation;
import com.mindlinksoft.recruitment.mychat.Objects.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.Utilities.ReadWrite;
import com.mindlinksoft.recruitment.mychat.Utilities.CommandLineArgumentParser;

public class ConversationExporter {
    private ReadWrite readWrite = new ReadWrite();

    public static void main(String[] args) throws Exception {

        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath);
    }

    void exportConversation(String inputFilePath, String outputFilePath) throws Exception {
        Conversation conversation = this.readWrite.readConversation(inputFilePath);

        readWrite.writeConversation(conversation, outputFilePath);

        // TODO: More logging
        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
    }
}
