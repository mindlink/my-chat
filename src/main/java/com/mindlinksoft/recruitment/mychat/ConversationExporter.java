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

    private String filterUserId;
    private String filterKeyword;
    private List<String> blacklist;

    /**
     * The application entry point.
     * @param args The command line arguments.
     * //@throws Exception Thrown when something bad happens.
     */
    //public static void main(String[] args) throws Exception {
    public static void main(String[] args) {
        // We use picocli to parse the command line - see https://picocli.info/
        ConversationExporterConfiguration configuration = new ConversationExporterConfiguration();
        CommandLine cmd = new CommandLine(configuration);

        try
        {
            ParseResult parseResult = cmd.parseArgs(args);
            List<String> options = parseResult.originalArgs();
            if (parseResult.isUsageHelpRequested())
            {
                cmd.usage(cmd.getOut());
                System.exit(cmd.getCommandSpec().exitCodeOnUsageHelp());
                return;
            }
            
            if (parseResult.isVersionHelpRequested())
            {
                cmd.printVersionHelp(cmd.getOut());
                System.exit(cmd.getCommandSpec().exitCodeOnVersionHelp());
                return;
            }

            ConversationExporter exporter = new ConversationExporter();
            if(parseResult.hasMatchedOption(configuration.userOpt)) {
                exporter.setFilterUserId(configuration.filterUserId);
            }
            else if(parseResult.hasMatchedOption(configuration.filterKeyword)) {
                exporter.setFilterKeyword(configuration.filterKeyword);
            }
            else if(parseResult.hasMatchedOption(configuration.blacklistOpt)) {
                exporter.setBlacklist(configuration.blacklist);
            }

            exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath);

            System.exit(cmd.getCommandSpec().exitCodeOnSuccess());
        } catch (ParameterException ex) {
            cmd.getErr().println(ex.getMessage());
            if (!UnmatchedArgumentException.printSuggestions(ex, cmd.getErr())) {
                ex.getCommandLine().usage(cmd.getErr());
            }

            System.exit(cmd.getCommandSpec().exitCodeOnInvalidInput());
        } catch (Exception ex)
        {
            ex.printStackTrace(cmd.getErr());
            System.exit(cmd.getCommandSpec().exitCodeOnExecutionException());
        }
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(String inputFilePath, String outputFilePath) throws Exception {
        Conversation conversation = this.readConversation(inputFilePath);

        this.writeConversation(conversation, outputFilePath);

        if(filterUserId !=null) {
            System.out.println("Showing messages with userId:" + filterUserId);
        }
        else if(filterKeyword !=null) {
            System.out.println("Showing messages with keyword:" + filterKeyword);
        }else if(blacklist != null)
        {
            String msg = "Hiding messages with following word(s):";
            StringBuilder sb = new StringBuilder(msg);
            int i = 0;
            for(String word: blacklist) {
                if(i == 0){
                    String str = " "+ word;
                    sb.append(str);
                }
                if(i > 0){
                    String str = ", "+ word;
                    sb.append(str);
                }
                i++;
            }
            System.out.println(msg + sb.toString());
        }
        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws Exception {
        try (OutputStream os = new FileOutputStream(outputFilePath, false);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os)))
        {
            Conversation c = configureConversation(conversation);
            // TODO: Maybe reuse this? Make it more testable...
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
            gsonBuilder.setPrettyPrinting();
            gsonBuilder.disableHtmlEscaping(); //TODO: should I use this? May be security flaw
            Gson g = gsonBuilder.create();
            String ob = g.toJson(c);
            bw.write(ob);
        } catch (FileNotFoundException e) {
            // TODO: Maybe include more information?
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            // TODO: Should probably throw different exception to be more meaningful :/
            throw new Exception("Something went wrong");
        }
    }

    /**
     * configures the conversation according to the command-line arguments
     * If there are no optional arguments used, the original conversation will be passed
     * @param c The original un-edited conversation
     * @return The edited conversation (if optional arguments used)
     */
    private Conversation configureConversation(Conversation c)
    {
        ConversationBuilder cb = new ConversationBuilder(c);
        if(filterUserId !=null) {
            cb.filterByUser(filterUserId);
        }
        else if(filterKeyword !=null) {
            cb.filterByKeyword(filterKeyword);
        }else if(blacklist != null)
        {
            for(String word: blacklist) {
                cb.blacklistWord(word);
            }
        }
        return cb.build();
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

            while ((line = r.readLine()) != null)
            {
                String[] split = line.split(" ", 3);
                StringBuilder sb = new StringBuilder(split[2]);
                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            throw new Exception("Something went wrong");
        }
    }

    //Getters and setters for arguments
    public String getFilterUserId()
    {
        return this.filterUserId;
    }
    public void setFilterUserId(String filterUserId)
    {
        this.filterUserId = filterUserId;
    }
    public String getFilterKeyword()
    {
        return this.filterUserId;
    }
    public void setFilterKeyword(String filterKeyword)
    {
        this.filterKeyword = filterKeyword;
    }
    public List<String> getBlacklist()
    {
        return this.blacklist;
    }
    public void setBlacklist(List<String> blacklist)
    {
        this.blacklist = blacklist;
    }

    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
