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
     * The commands are accessed by using the key commands at the end of an command in argument
     * '*' activates the User filter function e.g 'bob*'
     * '!' activates the Keyword Filter function e.g 'pie!'
     * '#' activates the Blacklist function e.g 'pie#'
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        char command = '\0';

        if (args.length != 0){
            command = args[0].charAt(args[0].length() - 1);
        }

        if (command == '*') {
            exporter.exportConversationUserFilter(configuration.inputFilePath, configuration.outputFilePath, args);
        }
        else if (command == '!') {
            exporter.exportConversationKeyword(configuration.inputFilePath, configuration.outputFilePath, args);
        }
        else if (command == '#') {
            exporter.exportConversationBlacklist(configuration.inputFilePath, configuration.outputFilePath, args);
        }
        else {
            exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath);
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

        // TODO: Add more logging...
        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
        System.out.println("The Conversation is stored in JSON file format.");
        System.out.println("This Conversation has no commands applied to it.");

    }
    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath} with a user filter command-line argument.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversationUserFilter(String inputFilePath, String outputFilePath, String[] args) throws Exception {
        Conversation conversation = this.filterByUser(inputFilePath, args);
        String user = args[0].substring(0, args[0].length() - 1);

        this.writeConversation(conversation, outputFilePath);

        // TODO: Add more logging...
        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
        System.out.println("The Conversation is stored in JSON file format.");
        System.out.println("The Conversation is filtered using the user named:" + user);
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath} with a user filter command-line argument.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversationKeyword(String inputFilePath, String outputFilePath, String[] args) throws Exception {
        Conversation conversation = this.filterByWord(inputFilePath, args);
        String keyword = args[0].substring(0, args[0].length() - 1);


        this.writeConversation(conversation, outputFilePath);

        // TODO: Add more logging...
        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
        System.out.println("The Conversation is stored in JSON file format.");
        System.out.println("The Conversation is filtered using the keyword:" + keyword);
    }

    public void exportConversationBlacklist(String inputFilePath, String outputFilePath, String[] args) throws Exception {
        Conversation conversation = this.blackListWord(inputFilePath, args);
        String blacklistedWord = args[0].substring(0, args[0].length() - 1);


        this.writeConversation(conversation, outputFilePath);

        // TODO: Add more logging...
        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
        System.out.println("The Conversation is stored in JSON file format.");
        System.out.println("The word:" + blacklistedWord + ", is blacklisted in this Conversation.");
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws Exception {
        // TODO: Do we need both to be resources, or will buffered writer close the stream?
        try (OutputStream os = new FileOutputStream(outputFilePath, true);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

            // TODO: Maybe reuse this? Make it more testable...
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Instant.class, new InstantSerializer())
                    .setPrettyPrinting().create();

            bw.write(gson.toJson(conversation));
        } catch (FileNotFoundException e) {
            // TODO: Maybe include more information?
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            // TODO: Should probably throw different exception to be more meaningful :/
            e.printStackTrace();
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
                String[] split = line.split(" ", 3);
                String timestamp = split[0];
                String user = split[1];
                String message = split[2];

                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(timestamp)), user, message));
            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            throw new Exception("Something went wrong.");
        }
    }

    /**
     * Represents a helper to filter a user in a conversation from the given {@code inputFilePath}
     * @param inputFilePath The path to the input file
     * @param args The user command-in argument
     * @return The {@link Conversation} representing by the input file
     * @throws Exception Thrown when something bad happens
     */
    public Conversation filterByUser(String inputFilePath, String[] args) throws Exception {
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<>();

            String conversationName = r.readLine();
            String line;
            String userFilter = args[0].substring(0, args[0].length() - 1);

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ", 3);
                String timestamp = split[0];
                String user = split[1];
                String message = split[2];

                if (user.equals(userFilter)) {
                    messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(timestamp)), user, message));
                }
            }

            if (messages.isEmpty()) {
                throw new Exception("Name does not exist.");
            }
            return new Conversation(conversationName, messages);

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            throw new Exception("Something went wrong.");
        }
    }

    /**
     * Represents a helper to filtered a specific word in a conversation from the given {@code inputFilePath}
     * @param inputFilePath The path to the input file
     * @param args The Keyword command-in argument
     * @return The {@link Conversation} representing by the input file
     * @throws Exception Thrown when something bad happens
     */

    public Conversation filterByWord(String inputFilePath, String[] args) throws Exception {
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<>();

            String conversationName = r.readLine();
            String line;
            String wordFilter = args[0].substring(0, args[0].length() - 1);

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ", 3);
                String currentMessage = split[2];
                String[] words = currentMessage.split(" ");
                String lastWord = words[words.length - 1];
                lastWord = lastWord.substring(0, lastWord.length() - 1);

                String timestamp = split[0];
                String user = split[1];

                if (currentMessage.contains(wordFilter)) {
                    messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(timestamp)), user, currentMessage));
                }

            }

            if (messages.isEmpty()) {
                throw new Exception("Word does not exist.");
            }
            return new Conversation(conversationName, messages);

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            throw new Exception("Something went wrong.");
        }
    }

    /**
     * Represents a helper to blacklist a specific word in a conversation from the given {@code inputFilePath}
     * @param inputFilePath The path to the input file
     * @param args The black-list command-in argument
     * @return The {@link Conversation} representing by the input file
     * @throws Exception Thrown when something bad happens
     */

    public Conversation blackListWord(String inputFilePath, String[] args) throws Exception {
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<>();

            String conversationName = r.readLine();
            String line;
            String wordFilter = args[0].substring(0, args[0].length() - 1);

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ", 3);
                String currentMessage = split[2];
                String[] words = currentMessage.split(" ");

                String endWord = words[words.length - 1];
                String lastWord = endWord.substring(0, endWord.length() - 1);
                Character punct = endWord.charAt(endWord.length() - 1);

                String timestamp = split[0];
                String user = split[1];

                if (lastWord.equals(wordFilter)) {
                        String newMessage = currentMessage.replace(endWord, "*redacted*" + punct);
                        newMessage = newMessage.replaceAll(lastWord, "*redacted");
                        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(timestamp)), user, newMessage));
                    }

                else if (currentMessage.contains(wordFilter)) {
                    String newMessage = currentMessage.replaceAll(wordFilter, "*redacted*");
                    messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(timestamp)), user, newMessage));
                }
                else {
                    messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(timestamp)), user, currentMessage));
                }
            }
            return new Conversation(conversationName, messages);

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            throw new Exception("Something went wrong.");
        }
    }



    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
