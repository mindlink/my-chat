package com.mindlinksoft.recruitment.mychat;


import com.mindlinksoft.recruitment.mychat.Constructs.ConversationDefault;
import com.mindlinksoft.recruitment.mychat.Constructs.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.Constructs.FlagContainer;
import com.mindlinksoft.recruitment.mychat.Utilities.AdditionalFeatures.Details;
import com.mindlinksoft.recruitment.mychat.Utilities.AdditionalFeatures.Obfuscate;
import com.mindlinksoft.recruitment.mychat.Utilities.AdditionalFeatures.Report;
import com.mindlinksoft.recruitment.mychat.Utilities.CommandLineArgumentParser;
import com.mindlinksoft.recruitment.mychat.Utilities.Filter.FilterHide;
import com.mindlinksoft.recruitment.mychat.Utilities.Filter.FilterKeyword;
import com.mindlinksoft.recruitment.mychat.Utilities.Filter.FilterName;
import com.mindlinksoft.recruitment.mychat.Utilities.ReadWrite;

import java.util.ArrayList;
import java.util.Arrays;

public class ConversationExporter {
    private ReadWrite readWrite = new ReadWrite();
    private ArrayList<String> flags = new ArrayList<>(Arrays.asList("-details", "-obf", "-report"));
    private String doneOutput;
    private Report report = new Report();
    private Obfuscate obfuscate = new Obfuscate();
    private FilterName filterName = new FilterName();
    private FilterKeyword filterKeyword = new FilterKeyword();
    private FilterHide filterHide = new FilterHide();
    private Details details = new Details();


    public static void main(String[] args) throws Exception {

        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, configuration.argument_1, configuration.argument_2, configuration.argument_3, configuration.argument_4, configuration.argument_5);
    }

    void exportConversation(String inputFilePath, String outputFilePath, String argument_1, String argument_2, String argument_3, String argument_4, String argument_5) throws Exception {

        ConversationDefault conversationDefault = this.readWrite.readConversation(inputFilePath);

        doneOutput = "Done! ConversationDefault exported from '" + inputFilePath + "' to '" + outputFilePath + "'...";

        switch (argument_1) {
            case "-name": {
                System.out.println("...messages are filtered by " + "'" + argument_2 + "'");
                decide(determineFlags(argument_3, argument_4, argument_5), filterName.populateAndReturn(conversationDefault, argument_2), outputFilePath);
                break;
            }
            case "-keyword": {
                System.out.println("...messages are filtered by " + "'" + argument_2 + "'");
                decide(determineFlags(argument_3, argument_4, argument_5), filterKeyword.populateAndReturn(conversationDefault, argument_2), outputFilePath);

                break;
            }
            case "-hide": {
                System.out.println("...'" + argument_2 + "' are hidden, replaced with *redacted*");
                decide(determineFlags(argument_3, argument_4, argument_5), filterHide.populateAndReturn(conversationDefault, argument_2), outputFilePath);
                break;
            }
            default:
                decide(determineFlags(argument_1, argument_2, argument_3), conversationDefault, outputFilePath);
                break;
        }
    }

    /**
     * The decide method covers all possible states where up to 3 different flags, irrespective of order, can be parsed as arguments.
     * For example: "chat.txt chat.json -hide hello,there,pie -obf -report -details" is effectively the same as
     * "chat.txt chat.json -hide hello,there,pie -report -details -obf".
     * A design choice for a better user experience.
     **/

    private void decide(FlagContainer determiner, ConversationDefault conversationDefault, String outputFilePath) throws Exception {
        if (determiner.detailsFlag && determiner.obfFlag && determiner.reportFlag) {
            pipelineFull(conversationDefault, outputFilePath);
            printAccordingly(true, true, true);
        }
        if (!determiner.detailsFlag && determiner.obfFlag && determiner.reportFlag) {
            pipelineObfReport(conversationDefault, outputFilePath);
            printAccordingly(false, true, true);
        }
        if (determiner.detailsFlag && determiner.obfFlag && !determiner.reportFlag) {
            pipelineObfDetails(conversationDefault, outputFilePath);
            printAccordingly(true, true, false);
        }
        if (determiner.detailsFlag && !determiner.obfFlag && determiner.reportFlag) {
            pipelineDetailsReport(conversationDefault, outputFilePath);
            printAccordingly(true, false, true);
        }
        if (determiner.detailsFlag && !determiner.obfFlag && !determiner.reportFlag) {
            readWrite.writeConversation(details.populateAndReturn(conversationDefault), outputFilePath);
            printAccordingly(true, false, false);
        }
        if (!determiner.detailsFlag && determiner.obfFlag && !determiner.reportFlag) {
            readWrite.writeConversation(obfuscate.processAndReturn(conversationDefault), outputFilePath);
            printAccordingly(false, true, false);
        }
        if (!determiner.detailsFlag && !determiner.obfFlag && determiner.reportFlag) {
            readWrite.writeConversation(report.processAndReturn(conversationDefault), outputFilePath);
            printAccordingly(false, false, true);
        }
        if (!determiner.detailsFlag && !determiner.obfFlag && !determiner.reportFlag) {
            readWrite.writeConversation(conversationDefault, outputFilePath);
            printAccordingly(false, false, false);
        }
    }

    private void pipelineFull(ConversationDefault conversationDefault, String outputFilePath) throws Exception {
        readWrite.writeConversation(report.processAndReturn(obfuscate.processAndReturn(details.populateAndReturn(conversationDefault))), outputFilePath);
    }

    private void pipelineObfReport(ConversationDefault conversationDefault, String outputFilePath) throws Exception {
        readWrite.writeConversation(report.processAndReturn(obfuscate.processAndReturn(conversationDefault)), outputFilePath);
    }

    private void pipelineObfDetails(ConversationDefault conversationDefault, String outputFilePath) throws Exception {
        readWrite.writeConversation(obfuscate.processAndReturn(details.populateAndReturn(conversationDefault)), outputFilePath);
    }

    private void pipelineDetailsReport(ConversationDefault conversationDefault, String outputFilePath) throws Exception {
        readWrite.writeConversation(report.processAndReturn(details.populateAndReturn(conversationDefault)), outputFilePath);
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
            System.out.println("...credit card and phone numbers have been replaced with *redacted*");
        }
        if (obf) {
            System.out.println("...senders IDs have been replaced with random unique 5 digit IDs, view their identity in users.txt");
        }
        if (report) {
            System.out.println("...conversation is extended with user activity reports");
        }
        System.out.println(doneOutput);
    }
}
