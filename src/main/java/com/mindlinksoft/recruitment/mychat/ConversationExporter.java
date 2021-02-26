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
import java.util.Arrays;
import java.util.List;

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
     * @param configuration The configuration which contains the paramters for exportation.
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
        long startTime;
        double exportTime; // time taken to export conversation

        // start timing exportation
        startTime = System.nanoTime();

        // read conversation from file
        Conversation conversation = readConversation(inputFilePath);
        // filter conversation based on specified parameters (user, keyword, blacklisted words)
        filterConversation(conversation);
        // track conversation activity if a report is specified
        if (this.report) {
            conversation.trackActivity();
        }
        // write to JSon file
        writeConversation(conversation, outputFilePath);

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
        System.out.println("Exportation took " + exportTime + " milliseconds to execute.");
    }


    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws Exception {
        try (OutputStream os = new FileOutputStream(outputFilePath, false);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

            Gson g = gsonBuilder.create();
            bw.write(g.toJson(conversation));

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Cannot find file named \'" + outputFilePath + "\'.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Output buffer size is ef illegal value. Must be a non-zero value");
        } catch (IOException e) {
            throw new IOException("An I/O error has occurred.");
        }
    }


    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation readConversation(String inputFilePath) throws Exception {
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ");
                StringBuilder content = new StringBuilder();

                // adds the rest of the splits to the message content
                for (int i = 2; i < split.length; i++) {
                    content.append(split[i]);
                    // adds spaces in between all the splits (makes sure an extra one isn't added to the end)
                    if (i < split.length - 1) {
                        content.append(" ");
                    }
                }

                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], content.toString()));
            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Cannot find file named \'" + inputFilePath + "\'.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Input buffer size is ef illegal value. Must be a non-zero value");
        } catch (IOException e) {
            throw new IOException("An I/O error has occurred.");
        }
    }


    /**
     * Filters conversation content based on the filter parameters (user, keyword, blacklist)
     * @param conversation The conversation to be filtered.
     */
    public void filterConversation(Conversation conversation) {
        ConversationFilterer filterer = new ConversationFilterer();

        // if a user is specified, filter conversation to only feature messages by the specified user
        if (this.user != null) {
            filterer.filterConversationByUser(conversation, this.user);
        }

        // if a keyword is specified, filter conversation to only feature messages containing the specified keyword
        if (this.keyword != null) {
            filterer.filterConversationByKeyword(conversation, this.keyword);
        }

        // if blacklisted words are specified, redact blacklisted words
        if (this.blacklistedWords != null) {
            filterer.redactBlacklistedWords(conversation, this.blacklistedWords);
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

    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
