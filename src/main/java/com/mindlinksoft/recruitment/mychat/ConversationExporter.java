package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import com.mindlinksoft.recruitment.mychat.Model.Conversation;
import com.mindlinksoft.recruitment.mychat.Model.Message;

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

    /**
     * The application entry point.
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception {
        // We use picocli to parse the command line - see https://picocli.info/

        // for(String s : args){
        //     System.out.println(s);
        // }


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

            exporter.exportConversation(parseResult);

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
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(String inputFilePath, String outputFilePath) throws Exception {
        Conversation conversation = this.readConversation(inputFilePath);
        
        this.writeConversation(conversation, outputFilePath);

        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath + "'");
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param pr ParseResult containing matched command options.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(ParseResult pr) throws Exception {

        if( !pr.hasMatchedOption("--inputFilePath") ){ 
            throw new Exception("Does not have i/p option");
        }
            
        if( !pr.hasMatchedOption("--outputFilePath") ){
            throw new Exception("Does not have o/p option");
        }
        
        String inputFilePath = pr.matchedOption("--inputFilePath").getValue();
        String outputFilePath = pr.matchedOption("outputFilePath").getValue();
        
        Conversation conversation = this.readConversation(inputFilePath);
        
        IConversationArgumentExecution cae = new ConversationArgumentExecution();
        conversation = cae.executue(conversation, pr);

        this.writeConversation(conversation, outputFilePath);

        System.out.println("Conversation successfully exported from '" + inputFilePath + "' to '" + outputFilePath + "'");
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
            gsonBuilder.setPrettyPrinting();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

            Gson g = gsonBuilder.create();

            bw.write(g.toJson(conversation));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(outputFilePath + " file was not found.");
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("IOException thrown in writing o/p JSON.");
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

            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ");

                //This bit was apparently wrong, the content of the message was only ever the first word.
                messages.add(new Message(
                    Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])),
                    split[1],
                    String.join(" ", Arrays.copyOfRange(split, 2, split.length))
                ));
            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(inputFilePath + " file was not found.");
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("IOException thrown in reading i/p file.");
        }
    }

    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
