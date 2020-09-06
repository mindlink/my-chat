package com.mindlinksoft.recruitment.mychat.Tools;


import com.mindlinksoft.recruitment.mychat.Objects.Conversation;
import com.mindlinksoft.recruitment.mychat.Objects.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.Objects.FlagContainer;
import com.mindlinksoft.recruitment.mychat.Utilities.*;

import java.util.ArrayList;
import java.util.Arrays;

public class ConversationExporter {
    private ReadWrite readWrite = new ReadWrite();
    private ArrayList<String> flags = new ArrayList<>(Arrays.asList("-details", "-obf", "-report"));

    public static void main(String[] args) throws Exception {

        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, configuration.argument, configuration.value, configuration.flag1, configuration.flag2, configuration.flag3);
    }

    public void exportConversation(String inputFilePath, String outputFilePath, String argument, String value, String flag1, String flag2, String flag3) throws Exception {
        Conversation conversation = this.readWrite.readConversation(inputFilePath);
        switch (argument) {
            case "-name" : {
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
            case "-obf": {
                Obfuscate.generateUserData(conversation);
                readWrite.writeConversation(Obfuscate.obfuscateSenderId(), outputFilePath);

                System.out.println("Conversation exported from '" + inputFilePath + "' to '" + " outputFilePath\nSender IDs have been assigned random 5 digit IDs.");
                break;
            }
            case "-report": {
                Report.generateActivityData(conversation);
                readWrite.writeConversation(Report.generateReport(), outputFilePath);

                System.out.println("Conversation exported from '" + inputFilePath + "' to '" + " outputFilePath\nReport included in JSON file.");
                break;
            }
            default:
                Conversation filteredCon;

                FlagContainer determiner = determineFlagsDefault(argument, value, flag1);

                if (determiner.detailFlag && determiner.obfFlag && determiner.reportFlag){
                    // if all true we have to follow a specific order
                    // details,obf,report
                    filteredCon = Filter.filterDetails(conversation);

                    Obfuscate.generateUserData(filteredCon);
                    filteredCon = Obfuscate.obfuscateSenderId();

                    Report.generateActivityData(filteredCon);
                    readWrite.writeConversation(Report.generateReport(), outputFilePath);

                    System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath + "\ndetails have been hidden, senders have been obfuscated and a report has been generated.");
                }


                // TODO: More logging

                break;
        }
    }

    private FlagContainer determineFlagsDefault(String argument, String value, String flag1){
        FlagContainer flagContainer = new FlagContainer(false, false, false);
        for (String flag : flags){
            if (argument.equals(flag)){
                if(flag.equals("-details")){
                    flagContainer.detailFlag = true;
                }
                if(flag.equals("-obf")){
                    flagContainer.obfFlag = true;
                }
                if(flag.equals("-report")){
                    flagContainer.reportFlag = true;
                }
            }
            if (value.equals(flag)){
                if(flag.equals("-details")){
                    flagContainer.detailFlag = true;
                }
                if(flag.equals("-obf")){
                    flagContainer.obfFlag = true;
                }
                if(flag.equals("-report")){
                    flagContainer.reportFlag = true;
                }
            }
            if (flag1.equals(flag)){
                if(flag.equals("-details")){
                    flagContainer.detailFlag = true;
                }
                if(flag.equals("-obf")){
                    flagContainer.obfFlag = true;
                }
                if(flag.equals("-report")){
                    flagContainer.reportFlag = true;
                }
            }
        }
        return flagContainer;
    }
}
