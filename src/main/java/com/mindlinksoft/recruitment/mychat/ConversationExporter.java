package com.mindlinksoft.recruitment.mychat;

import java.io.*;
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
    // The messages in the conversation.
    private final List<Message> messages;
    // The redacted string, used in place of a blacklisted word.
    private final String redact;
    // The separator used when splitting a message content into words.
    private final String sepRegex;
    // The separator/delimiter used when joining words to make a message content.
    private final String sepJoin;
    // Regex for specifying any non-letter and non-whitespace characters.
    private final String lettersAndSpaces;
    // The index in a message split where the content starts.
    private final int contentStartingIndex;

    /**
     * Initializes a new instance of the {@link ConversationExporter} class.
     */
    public ConversationExporter()
    {
        messages = new ArrayList<>();
        redact = "*redacted*";
        sepRegex = "\\s+";
        sepJoin = " ";
        lettersAndSpaces = "[^a-zA-Z ]";
        contentStartingIndex = 2;
    }

    /**
     * The application entry point.
     *
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception
    {
        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration config = new CommandLineArgumentParser().parseCommandLineArguments(args);
        exporter.exportConversation(config.getInputFilePath(), config.getOutputFilePath(), config.getUser(), config.getKeyword(), config.getWordsToHide());
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     *
     * @param inputFilePath  The input file path.
     * @param outputFilePath The output file path.
     * @param user           Filter messages from this user.
     * @param keyword        Filter messages containing this word.
     * @param wordsToHide    List of blacklisted words which need redacting.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(String inputFilePath, String outputFilePath, String user, String keyword, String[] wordsToHide) throws Exception
    {
        Conversation conversation = this.readConversation(inputFilePath, user, keyword, wordsToHide);
        this.writeConversation(conversation, outputFilePath);
        String baseMsg = "Conversation exported from '" + inputFilePath + "' to '" + outputFilePath + "' ";
        writeToLog(baseMsg, user, keyword, wordsToHide);
    }

    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     *
     * @param inputFilePath The path to the input file.
     * @param user          Filter messages from this user.
     * @param keyword       Filter messages containing this word.
     * @param wordsToHide   List of blacklisted words which need redacting.
     * @return The {@link Conversation} represented by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation readConversation(String inputFilePath, String user, String keyword, String[] wordsToHide) throws Exception
    {
        try (BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath)))) {
            String conversationName = r.readLine();
            String line;
            if (user != null && keyword != null) {
                while ((line = r.readLine()) != null) {
                    filterUserKeyword(line, user, keyword);
                }
            } else if (user != null) {
                while ((line = r.readLine()) != null) {
                    filterUser(line, user);
                }
            } else if (keyword != null) {
                while ((line = r.readLine()) != null) {
                    filterKeyword(line, keyword);
                }
            } else {
                while ((line = r.readLine()) != null) {
                    filterNone(line);
                }
            }
            if (wordsToHide != null) {
                for (Message m : messages) {
                    m.setContent(redactWords(m.getContent(), wordsToHide));
                }
            }
            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Input file path argument: '" + inputFilePath + "', could not be found. More details:" + e.getCause());
        } catch (IOException e) {
            throw new IOException("Input file path argument: '" + inputFilePath + "', could not be read from. More details: " + e.getCause());
        }
    }

    /**
     * Filter by both {@code user} and {@code keyword}.
     *
     * @param line    The {@link Message} read in from the input file.
     * @param user    Filter messages from this user.
     * @param keyword Filter messages containing this word.
     */
    private void filterUserKeyword(String line, String user, String keyword)
    {
        String[] split = line.split(sepRegex);
        String content = String.join(sepJoin, getContentSplit(split));
        if (user.equals(split[1].toLowerCase()) && containsKeyword(content, keyword)) {
            messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], content));
        }
    }

    /**
     * Filter messages by {@code user}.
     *
     * @param line The {@link Message} read in from the input file.
     * @param user Filter messages from this user.
     */
    private void filterUser(String line, String user)
    {
        String[] split = line.split(sepRegex);
        String content = String.join(sepJoin, getContentSplit(split));
        if (user.equals(split[1].toLowerCase())) {
            messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], content));
        }
    }

    /**
     * Filter messages just by {@code keyword}.
     *
     * @param line    The {@link Message} read in from the input file.
     * @param keyword Filter messages containing this word.
     */
    private void filterKeyword(String line, String keyword)
    {
        String[] split = line.split(sepRegex);
        String content = String.join(sepJoin, getContentSplit(split));
        if (containsKeyword(content, keyword)) {
            messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], content));
        }
    }

    /**
     * No filter for user or keyword.
     *
     * @param line The {@link Message} read in from the input file.
     */
    private void filterNone(String line)
    {
        String[] split = line.split(sepRegex);
        String content = String.join(sepJoin, getContentSplit(split));
        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], content));
    }

    /**
     * Redact any words which appear in {@code wordsToHide} (blacklisted words)
     * in the {@code content} of a {@link Message}.
     *
     * @param content     The {@code content} of the {@link Message}.
     * @param wordsToHide The list of words to redact.
     * @return A string representing the new redacted message content.
     */
    private String redactWords(String content, String[] wordsToHide)
    {
        String[] wordsOrig = content.split(sepRegex);
        String[] words = content.replaceAll(lettersAndSpaces, "").toLowerCase().split(sepRegex);
        for (int i = 0; i < words.length; i++) {
            for (String w : wordsToHide) {
                if (words[i].equals(w)) {
                    wordsOrig[i] = wordsOrig[i].replaceAll(String.format("(?i)%s", Pattern.quote(words[i])), redact);
                    break;
                } else if (checkNonLetterSplit(wordsOrig[i], w)) {
                    String[] subComponents = wordsOrig[i].split(lettersAndSpaces);
                    for (String subComponent : subComponents) {
                        if (subComponent.equals(w)) {
                            wordsOrig[i] = wordsOrig[i].replaceAll(String.format("(?i)%s", Pattern.quote(subComponent)), redact);
                        }
                    }
                }
            }
        }
        return String.join(sepJoin, wordsOrig);
    }

    /**
     * Get the entire {@code content} of the {@link Message}.
     * Using all entries in {@code split}, except for the first {@code startingIndex} entries, separated by {@code sep}.
     *
     * @param split The read in line from the {@code inputFilePath}.
     * @return A string representing the {@code content} of the {@link Message}.
     */
    private String[] getContentSplit(String[] split)
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
     * @param content The {@code content} of a {@link Message}.
     * @param keyword The {@code keyword} to look for.
     * @return Returns true if {@code content} contains at least one {@code keyword}.
     */
    private boolean containsKeyword(String content, String keyword)
    {
        String[] wordsOrig = content.split(sepRegex);
        String[] words = content.replaceAll(lettersAndSpaces, "").toLowerCase().split(sepRegex);
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(keyword) || checkNonLetterSplit(wordsOrig[i], keyword)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if a {@code word} contains any sub words when split by non-letter characters.
     *
     * @param word    The {@code word} to split by non-letters.
     * @param keyword The {@code keyword} to match against.
     * @return Returns true if {@code word} contains a sub word when split at non-letters
     * (e.g. By splitting "there's" at any non-letters, the {@code keyword} "there" should match.
     */
    private boolean checkNonLetterSplit(String word, String keyword)
    {
        String[] subComponents = word.split(lettersAndSpaces);
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
            CreateGsonBuild createGsonBuild = new CreateGsonBuild();
            bw.write(createGsonBuild.convert(conversation));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Output file path argument: '" + outputFilePath + "', could not be found. More details:" + e.getCause());
        } catch (IOException e) {
            throw new IOException("Output file path argument: '" + outputFilePath + "', could not be written to. More details: " + e.getCause());
        }
    }

    /**
     * Write to standard output, concerning the latest exportation to JSON.
     *
     * @param msg The base message for logging.
     * @param u   Filter messages from this user.
     * @param k   Filter messages containing this word.
     * @param w   List of blacklisted words which need redacting.
     */
    private void writeToLog(String msg, String u, String k, String[] w)
    {
        if (u != null && k != null && w != null) {
            System.out.println(msg + "-" + printUser(u) + printKeyword(k) + printWordsToHide(w));
        } else if (u != null && k != null) {
            System.out.println(msg + "-" + printUser(u) + printKeyword(k));
        } else if (u != null && w != null) {
            System.out.println(msg + "-" + printUser(u) + printWordsToHide(w));
        } else if (k != null && w != null) {
            System.out.println(msg + "-" + printKeyword(k) + printWordsToHide(w));
        } else if (u != null) {
            System.out.println(msg + "-" + printUser(u));
        } else if (k != null) {
            System.out.println(msg + "-" + printKeyword(k));
        } else if (w != null) {
            System.out.println(msg + "-" + printWordsToHide(w));
        } else {
            System.out.println(msg);
        }
    }

    private String printUser(String u)
    {
        return " (user: " + u + ")";
    }

    private String printKeyword(String k)
    {
        return " (keyword: " + k + ")";
    }

    private String printWordsToHide(String[] w)
    {
        return " (wordsToHide: " + Arrays.toString(w) + ")";
    }
}
