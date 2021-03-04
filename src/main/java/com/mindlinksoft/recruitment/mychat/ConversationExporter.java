package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.ConversationFilterers.BlacklistedWordFilterer;
import com.mindlinksoft.recruitment.mychat.ConversationFilterers.KeywordFilterer;
import com.mindlinksoft.recruitment.mychat.ConversationFilterers.UserFilterer;
import picocli.CommandLine;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.ParseResult;
import picocli.CommandLine.UnmatchedArgumentException;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {

    private String user;                // user whose messages are to be output
    private String keyword;             // keyword whose containing messages are to be output
    private String[] blacklistedWords;  // words to be redacted in the output
    private boolean report;             // flag determining whether or not to add the messaging activity to the output

    /**
     * Run method from which the rest of the exporter starts
     */
    public void run(String[] args) {
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

            // get parameters from command line
            getCLIParameters(configuration);
            // start exporting conversation
            exportConversation(configuration.inputFilePath, configuration.outputFilePath);

            System.exit(cmd.getCommandSpec().exitCodeOnSuccess());
        } catch (ParameterException e) {
            cmd.getErr().println(e.getMessage());
            if (!UnmatchedArgumentException.printSuggestions(e, cmd.getErr())) {
                e.getCommandLine().usage(cmd.getErr());
            }

            System.exit(cmd.getCommandSpec().exitCodeOnInvalidInput());
        } catch (Exception e) {
            e.printStackTrace(cmd.getErr());
            System.exit(cmd.getCommandSpec().exitCodeOnExecutionException());
        }
    }


    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param configuration The configuration which contains the parameters for exportation.
     */
    public void getCLIParameters(ConversationExporterConfiguration configuration) {
        // stores the arguments
        this.user = configuration.user;
        this.keyword = configuration.keyword;
        this.blacklistedWords = configuration.blacklistedWords;
        this.report = configuration.report;
    }


    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}. Holds the sequence of stages of exportation.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(String inputFilePath, String outputFilePath) throws Exception {
        ConversationExporterIO exporterIO = new ConversationExporterIO();
        long startTime;
        double exportTime; // time taken to export conversation

        // start timing exportation
        startTime = System.nanoTime();

        // read conversation from file
        Conversation conversation = exporterIO.readConversation(inputFilePath);

        // filter conversation based on specified parameters (user, keyword, blacklisted words)
        conversation = filterConversation(conversation);

        // track conversation activity if a report is specified
        checkReport(conversation);

        // write to JSon file
        exporterIO.writeConversation(conversation, outputFilePath);

        // finish timing exportation and convert from nanoseconds to milliseconds
        exportTime = ((double) (System.nanoTime() - startTime)) / 1_000_000.0;

        // report the completion of the exportation
        reportExportCompletion(inputFilePath, outputFilePath, exportTime);
    }


    /**
     * Reports on the completion of the exportation
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param exportTime The time (in milliseconds) over which the exportation took place.
     */
    public void reportExportCompletion(String inputFilePath, String outputFilePath, double exportTime) {
        System.out.println("\nConversation exported from '" + inputFilePath + "' to '" + outputFilePath + "'.");
        System.out.println("Time taken: " + exportTime + " milliseconds");
    }


    /**
     * Filters conversation content based on the filter parameters (user, keyword, blacklist)
     * @param conversation The conversation to be filtered.
     */
    public Conversation filterConversation(Conversation conversation) {
        // if a user is specified, filter conversation to only feature messages by the specified user
        if (this.user != null) {
            UserFilterer userFilterer = new UserFilterer();
            userFilterer.setUser(this.user);
            conversation = userFilterer.filter(conversation);
        }

        // if a keyword is specified, filter conversation to only feature messages containing the specified keyword
        if (this.keyword != null) {
            KeywordFilterer keywordFilterer = new KeywordFilterer();
            keywordFilterer.setKeyword(this.keyword);
            conversation = keywordFilterer.filter(conversation);
        }

        // if blacklisted words are specified, redact blacklisted words
        if (this.blacklistedWords != null) {
            BlacklistedWordFilterer blacklistedWordFilterer = new BlacklistedWordFilterer();
            blacklistedWordFilterer.setBlacklistedWords(this.blacklistedWords);
            conversation = blacklistedWordFilterer.filter(conversation);
        }

        return conversation;
    }

    public void checkReport(Conversation conversation) {
        if (this.report) {
            conversation.trackActivity();
            conversation.sortActivity();
        }
    }

    public String getUser() {
        return user;
    }

    public String getKeyword() {
        return keyword;
    }

    public String[] getBlacklistedWords() {
        return blacklistedWords;
    }

    public boolean isReport() {
        return report;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setBlacklistedWords(String[] blacklistedWords) {
        this.blacklistedWords = blacklistedWords;
    }

    public void setReport(boolean report) {
        this.report = report;
    }
}
