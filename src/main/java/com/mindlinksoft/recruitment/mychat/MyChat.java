package com.mindlinksoft.recruitment.mychat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import picocli.CommandLine;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.ParseResult;
import picocli.CommandLine.UnmatchedArgumentException;

public class MyChat {
    public static final Logger logger = LogManager.getLogger(ConversationExporter.class);

    /**
     * The application entry point.
     * 
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
                logger.info("Help requested by user with the '--help' command");
                cmd.usage(cmd.getOut());
                System.exit(cmd.getCommandSpec().exitCodeOnUsageHelp());
                return;
            }

            if (parseResult.isVersionHelpRequested()) {
                logger.info("Version requested by user with the '--version' command");
                cmd.printVersionHelp(cmd.getOut());
                System.exit(cmd.getCommandSpec().exitCodeOnVersionHelp());
                return;
            }

            ConversationExporter exporter = new ConversationExporter();
            logger.trace("Conversation Exporter started");
            exporter.exportConversation(configuration);
            logger.trace("Conversation Exporter ended");

            System.exit(cmd.getCommandSpec().exitCodeOnSuccess());
        } catch (ParameterException e) {
            logger.error("Unexpected error occured concerning the parameter(s). With error message: " + e.getMessage());
            if (!UnmatchedArgumentException.printSuggestions(e, cmd.getErr())) {
                e.getCommandLine().usage(cmd.getErr());
            }

            System.exit(cmd.getCommandSpec().exitCodeOnInvalidInput());
        } catch (Exception e) {
            logger.error("Unexpected error occured concerning the parameter(s). With error message: " + e.getMessage()
                    + " & " + cmd.getErr());
            System.exit(cmd.getCommandSpec().exitCodeOnExecutionException());
        }
    }

}
