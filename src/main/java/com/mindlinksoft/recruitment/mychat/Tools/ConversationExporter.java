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
    private String detailsOutput = "...credit card and phone numbers have been replaced with *redacted*";
    private String obfOutput = "...senders IDs have been replaced with random unique 5 digit IDs, view their identity in users.txt";
    private String reportOutput = "...conversation is extended with user activity reports";
    private String doneOutput;

    public static void main(String[] args) throws Exception {

        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, configuration.argument, configuration.value, configuration.flag1, configuration.flag2, configuration.flag3);
    }

    public void exportConversation(String inputFilePath, String outputFilePath, String argument, String value, String flag1, String flag2, String flag3) throws Exception {

        Conversation conversation = this.readWrite.readConversation(inputFilePath);

        doneOutput = "Done! Conversation exported from '" + inputFilePath + "' to '" + outputFilePath +"'...";

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
                FlagContainer determiner = determineFlagsDefault(argument, value, flag1);
                // all flags
                if (determiner.detailsFlag && determiner.obfFlag && determiner.reportFlag){
                    pipelineFull(conversation, outputFilePath);
                }
                // -obf and -report
                if (!determiner.detailsFlag && determiner.obfFlag && determiner.reportFlag){
                    pipelineObfReport(conversation,outputFilePath);
                }
                // -details and -obf
                if (determiner.detailsFlag && determiner.obfFlag && !determiner.reportFlag){
                    pipelineObfDetails(conversation, outputFilePath);
                }
                // -details and -report
                if (determiner.detailsFlag && !determiner.obfFlag && determiner.reportFlag){
                    pipelineDetailsReport(conversation, outputFilePath);
                }
                // -details
                if (determiner.detailsFlag && !determiner.obfFlag && !determiner.reportFlag){
                    readWrite.writeConversation(Filter.filterDetails(conversation), outputFilePath);
                    System.out.println(detailsOutput);
                    System.out.println(doneOutput);
                }
                // -obf
                if (!determiner.detailsFlag && determiner.obfFlag && !determiner.reportFlag){
                    Obfuscate.generateUserData(conversation);
                    readWrite.writeConversation(Obfuscate.obfuscateSenderId(), outputFilePath);
                    System.out.println(obfOutput);
                    System.out.println(doneOutput);
                }
                // -report
                if (!determiner.detailsFlag && !determiner.obfFlag && determiner.reportFlag){
                    Report.generateActivityData(conversation);
                    readWrite.writeConversation(Report.generateReport(), outputFilePath);
                    System.out.println(reportOutput);
                    System.out.println(doneOutput);
                }
                // TODO: More logging

                break;        }
    }

    private void pipelineFull(Conversation conversation, String outputFilePath) throws Exception {
        Conversation filteredCon = Filter.filterDetails(conversation);
        System.out.println(detailsOutput);

        Obfuscate.generateUserData(filteredCon);
        filteredCon = Obfuscate.obfuscateSenderId();
        System.out.println(obfOutput);

        Report.generateActivityData(filteredCon);
        System.out.println(reportOutput);

        readWrite.writeConversation(Report.generateReport(), outputFilePath);
        System.out.println(doneOutput);

    }

    private void pipelineObfReport(Conversation conversation, String outputFilePath) throws Exception {
        Conversation filteredCon = conversation;

        Obfuscate.generateUserData(filteredCon);
        filteredCon = Obfuscate.obfuscateSenderId();
        System.out.println(obfOutput);

        Report.generateActivityData(filteredCon);
        System.out.println(reportOutput);

        readWrite.writeConversation(Report.generateReport(), outputFilePath);
        System.out.println(doneOutput);
    }

    private void pipelineObfDetails(Conversation conversation, String outputFilePath) throws Exception {
        Conversation filteredCon = Filter.filterDetails(conversation);
        System.out.println(detailsOutput);

        Obfuscate.generateUserData(filteredCon);
        System.out.println(obfOutput);

        readWrite.writeConversation(Obfuscate.obfuscateSenderId(), outputFilePath);
        System.out.println(doneOutput);
    }

    private void pipelineDetailsReport(Conversation conversation, String outputFilePath) throws Exception {
        Conversation filteredCon = Filter.filterDetails(conversation);
        System.out.println(detailsOutput);

        Report.generateActivityData(filteredCon);
        System.out.println(reportOutput);

        readWrite.writeConversation(Report.generateReport(), outputFilePath);
        System.out.println(doneOutput);
    }

    private FlagContainer determineFlagsDefault(String argument, String value, String flag1){
        FlagContainer flagContainer = new FlagContainer(false, false, false);
        for (String flag : flags){
            if (argument.equals(flag)){
                if(flag.equals("-details")){
                    flagContainer.detailsFlag = true;
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
                    flagContainer.detailsFlag = true;
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
                    flagContainer.detailsFlag = true;
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
    private FlagContainer determineFlags(String flag1, String flag2, String flag3){
        FlagContainer flagContainer = new FlagContainer(false, false, false);
        for (String flag : flags){
            if (flag1.equals(flag)){
                if(flag.equals("-details")){
                    flagContainer.detailsFlag = true;
                }
                if(flag.equals("-obf")){
                    flagContainer.obfFlag = true;
                }
                if(flag.equals("-report")){
                    flagContainer.reportFlag = true;
                }
            }
            if (flag2.equals(flag)){
                if(flag.equals("-details")){
                    flagContainer.detailsFlag = true;
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
                    flagContainer.detailsFlag = true;
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
