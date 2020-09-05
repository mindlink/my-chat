package com.mindlinksoft.recruitment.mychat.Tools;


import com.mindlinksoft.recruitment.mychat.Objects.Conversation;
import com.mindlinksoft.recruitment.mychat.Objects.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.Utilities.CommandLineArgumentParser;
import com.mindlinksoft.recruitment.mychat.Utilities.Filter;
import com.mindlinksoft.recruitment.mychat.Utilities.ReadWrite;

public class ConversationExporter {
    private ReadWrite readWrite = new ReadWrite();

    public static void main(String[] args) throws Exception {

        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, configuration.argument, configuration.value);
    }

    public void exportConversation(String inputFilePath, String outputFilePath, String argument, String value) throws Exception {
        Conversation conversation = this.readWrite.readConversation(inputFilePath);
        switch (argument) {
            case "-name": {
                Conversation filteredCon = Filter.filterName(conversation, value);
                readWrite.writeConversation(filteredCon, outputFilePath);

                System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath + "\nFiltered for name: " + "'" + value + "'.");
                break;
            }
            case "-keyword": {
                Conversation filteredCon = Filter.filterKeyword(conversation, value);
                readWrite.writeConversation(filteredCon, outputFilePath);

                System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath + "\nFiltered for keyword: " + "'" + value + "'.");
                break;
            }
            case "-hide": {
                Conversation filteredCon = Filter.filterHide(conversation, value);
                readWrite.writeConversation(filteredCon, outputFilePath);

                System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath + "\n'" + value + "' replaced with *redacted*.");
                break;
            }
            case "-details": {
                Conversation filteredCon = Filter.filterDetails(conversation);
                readWrite.writeConversation(filteredCon, outputFilePath);

                System.out.println("Conversation exported from '" + inputFilePath + "' to '" + " outputFilePath\nCredit card and mobile numbers replaced with *redacted*.");
                break;
            }
            default:
                readWrite.writeConversation(conversation, outputFilePath);
                // TODO: More logging
                System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
                break;
        }
    }
}
