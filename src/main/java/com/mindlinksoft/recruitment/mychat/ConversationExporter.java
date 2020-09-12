package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
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
        String msg = "Conversation exported from '" + inputFilePath + "' to '" + outputFilePath;
        if (user != null && keyword != null && wordsToHide != null) {
            System.out.println(msg + ", filtering by (user: " + user + "), (keyword: " + keyword + ") and redacted (wordsToHide: " + Arrays.toString(wordsToHide) + ")");
        } else if (user != null && keyword != null) {
            System.out.println(msg + ", filtering by (user: " + user + ") and (keyword: " + keyword + ")");
        } else if (user != null && wordsToHide != null) {
            System.out.println(msg + ", filtering by (user: " + user + ") and redacted (wordsToHide: " + Arrays.toString(wordsToHide) + ")");
        } else if (keyword != null && wordsToHide != null) {
            System.out.println(msg + ", filtering by (keyword: " + keyword + ") and redacted (wordsToHide: " + Arrays.toString(wordsToHide) + ")");
        } else if (user != null) {
            System.out.println(msg + ", filtering by (user: " + user + ")");
        } else if (keyword != null) {
            System.out.println(msg + ", filtering by (keyword: " + keyword + ")");
        } else if (wordsToHide != null) {
            System.out.println(msg + ", redacted (wordsToHide: " + Arrays.toString(wordsToHide) + ")");
        } else {
            System.out.println(msg);
        }
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
        String sepRegex = "\\s+";
        String sepJoin = " ";
        String lettersAndSpaces = "[^a-zA-Z ]";
        int contentStartingIndex = 2;

        try (BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath)))) {
            List<Message> messages = new ArrayList<>();
            String conversationName = r.readLine();
            String line;
            if (user != null && keyword != null) {
                // Filter by both user and keyword
                while ((line = r.readLine()) != null) {
                    String[] split = line.split(sepRegex);
                    String content = String.join(sepJoin, getContentSplit(split, contentStartingIndex));
                    if (user.equals(split[1].toLowerCase()) && containsKeyword(content, keyword, sepRegex, lettersAndSpaces)) {
                        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], content));
                    }
                }
            } else if (user != null) {
                // Filter by just user
                while ((line = r.readLine()) != null) {
                    String[] split = line.split(sepRegex);
                    String content = String.join(sepJoin, getContentSplit(split, contentStartingIndex));
                    if (user.equals(split[1].toLowerCase())) {
                        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], content));
                    }
                }
            } else if (keyword != null) {
                // Filter just by keyword
                while ((line = r.readLine()) != null) {
                    String[] split = line.split(sepRegex);
                    String content = String.join(sepJoin, getContentSplit(split, contentStartingIndex));
                    if (containsKeyword(content, keyword, sepRegex, lettersAndSpaces)) {
                        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], content));
                    }
                }
            } else {
                // No user or keyword filter
                while ((line = r.readLine()) != null) {
                    String[] split = line.split(sepRegex);
                    String content = String.join(sepJoin, getContentSplit(split, contentStartingIndex));
                    messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], content));
                }
            }
            if (wordsToHide != null) {
                // Redact all wordsToHide in all filtered messages
                for (Message m : messages) {
                    String content = m.getContent();
                    String[] wordsOrig = content.split(sepRegex);
                    String[] words = content.replaceAll(lettersAndSpaces, "").toLowerCase().split(sepRegex);
                    for (int i = 0; i < words.length; i++) {
                        for (String w : wordsToHide) {
                            if (words[i].equals(w)) {
                                wordsOrig[i] = wordsOrig[i].replaceAll(String.format("(?i)%s", Pattern.quote(words[i])), redact);
                                break;
                            } else if (checkNonLetterSplit(wordsOrig[i], w, lettersAndSpaces)) {
                                String[] subComponents = wordsOrig[i].split(lettersAndSpaces);
                                for (String subComponent : subComponents) {
                                    if (subComponent.equals(w)) {
                                        wordsOrig[i] = wordsOrig[i].replaceAll(String.format("(?i)%s", Pattern.quote(subComponent)), redact);
                                    }
                                }
                            }
                        }
                    }
                    m.setContent(String.join(sepJoin, wordsOrig));
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
     * Using all entries in {@code split}, except for the first {@code startingIndex} entries, separated by {@code sep}.
     *
     * @param split                The read in line from the {@code inputFilePath}.
     * @param contentStartingIndex The index in {@code split} where the {@code content} of a {@link Message} starts.
     * @return String representing the {@code content} of the {@link Message}.
     */
    private String[] getContentSplit(String[] split, int contentStartingIndex)
    {
        String[] contentSplit = new String[split.length - contentStartingIndex];
        if (contentSplit.length >= 0) {
            System.arraycopy(split, contentStartingIndex, contentSplit, 0, contentSplit.length);
        }
        return contentSplit;
    }

    /**
     * Check to see if the {@code content} of a {@link Message} contains a given {@code keyword}.
     *
     * @param content               The {@code content} of a {@link Message}.
     * @param keyword               The {@code keyword} to look for.
     * @param sep                   The separator for splitting up {@code content}.
     * @param lettersAndSpacesRegex Regex for non-letters and non-spaces.
     * @return Returns true if {@code content} contains at least one {@code keyword}.
     */
    private boolean containsKeyword(String content, String keyword, String sep, String lettersAndSpacesRegex)
    {
        String[] wordsOrig = content.split(sep);
        String[] words = content.replaceAll(lettersAndSpacesRegex, "").toLowerCase().split(sep);
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(keyword) || checkNonLetterSplit(wordsOrig[i], keyword, lettersAndSpacesRegex)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if a {@code word} contains any sub words when split by non-letter characters.
     *
     * @param word                  The {@code word} to split by non-letters.
     * @param keyword               The {@code keyword} to match against.
     * @param lettersAndSpacesRegex The {@code regex} for splitting at non-letter characters.
     * @return Returns true if {@code word} contains a sub word when split at non-letters
     * (e.g. By splitting "there's" at any non-letters, the {@code keyword} "there" should match.
     */
    private boolean checkNonLetterSplit(String word, String keyword, String lettersAndSpacesRegex)
    {
        String[] subComponents = word.split(lettersAndSpacesRegex);
        if (subComponents.length > 1) {
            for (String subComponent : subComponents) {
                if (subComponent.equals(keyword)) {
                    return true;
                }
            }
        }
        return false;
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
