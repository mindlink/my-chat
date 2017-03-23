package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.io.IOUtils;
import com.mindlinksoft.recruitment.mychat.model.Conversation;
import com.mindlinksoft.recruitment.mychat.model.Message;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {

    private static final Logger logger = Logger
            .getAnonymousLogger();

    /**
     * The application entry point.
     * @param args The command line arguments.
     * @throws IOException Thrown when something bad happens.
     */
    public static void main(String[] args) throws IOException {
        ConversationExporter exporter = new ConversationExporter();
        // ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        // Deprecating this. Using apache commons-cli to parse the cli args
        CLIExtractor CLIExtractor = new CLIExtractor(args).invoke();
        if (CLIExtractor.noValidArgs()) return;
        CommandLine cmd = CLIExtractor.getCmd();

        String inputFilePath = cmd.getOptionValue("input");
        String outputFilePath = cmd.getOptionValue("output");
        String keyword = cmd.getOptionValue("keyword");
        String user = cmd.getOptionValue("user");
        String[] blacklist = cmd.getOptionValues("blacklist");

        exporter.exportConversation(inputFilePath, outputFilePath, keyword, user, blacklist);
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param keyword
     * @param user
     * @param blacklist
     * @throws IOException Thrown when something bad happens.
     */
    public void exportConversation(String inputFilePath, String outputFilePath, String keyword, String user, String[] blacklist) throws IOException {
        Conversation conversation = IOUtils.readConversation(inputFilePath, keyword, user, blacklist);

        String messageLog = "Trying to export " + conversation.name + " from '" + inputFilePath + "' to '" + outputFilePath + "'\nHere is the input:\n";

        for (Message message : IOUtils.readConversation(inputFilePath, null, null, null).messages) {
            messageLog += message.senderId + " " + message.content + "\n";
        }
        logger.info(messageLog);

        IOUtils.writeConversation(conversation, outputFilePath);
    }

    static class CLIExtractor {
        private boolean myResult;
        private String[] args;
        private CommandLine cmd;

        public CLIExtractor(String... args) {
            this.args = args;
        }

        boolean noValidArgs() {
            return myResult;
        }

        public CommandLine getCmd() {
            return cmd;
        }

        public CLIExtractor invoke() {
            Options options = new Options();
            Option input = new Option("i", "input", true, "input file path");
            input.setRequired(true);
            options.addOption(input);

            Option output = new Option("o", "output", true, "output file path");
            output.setRequired(true);
            options.addOption(output);

            Option user = new Option("u", "user", true, "filter by user");
            options.addOption(user);

            Option keyword = new Option("k", "keyword", true, "filter by keyword");
            options.addOption(keyword);

            Option blacklist = new Option("b", "blacklist", true, "hide blacklisted words");
            options.addOption(blacklist);

            CommandLineParser parser = new DefaultParser();
            HelpFormatter formatter = new HelpFormatter();

            try {
                cmd = parser.parse(options, args);
            } catch (ParseException e) {
                System.out.println(e.getMessage());
                formatter.printHelp("Mindlink's Chat", options);

                System.exit(1);
                myResult = true;
                return this;
            }
            myResult = false;
            return this;
        }
    }
}
