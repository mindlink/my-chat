package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.exceptions.*;
import com.mindlinksoft.recruitment.mychat.features.AdditionalFeatures;
import com.mindlinksoft.recruitment.mychat.features.EssentialFeatures;
import com.mindlinksoft.recruitment.mychat.io.ReadConversation;
import com.mindlinksoft.recruitment.mychat.io.WriteConversation;
import com.mindlinksoft.recruitment.mychat.model.Conversation;
import com.mindlinksoft.recruitment.mychat.model.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.model.Features;
import com.mindlinksoft.recruitment.mychat.utils.AppInformation;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {

    /**
     * The application entry point.
     *
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) {
        try {
            ConversationExporter exporter = new ConversationExporter();
            ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
            exporter.exportConversation(configuration);
        } catch (WrongFeatureException | IOProblemException | BadArgumentsException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     *
     * @param configuration contains all the relevant information
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(ConversationExporterConfiguration configuration) throws WrongFeatureException, IOProblemException, WrongArgumentsException {
        Conversation conversation = ReadConversation.read(configuration.inputFilePath);
        Conversation conversationWithFeatures = applyRequirements(conversation, configuration);
        WriteConversation.write(conversationWithFeatures, configuration);
    }

    /**
     * Verifies which program arguments were chosen to be executed
     *
     * @param conversation  containing all the messages
     * @param configuration containing all the program arguments
     * @return a conversation with the applied commands and/or features
     */
    public Conversation applyRequirements(Conversation conversation, ConversationExporterConfiguration configuration) throws WrongFeatureException {
        Features features = configuration.features;
        List<String> logOutputs = new ArrayList<String>();

        try {
            if (configuration.features.user != null) {
                EssentialFeatures.filterByUser(conversation, features.user);
                logOutputs.add(AppInformation.USER + ":" + features.user);
            }
            if (configuration.features.word != null) {
                EssentialFeatures.filterByWord(conversation, features.word);
                logOutputs.add(AppInformation.WORD + ":" + features.word);
            }
            if (configuration.features.redactWord != null) {
                EssentialFeatures.redactWords(conversation, features.redactWord);
                logOutputs.add(AppInformation.REDACT_WORD + ":" + features.redactWord);
            }
            if (configuration.features.obfuscate) {
                AdditionalFeatures.obfuscateIDs(conversation);
                logOutputs.add(AppInformation.OBFUSCATE);
            }
            if (configuration.features.report) {
                conversation = AdditionalFeatures.activityReport(conversation);
                logOutputs.add(AppInformation.REPORT);
            }
        } catch (Exception e) {
            throw new WrongFeatureException();
        }

        printLogs(logOutputs, configuration.outputFilePath);

        return conversation;
    }

    /**
     * Prints the logs
     *
     * @param logInfo        is the list that contains the features applied
     * @param outputFilePath the output file path
     */
    public void printLogs(List<String> logInfo, String outputFilePath) {
        if (!logInfo.isEmpty()) {
            System.out.println(AppInformation.OUTPUT_FEATURES);
            for (String f : logInfo) {
                System.out.println(f);
            }
        }
        if (outputFilePath.length() > 0) System.out.println(AppInformation.OUTPUT_INFO + outputFilePath);
    }
}
