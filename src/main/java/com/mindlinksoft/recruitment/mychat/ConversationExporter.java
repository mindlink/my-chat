package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

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
        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        
        //exporter.exportConversation(configuration.getInputFilePath(), configuration.getOutputFilePath());
        //exporter.exportConversationFilteredByUsername(configuration.getInputFilePath(), configuration.getOutputFilePath(), configuration.getUsername());
        //exporter.exportConversationFilteredByKeyword(configuration.getInputFilePath(), configuration.getOutputFilePath(), configuration.getKeyword());
        exporter.exportConversationBlacklisted(configuration.getInputFilePath(), configuration.getOutputFilePath(), configuration.getBlacklisted());
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

        // TODO: Add more logging...
        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
    }
    
    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath} based on a username.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param username The username.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversationFilteredByUsername(String inputFilePath, String outputFilePath, String username) throws Exception {
        Conversation conversation = this.readUsersConversation(inputFilePath, username);
        
        this.writeConversation(conversation, outputFilePath);
        
        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
        System.out.println("Conversation exported for user '" + username);
    }
    
     /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath} based on a username.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param keyword The keyword.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversationFilteredByKeyword(String inputFilePath, String outputFilePath, String keyword) throws Exception {
        Conversation conversation = this.readKeywordsConversation(inputFilePath, keyword);
        
        this.writeConversation(conversation, outputFilePath);
        
        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
        System.out.println("Conversation exported filtered by keyword '" + keyword);
    }
    
    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath} based on a username.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param blacklisted The list of blacklisted words.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversationBlacklisted(String inputFilePath, String outputFilePath, List<String> blacklisted) throws Exception {
        Conversation conversation = this.readBlacklistedConversation(inputFilePath, blacklisted);
        
        this.writeConversation(conversation, outputFilePath);
        
        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
        System.out.println("Conversation exported hiding the selected blacklisted keywords");
    }
    
    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws Exception {
        // TODO: Do we need both to be resources, or will buffered writer close the stream?
        /*
            In Java SE 7 and later, implements the interface java.lang.AutoCloseable. 
            Because the BufferedReader instance is declared in a try-with-resource statement, 
            it will be closed regardless of whether the try statement completes normally or abruptly 
        */
        
        try (OutputStream os = new FileOutputStream(outputFilePath, true);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

            // TODO: Maybe reuse this? Make it more testable...
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

            Gson g = gsonBuilder.create();

            bw.write(g.toJson(conversation));
        } catch (FileNotFoundException e) {
            // TODO: Maybe include more information?
            throw new IllegalArgumentException("The file was not found. Check the file name and try again.");
        } catch (IOException e) {
            // TODO: Should probably throw different exception to be more meaningful :/
            throw new Exception("Something went wrong, the writing operations was interupted.");
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
                String content = "";
                int i = 0; 
                for(String word : split){
                    if (!(i == 0 || i==1)) {
                        if (i == split.length-1){
                            content += word;
                        } else {
                            content += word + " ";
                        }
                    }
                    i++;
                }
                
                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], content));
            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            throw new Exception("Something went wrong");
        }
    }
    
    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath & username}.
     * @param inputFilePath The path to the input file.
     * @param username The username for filtering.
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation readUsersConversation(String inputFilePath, String username) throws Exception {
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" "); 
                
                Instant timestamp = Instant.ofEpochSecond(Long.parseUnsignedLong(split[0]));
                String senderId = split[1];
                String content = "";
                int i = 0; 
                for(String word : split){
                    if (!(i == 0 || i==1)) {
                        if (i == split.length-1){
                            content += word;
                        } else {
                            content += word + " ";
                        }
                    }
                    i++;
                }
                
                if (senderId.equals(username)){
                    messages.add(new Message(timestamp, senderId, content));
                }
            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            throw new Exception("Something went wrong");
        }
    }
    
    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath & keyword}.
     * @param inputFilePath The path to the input file.
     * @param keyword for filtering.
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation readKeywordsConversation(String inputFilePath, String keyword) throws Exception {
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" "); 
                
                Instant timestamp = Instant.ofEpochSecond(Long.parseUnsignedLong(split[0]));
                String senderId = split[1];
                String content = "";
                int i = 0; 
                for(String word : split){
                    if (!(i == 0 || i==1)) {
                        if (i == split.length-1){
                            content += word;
                        } else {
                            content += word + " ";
                        }
                    }
                    i++;
                }
                
                String[] contentWords = content.split(" ");
                for(String word : contentWords){
                    if (word.equals(keyword)) {
                        messages.add(new Message(timestamp, senderId, content));
                    }
                }
            }
            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            throw new Exception("Something went wrong");
        }
    }
    
    
    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath & keyword}.
     * @param inputFilePath The path to the input file.
     * @param blacklisted keywords.
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation readBlacklistedConversation(String inputFilePath, List<String> blacklisted) throws Exception {
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" "); 
                
                Instant timestamp = Instant.ofEpochSecond(Long.parseUnsignedLong(split[0]));
                String senderId = split[1];
                String content = "";
                int i = 0; 
                for(String word : split){
                    if (!(i == 0 || i==1)) {
                            if (i == split.length-1){
                                if (blacklisted.contains(word)) {
                                    content += "*redacted*";
                                } else {
                                    content += word;
                                }
                            } else {
                                if (blacklisted.contains(word)) {
                                    content += "*redacted* ";
                                } else {
                                    content += word + " ";
                                }
                            }
                    }
                    i++;
                }
                messages.add(new Message(timestamp, senderId, content));

            }
            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            throw new Exception("Something went wrong");
        }
    }

    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
