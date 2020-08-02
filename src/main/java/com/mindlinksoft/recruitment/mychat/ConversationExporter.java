package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toMap;


/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {

    /**
     * The application entry point.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath);
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(String inputFilePath, String outputFilePath) {
        Conversation conversation = this.readConversation(inputFilePath);

        this.writeConversation(conversation, outputFilePath);

        // TODO: Add more logging...
        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath + "\nShowing the whole conversation followed by a log of the most active users");
    }


    /**
     * Returns all messages of the specified user
     * @param conversation the conversation
     * @param sender the sender's name
     */
    public void filterBySender(Conversation conversation, String sender) {

        conversation.messages.removeIf(m -> !m.senderId.toLowerCase().equals(sender.toLowerCase()));
    }


    /**
     * Returns all the messages that contain a specific keyword
     * @param conversation the conversation
     * @param keyWord the keyword
     */
    public void filterByKeyword(Conversation conversation, String keyWord) {

        conversation.messages.removeIf(message -> !message.content.toLowerCase().contains(keyWord.toLowerCase()));
    }


    /**
     * Returns the conversation with certain words hidden
     * @param conversation the conversation
     * @param blacklist list of words to be censored
     */
    public void censorBlacklistedWords(Conversation conversation, List<String> blacklist) {

        for (String word: blacklist) {
            conversation.messages.forEach(message -> message.content = message.content.replaceAll(("(?i)"+ Pattern.quote(word)), "*redacted*"));
        }
    }


    /**
     * Tracks and hides the most common formats of mobile and card numbers
     * @param conversation the conversation
     */
    public void hideSensitiveNumbers(Conversation conversation) {

        conversation.messages.forEach(message -> message.content = message.content.replaceAll(("(\\d{3}) (\\d{3}) (\\d+)"), "*redacted*"));
        conversation.messages.forEach(message -> message.content = message.content.replaceAll(("(\\d{3})-(\\d{3})-(\\d+)"), "*redacted*"));
        conversation.messages.forEach(message -> message.content = message.content.replaceAll(("(\\d{4}) (\\d{4}) (\\d{4}) (\\d+)"), "*redacted*"));
        conversation.messages.forEach(message -> message.content = message.content.replaceAll(("(\\d{4})-(\\d{4})-(\\d{4})-(\\d+)"), "*redacted*"));
        conversation.messages.forEach(message -> message.content = message.content.replaceAll(("(\\d{8}\\d+)"), "*redacted*"));
        conversation.messages.forEach(message -> message.content = message.content.replaceAll(("(\\d{5}) (\\d+)"), "*redacted*"));
    }

    /**
     * Hides sender IDs in a cryptographic-like manner. Each encrypted ID retains the relationship with its messages and same IDs give the same encryptions
     * @param conversation the conversation
     */
    public void obfuscateID(Conversation conversation) {

        conversation.messages.forEach(message -> message.senderId = UUID.nameUUIDFromBytes(message.senderId.getBytes()).toString());
    }


    /**
     * Maps every user to their number of messages
     * @param conversation the conversation
     * @return an array with the most active users and their corresponding number of messages
     */
    public Map<String, Long> generateActivityReport(Conversation conversation) {

        Map<String, Long> activityReport = conversation.messages.stream().collect(Collectors.groupingBy(w -> w.senderId, Collectors.counting()));

        return activityReport
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
    }


    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws RuntimeException {
        // TODO: Do we need both to be resources, or will buffered writer close the stream? YES IT WILL
        try (OutputStream os = new FileOutputStream(outputFilePath, true);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

            // TODO: Maybe reuse this? Make it more testable...
            GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

            Gson g = gsonBuilder.create();

            bw.write(g.toJson(conversation));
            bw.newLine();
            bw.write(g.toJson(generateActivityReport(conversation)));
        } catch (FileNotFoundException e) {
            // TODO: Maybe include more information?
            throw new IllegalArgumentException("The file was not found. Try adding the file extension (eg .txt)");
        } catch (IOException e) {
            // TODO: Should probably throw different exception to be more meaningful :/
            throw new RuntimeException("Failed to close stream",e);
        }
    }


    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation readConversation(String inputFilePath) throws RuntimeException {
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {


            List<Message> messages = new ArrayList<>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ", 3);

                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            throw new RuntimeException("Failed to close stream",e);
        }
    }

    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
