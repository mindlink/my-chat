package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import picocli.CommandLine;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.ParseResult;
import picocli.CommandLine.UnmatchedArgumentException;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {

    /**
     * The application entry point.
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception {
        // We use picocli to parse the command line - see https://picocli.info/
        ConversationExporterConfiguration configuration = new ConversationExporterConfiguration();
        CommandLine cmd = new CommandLine(configuration);

        try {
            ParseResult parseResult = cmd.parseArgs(args);
        
            if (parseResult.isUsageHelpRequested()) {
                cmd.usage(cmd.getOut());
                System.exit(cmd.getCommandSpec().exitCodeOnUsageHelp());
                return;
            }
            
            if (parseResult.isVersionHelpRequested()) {
                cmd.printVersionHelp(cmd.getOut());
                System.exit(cmd.getCommandSpec().exitCodeOnVersionHelp());
                return;
            }

            ConversationExporter exporter = new ConversationExporter();

            exporter.exportConversation(configuration);

            System.exit(cmd.getCommandSpec().exitCodeOnSuccess());
        } catch (ParameterException ex) {
            cmd.getErr().println(ex.getMessage());
            if (!UnmatchedArgumentException.printSuggestions(ex, cmd.getErr())) {
                ex.getCommandLine().usage(cmd.getErr());
            }

            System.exit(cmd.getCommandSpec().exitCodeOnInvalidInput());
        } catch (Exception ex) {
            ex.printStackTrace(cmd.getErr());
            System.exit(cmd.getCommandSpec().exitCodeOnExecutionException());
        }
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}, AND applies filtering!
     * @param config The configuration of the exporter
     */
    public void exportConversation(ConversationExporterConfiguration config) throws Exception {
        try{
            System.out.println("Exporting conversation...");

            Conversation conversation = new Reader().readConversation(config.inputFilePath);

            // filter conversation
            conversation = new Filterer().filterConversation(conversation, config);

            // if report has be requested, add activity to conversation
            if(config.isReporting != null && config.isReporting) {
                conversation = new Reporter().recordActivity(conversation);
            }

            new Writer().writeConversation(conversation, config.outputFilePath);

            System.out.println("Conversation exported from '" + config.inputFilePath + "' to '" + config.outputFilePath);
        }catch (NullPointerException e) {
            throw new NullPointerException("Configuration of conversation was null.");
        }catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Configuration of conversation was illegal.");
        }
    }
}
