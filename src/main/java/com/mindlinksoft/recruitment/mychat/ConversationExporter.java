package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter
{
    /**
     * The application entry point.
     *
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception
    {
        String redact = "*redacted*";
        String wordsToHideSeparator = ",";

        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration config = new CommandLineArgumentParser(wordsToHideSeparator).parseCommandLineArguments(args);
        exporter.exportConversation(config.getInputFilePath(), config.getOutputFilePath(), config.getUser(), config.getKeyword(), config.getWordsToHide(), redact);
        // TODO: Create unit tests to test against empty conversation and other edge cases.
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     *
     * @param inputFilePath  The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(String inputFilePath, String outputFilePath, String user, String keyword, String[] wordsToHide, String redact) throws Exception
    {
        Conversation conversation = this.readConversation(inputFilePath, user, keyword, wordsToHide, redact);
        this.writeConversation(conversation, outputFilePath);
        if (user != null && keyword != null) {
            System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath + ", filtering by (user: " + user + ") and (keyword: " + keyword + ")");
        } else if (user != null) {
            System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath + ", filtering by (user: " + user + ")");
        } else if (keyword != null) {
            System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath + ", filtering by (keyword: " + keyword + ")");
        } else {
            System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
        }
        // TODO: Add more logging for wordsToHide...
    }

    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     *
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation readConversation(String inputFilePath, String user, String keyword, String[] wordsToHide, String redact) throws Exception
    {
        try (BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath)))) {
            List<Message> messages = new ArrayList<>();
            String conversationName = r.readLine();
            String line;
            String sep = " ";
            if (user != null && keyword != null) {
                // Filter by both user and keyword
                while ((line = r.readLine()) != null) {
                    String[] split = line.split(sep);
                    String content = generateContent(split, sep);
                    if (user.equals(split[1].toLowerCase()) && containsKeyword(content, keyword)) {
                        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], content));
                    }
                }
            } else if (user != null) {
                // Filter by just user
                while ((line = r.readLine()) != null) {
                    String[] split = line.split(sep);
                    String content = generateContent(split, sep);
                    if (user.equals(split[1].toLowerCase())) {
                        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], content));
                    }
                }
            } else if (keyword != null) {
                // Filter just by keyword
                while ((line = r.readLine()) != null) {
                    String[] split = line.split(sep);
                    String content = generateContent(split, sep);
                    if (containsKeyword(content, keyword)) {
                        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], content));
                    }
                }
            } else {
                // No user or keyword filter
                while ((line = r.readLine()) != null) {
                    String[] split = line.split(sep);
                    messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], generateContent(split, sep)));
                }
            }
            if (wordsToHide != null) {
                // Redact all wordsToHide in all filtered messages
                // TODO: Add unit tests
                for (Message m : messages) {
                    for (String word : wordsToHide) {
                        m.setContent(m.getContent().replaceAll(word, redact));
                    }
                }
            }
            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            // TODO: Maybe include more information?
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            // TODO: Should probably throw different exception to be more meaningful :/
            throw new Exception("Something went wrong");
        }
    }

    /**
     * Get the entire {@code content} of the {@link Message}.
     * Using all entries in {@code split}, except for the first 2 entries, separated by {@code sep}.
     *
     * @param split The read in line from the {@code inputFilePath}.
     * @return String representing the {@code content} of the {@link Message}.
     */
    private String generateContent(String[] split, String sep)
    {
        StringBuilder content = new StringBuilder();
        boolean first = true;
        for (int i = 2; i < split.length; i++) {
            if (!first) {
                content.append(sep);
                content.append(split[i]);
            } else {
                content.append(split[i]);
                first = false;
            }
        }
        return content.toString();
    }

    /**
     * Check to see if the {@code content} of a {@link Message} contains a given {@code keyword}.
     *
     * @param content The {@code content} of a {@link Message}.
     * @param keyword The {@code keyword} to look for.
     * @return Returns true if {@code content} contains at least one {@code keyword}.
     */
    private boolean containsKeyword(String content, String keyword)
    {
        return Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE).matcher(content).find();
    }

    /**
     * Helper method to write the given {@link Conversation} as JSON to the given {@code outputFilePath}.
     *
     * @param conversation   The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws Exception
    {
        // If outputFilePath exists, delete the file.
        File outputFile = new File(outputFilePath);
        if (outputFile.exists() && outputFile.isFile()) {
            outputFile.delete();
        }
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFilePath, true)))) {
            // TODO: Maybe reuse this? Make it more testable...
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
            Gson g = gsonBuilder.create();
            bw.write(g.toJson(conversation));
        } catch (FileNotFoundException e) {
            // TODO: Maybe include more information?
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            // TODO: Should probably throw different exception to be more meaningful :/
            throw new Exception("Something went wrong");
        }
    }

    class InstantSerializer implements JsonSerializer<Instant>
    {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext)
        {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
