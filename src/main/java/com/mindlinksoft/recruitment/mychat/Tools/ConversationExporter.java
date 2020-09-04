package com.mindlinksoft.recruitment.mychat.Tools;


import com.mindlinksoft.recruitment.mychat.Objects.Conversation;
import com.mindlinksoft.recruitment.mychat.Objects.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.Utilities.Filter;
import com.mindlinksoft.recruitment.mychat.Utilities.ReadWrite;
import com.mindlinksoft.recruitment.mychat.Utilities.CommandLineArgumentParser;

public class ConversationExporter {
    private ReadWrite readWrite = new ReadWrite();

    public static void main(String[] args) throws Exception {

        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, configuration.argument, configuration.value);
    }

    public void exportConversation(String inputFilePath, String outputFilePath, String argument, String value) throws Exception {
        Conversation conversation = this.readWrite.readConversation(inputFilePath);
        if (argument.equals("-f")){
            Conversation filteredCon = Filter.filterName(conversation, value);
            readWrite.writeConversation(filteredCon, outputFilePath);
        }
        else {

            readWrite.writeConversation(conversation, outputFilePath);

            // TODO: More logging
            System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
        }
    }
}