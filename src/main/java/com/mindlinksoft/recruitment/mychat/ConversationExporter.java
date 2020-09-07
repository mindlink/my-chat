package com.mindlinksoft.recruitment.mychat;


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
    private Filter filter = new Filter();
    private Report report = new Report();
    private Obfuscate obfuscate = new Obfuscate();

    public static void main(String[] args) throws Exception {

        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, configuration.argument_1, configuration.argument_2, configuration.argument_3, configuration.argument_4, configuration.argument_5);
    }

    void exportConversation(String inputFilePath, String outputFilePath, String argument_1, String argument_2, String argument_3, String argument_4, String argument_5) throws Exception {

        Conversation conversation = this.readWrite.readConversation(inputFilePath);

        doneOutput = "Done! Conversation exported from '" + inputFilePath + "' to '" + outputFilePath + "'...";

        switch (argument_1) {
            case "-name": {
                System.out.println("...messages are filtered by " + "'" + argument_2 + "'");
                FlagContainer determiner = determineFlags(argument_3, argument_4, argument_5);
                Conversation filteredCon = filter.filterName(conversation, argument_2);
                decide(determiner, filteredCon, outputFilePath);

                break;
            }
            case "-keyword": {
                System.out.println("...messages are filtered by " + "'" + argument_2 + "'");
                FlagContainer determiner = determineFlags(argument_3, argument_4, argument_5);
                Conversation filteredCon = filter.filterKeyword(conversation, argument_2);
                decide(determiner, filteredCon, outputFilePath);

                break;
            }
            case "-hide": {
                System.out.println("...'" + argument_2 + "' are hidden, replaced with *redacted*");
                FlagContainer determiner = determineFlags(argument_3, argument_4, argument_5);
                Conversation filteredCon = filter.filterHide(conversation, argument_2);
                decide(determiner, filteredCon, outputFilePath);
                break;
            }
            default:
                FlagContainer determiner = determineFlags(argument_1, argument_2, argument_3);
                decide(determiner, conversation, outputFilePath);
                break;
        }
    }

    private void decide(FlagContainer determiner, Conversation conversation, String outputFilePath) throws Exception {
        if (determiner.detailsFlag && determiner.obfFlag && determiner.reportFlag) {
            pipelineFull(conversation, outputFilePath);
            printAccordingly(true, true, true);
        }
        if (!determiner.detailsFlag && determiner.obfFlag && determiner.reportFlag) {
            pipelineObfReport(conversation, outputFilePath);
            printAccordingly(false, true, true);
        }
        if (determiner.detailsFlag && determiner.obfFlag && !determiner.reportFlag) {
            pipelineObfDetails(conversation, outputFilePath);
            printAccordingly(true, true, false);
        }
        if (determiner.detailsFlag && !determiner.obfFlag && determiner.reportFlag) {
            pipelineDetailsReport(conversation, outputFilePath);
            printAccordingly(true, false, true);
        }
        if (determiner.detailsFlag && !determiner.obfFlag && !determiner.reportFlag) {
            readWrite.writeConversation(filter.filterDetails(conversation), outputFilePath);
            printAccordingly(true, false, false);
        }
        if (!determiner.detailsFlag && determiner.obfFlag && !determiner.reportFlag) {
            readWrite.writeConversation(obfuscate.obfuscateSenderId(conversation), outputFilePath);
            printAccordingly(false, true, false);
        }
        if (!determiner.detailsFlag && !determiner.obfFlag && determiner.reportFlag) {
            readWrite.writeConversation(report.generateReport(conversation), outputFilePath);
            printAccordingly(false, false, true);
        }
        if (!determiner.detailsFlag && !determiner.obfFlag && !determiner.reportFlag) {
            readWrite.writeConversation(conversation, outputFilePath);
            printAccordingly(false, false, false);
        }

    }

    private void pipelineFull(Conversation conversation, String outputFilePath) throws Exception {
        Conversation filteredCon = filter.filterDetails(conversation);
        filteredCon = obfuscate.obfuscateSenderId(filteredCon);
        readWrite.writeConversation(report.generateReport(filteredCon), outputFilePath);
    }

    private void pipelineObfReport(Conversation conversation, String outputFilePath) throws Exception {
        Conversation filteredCon = conversation;
        filteredCon = obfuscate.obfuscateSenderId(filteredCon);
        readWrite.writeConversation(report.generateReport(filteredCon), outputFilePath);
    }

    private void pipelineObfDetails(Conversation conversation, String outputFilePath) throws Exception {
        Conversation filteredCon = filter.filterDetails(conversation);
        readWrite.writeConversation(obfuscate.obfuscateSenderId(filteredCon), outputFilePath);
    }

    private void pipelineDetailsReport(Conversation conversation, String outputFilePath) throws Exception {
        Conversation filteredCon = filter.filterDetails(conversation);
        readWrite.writeConversation(report.generateReport(filteredCon), outputFilePath);
    }

    private FlagContainer determineFlags(String flag1, String flag2, String flag3) {
        FlagContainer flagContainer = new FlagContainer(false, false, false);
        for (String flag : flags) {
            if (flag1.equals(flag)) {
                if (flag.equals("-details")) {
                    flagContainer.detailsFlag = true;
                }
                if (flag.equals("-obf")) {
                    flagContainer.obfFlag = true;
                }
                if (flag.equals("-report")) {
                    flagContainer.reportFlag = true;
                }
            }
            if (flag2.equals(flag)) {
                if (flag.equals("-details")) {
                    flagContainer.detailsFlag = true;
                }
                if (flag.equals("-obf")) {
                    flagContainer.obfFlag = true;
                }
                if (flag.equals("-report")) {
                    flagContainer.reportFlag = true;
                }
            }
            if (flag3.equals(flag)) {
                if (flag.equals("-details")) {
                    flagContainer.detailsFlag = true;
                }
                if (flag.equals("-obf")) {
                    flagContainer.obfFlag = true;
                }
                if (flag.equals("-report")) {
                    flagContainer.reportFlag = true;
                }
            }
        }
        return flagContainer;
    }

    private void printAccordingly(boolean details, boolean obf, boolean report) {
        if (details) {
            System.out.println(detailsOutput);
        }
        if (obf) {
            System.out.println(obfOutput);
        }
        if (report) {
            System.out.println(reportOutput);
        }
        System.out.println(doneOutput);
    }
}
