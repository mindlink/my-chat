package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.security.KeyStore.Entry;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.nio.charset.StandardCharsets;;

/**
 * Represents a conversation exporter that can read a conversation and write it
 * out in JSON.
 */
public class ConversationExporter {

    /**
     * The application entry point.
     * 
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser()
                .parseCommandLineArguments(args);

        exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, configuration.userFilter,
                configuration.keyword, configuration.redacted, configuration.credit, configuration.obfuscate,
                configuration.count);
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to
     * {@code outputFilePath}.
     * 
     * @param inputFilePath  The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(String inputFilePath, String outputFilePath, String userFilter, String keyword,
            String redacted, Boolean credit, Boolean obfuscate, Boolean count) throws Exception {
        Conversation conversation = this.readConversation(inputFilePath);

        // FILTER BY USER IF USERFILTER IS NOT NULL
        if (!userFilter.isEmpty()) {
            System.out.println("filtering by user: " + userFilter);
            conversation = new Conversation(conversation.name, filterByUser(conversation, userFilter));
        }

        // FILTER BY KEYWORD IF KEYWORD IS NOT NULL
        if (!keyword.isEmpty()) {
            System.out.println("filtering keyword: " + keyword);
            conversation = new Conversation(conversation.name, filterByKeyword(conversation, keyword));
        }

        // REDACT CONTENT
        if (!redacted.isEmpty()) {
            System.out.println("redacting: " + redacted);
            conversation = new Conversation(conversation.name, redactContent(conversation, redacted));
        }

        // REDACT CREDITCARD AND PHONE INFO
        if (credit) {
            System.out.println("redacting credit card and phone numbers");
            conversation = new Conversation(conversation.name, redactCreditPhone(conversation));
        }

        // OBFUSCATE USER IDS
        if (obfuscate) {
            System.out.println("obfuscating userIDs");
            conversation = new Conversation(conversation.name, obfuscateUsers(conversation));
        }

        // ADD USER LEADER BOARD
        if (count) {
            System.out.println("adding counter to export");
            conversation.sortedMostActiveUsers = countActivity(conversation);
        }

        this.writeConversation(conversation, outputFilePath);

        // TODO: Add more logging...
        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
    }

    /**
     * Returns a new Collection of Messages that were sent by a specific user
     * 
     * @param conversation
     * @param outputFilePath
     * @throws Exception
     */
    public Collection<Message> filterByUser(Conversation conversation, String userFilter) {

        Predicate<Message> filter = item -> item.senderId.equals(userFilter);
        Collection<Message> filteredCollection = conversation.messages;

        return filteredCollection.stream().filter(filter).collect(Collectors.toList());
    }

    /**
     * Returns a new Collection of Messages that contain a keyword
     * 
     * @param conversation
     * @param keyword
     * @return
     */
    public Collection<Message> filterByKeyword(Conversation conversation, String keyword) {

        Predicate<Message> filter = item -> item.content.contains(keyword);
        Collection<Message> filteredCollection = conversation.messages;

        return filteredCollection.stream().filter(filter).collect(Collectors.toList());
    }

    /**
     * Returns a new Collection of Messages with redacted content
     * 
     * @param conversation
     * @param redacted
     * @return
     */
    public Collection<Message> redactContent(Conversation conversation, String redacted) {

        Collection<Message> messages = conversation.messages;

        String[] redactedList = redacted.split(",");
        for (String redactedword : redactedList) {
            for (Message message : messages) {
                message.content = message.content.replace(redactedword, "*redacted*");
            }
        }

        return messages;
    }

    /**
     * Returns a new Collection of Messages with redacted VISA style credit cards
     * and UK mobile phone numbers
     * 
     * @param conversation
     * @throws Exception
     */
    public Collection<Message> redactCreditPhone(Conversation conversation) {

        Collection<Message> messages = conversation.messages;
        String regexCC = "((?:(?:\\d{4}[- ]){3}\\d{4}|\\d{16}))(?![\\d])";
        String regexPH = "(\\+44\\s?7\\d{3}|\\(?07\\d{3}\\)?)\\s?\\d{3}\\s?\\d{3}";

        for (Message message : messages) {
            message.content = message.content.replaceAll(regexCC, "*redacted*");
            message.content = message.content.replaceAll(regexPH, "*redacted*");
        }
        return messages;
    }

    /**
     * Obfuscate user IDs
     * 
     * @param conversation
     * @throws Exception
     */
    public Collection<Message> obfuscateUsers(Conversation conversation) {
        Collection<Message> messages = conversation.messages;
        ArrayList<String> users = new ArrayList<>();

        for (Message message : messages) {
            // check if know the userID
            if (!users.contains(message.senderId)) {
                users.add(message.senderId);
                message.senderId = "User" + users.indexOf(message.senderId);
            } else {
                message.senderId = "User" + users.indexOf(message.senderId);
            }
        }

        System.out.println(users);
        return messages;
    }

    /**
     * Count messages to determine active users
     * 
     * @param conversation
     * @throws Exception
     */
    public ArrayList<String> countActivity(Conversation conversation) {
        Collection<Message> messages = conversation.messages;
        ArrayList<String> sortedUsers = new ArrayList<String>();
        Map<String, Integer> map = new HashMap<String, Integer>();
        // populate map
        for (Message message : messages) {
            if (map.containsKey(message.senderId)) {
                map.put(message.senderId, map.get(message.senderId) + 1);
            } else {
                map.put(message.senderId, 1);
            }
        }

        // sort map
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(map.entrySet());
        // create custom comparator
        Comparator comparator = new Comparator<Map.Entry<String, Integer>>() {

            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                // TODO Auto-generated method stub
                return o1.getValue().compareTo(o2.getValue());
            }

        };
        Collections.sort(entries, comparator.reversed());

        // format output
        for (Map.Entry<String, Integer> entry : entries) {
            sortedUsers.add(entry.getKey());
        }

        System.out.println(sortedUsers);
        return sortedUsers;
    }

    /** helper method to sort map by value */
    private void sortByValue(Map<String, Integer> unsortedMap) {

    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given
     * {@code outputFilePath}.
     * 
     * @param conversation   The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws Exception {

        try {
            // gson builder
            GsonBuilder gsonBuilder = new GsonBuilder().setLenient();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
            Gson g = gsonBuilder.create();

            // file writer
            Writer writer = new OutputStreamWriter(new FileOutputStream(outputFilePath), StandardCharsets.UTF_8);
            g.toJson(conversation, writer);
            writer.flush();
            writer.close();

        } catch (Exception e) {
            System.out.println("Error occured during file writing" + e.toString());
        }

    }

    /**
     * Represents a helper to read a conversation from the given
     * {@code inputFilePath}.
     * 
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation readConversation(String inputFilePath) throws Exception {
        try (InputStream is = new FileInputStream(inputFilePath);
                BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ");

                Instant time = Instant.ofEpochSecond(Long.parseUnsignedLong(split[0]));
                String sender = split[1];
                String[] contentArray = Arrays.copyOfRange(split, 2, split.length);
                String content = String.join(" ", contentArray);

                messages.add(new Message(time, sender, content));
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
