package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import java.io.*;

import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {
    public static final String inputFilePath = "chat.txt";
    public static final String outputFilePath = "chat.json";
    public String conversation_name;

    /**
     * The application entry point.
     *
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        exporter.exportConversation(inputFilePath, outputFilePath, args);
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * With parameters as set up in the main argument string array.
     *
     * @param inputFilePath  The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(String inputFilePath, String outputFilePath, String[] args) throws Exception {
        String filterMethod = args[0];
        String stringToFilterBy = args[1];
        String[] wordsToFilterBy = stringToFilterBy.split(",");
        Boolean hideCardAndPhoneNumbers = convertStringToBoolean(args[2]);
        Boolean obfuscateUserIds = convertStringToBoolean(args[3]);
        CommandLineArgumentParser p = new CommandLineArgumentParser();
        p.parseCommandLineArguments(filterMethod, wordsToFilterBy, hideCardAndPhoneNumbers, obfuscateUserIds);
        // TODO: Add more logging...
        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
    }

    /**
     * Method to convert the String user entry, either 'yes' oor 'no', into a boolean.
     * @param yesOrNo User entry.
     * @return User entry to boolean
     */
    public Boolean convertStringToBoolean(String yesOrNo) {
        Boolean trueOrFalse = false;
        if (yesOrNo.equals("yes")) {
            trueOrFalse = true;
        }
        return trueOrFalse;
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     *
     * @param conversation The conversation to write.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, Boolean filterConversation) throws Exception {
        try (OutputStream outputStream = new FileOutputStream(outputFilePath, true);
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream))) {
            Report report = new Report();
            List<String> activeUserList = report.makeReport(conversation);
            Conversation processedConversation;
            if (filterConversation) {
                processedConversation = filterConversation(conversation);
            } else {
                processedConversation = new Conversation(conversation.conversation_name, conversation.messages, activeUserList);
            }
            writeToJson(outputStream, bufferedWriter, processedConversation);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The output file was not found so the writing of the conversation could not be completed. Please ensure the output file is correct.");
        } catch (IOException e) {
            throw new Exception("Input Output error: the file was not created.");
        }
    }

    /**
     * Method which filters the conversation in search of numbers in the messages. When any number is found,
     * it is sent to the removeCredentials method which examines whether the number is a card or phone number.
     *
     * @param conversation The conversation being filtered.
     * @return The conversation filtered as appropriate.
     */
    private Conversation filterConversation(Conversation conversation) {
        Report report = new Report();
        List<String> activeUserList = report.makeReport(conversation);
        List<Message> messageList = new ArrayList<>();

        conversation.messages.forEach(s -> {
            String[] split = s.message.split("\\b");
            StringBuilder censoredWords = new StringBuilder();
            for (String word : split) {
                if (word.matches("[0-9]+")) {
                    censoredWords = censoredWords.append(removeCredentials(word));

                } else {
                    censoredWords = censoredWords.append(word);
                }

            }
            s.message = censoredWords.toString();
            Message m = new Message((s.unix_timestamp), s.username, s.message);
            messageList.add(m);
        });
        return new Conversation(conversation.conversation_name, messageList, activeUserList);
    }

    /**
     * Method takes in an outstream, a bufferedwriter and the conversatiion and writes it to JSON format.
     *
     * @param outputStream           The outputstream
     * @param bufferedWriter         The bufferedwriiter
     * @param conversationWithReport The conversation requiring outputting.
     * @throws IOException Output exception when writing the file to JSON format.
     */
    private void writeToJson(OutputStream outputStream, BufferedWriter bufferedWriter, Conversation conversationWithReport) throws IOException {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
        Gson g = gsonBuilder.create();
        try {
            bufferedWriter.write(g.toJson(conversationWithReport));
            bufferedWriter.close();
            outputStream.close();
        } catch (IOException e) {
            System.out.println("There was an issue in outputting the conversation into its written JSON format.");
        }
    }

    /**
     * Regex method which takes out the card and phone numbers from the messages.
     *
     * @param word
     * @return
     */
    public String removeCredentials(String word) {
        final String regex = "(^(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12}$)|(^4[0-9]{12}(?:[0-9]{3})?$)|^3[47][0-9]{13}$|^(((\\+44\\s?\\d{4}|\\(?0\\d{4}\\)?)\\s?\\d{3}\\s?\\d{3})|((\\+44\\s?\\d{3}|\\(?0\\d{3}\\)?)\\s?\\d{3}\\s?\\d{4})|((\\+44\\s?\\d{2}|\\(?0\\d{2}\\)?)\\s?\\d{4}\\s?\\d{4}))(\\s?\\#(\\d{4}|\\d{3}))?$|\\b(?:\\d[ -]*?){13,16}\\b|^(?:[0-9])*[0-9]{9}.$";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(word);
        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                return "*redacted*";
            }
        }
        return word;
    }

    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     *
     * @param obfuscateUserIds
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation readConversation(Boolean obfuscateUserIds) throws Exception {
        try (InputStream is = new FileInputStream(inputFilePath);
             BufferedReader r = new BufferedReader(new InputStreamReader(is))) {
            List<Message> messages = new ArrayList<Message>();
            conversation_name = r.readLine();
            String line;
            while ((line = r.readLine()) != null) {
                String[] split = new String[3];
                final String regex = "^(\\d+) ([A-Za-z]+) (.+)$";
                final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
                final Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    int j = 0;
                    for (int i = 1; i <= matcher.groupCount(); i++) {
                        split[j] = matcher.group(i);
                        j++;
                    }
                }
                if (obfuscateUserIds) {
                    String hiddenId = obfuscateUserIds(split[1]);
                    Message m = new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), hiddenId, split[2]);
                    messages.add(m);
                } else {
                    Message m = new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]);
                    messages.add(m);
                }
            }
            Report userActivityReport = new Report();
            List<String> userActivityList = userActivityReport.makeReport(new Conversation(conversation_name, messages));

            return new Conversation(conversation_name, messages, userActivityList);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            throw new Exception("Something went wrong");
        }
    }

    /**
     * Method takes in a username and returns it as a hidden Id based on its bytes and using
     * the UUID class.
     *
     * @param username The username to hide
     * @return the hidden username
     */
    public String obfuscateUserIds(String username) {
        String hiddenId = UUID.nameUUIDFromBytes(username.getBytes()).toString();
        return hiddenId;
    }


}
