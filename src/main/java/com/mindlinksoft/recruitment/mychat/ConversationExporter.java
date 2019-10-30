package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import com.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {
    public final String inputFilePath = "chat.txt";
    public final String outputFilePath = "chat.json";
    public String conversation_name;

    /**
     * The application entry point.
     *
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception {


        ConversationExporter exporter = new ConversationExporter();

         exporter.exportConversation("chat.txt", "chat.json", args);
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
        String whetherToHideCardAndPhoneNumbers = args[2];
        String whetherToObfuscateUserIds = args[3];
        CommandLineArgumentParser p = new CommandLineArgumentParser();
        p.parseCommandLineArguments(filterMethod, wordsToFilterBy, whetherToHideCardAndPhoneNumbers, whetherToObfuscateUserIds);

//        Conversation conversation = this.readConversation();
//
//        this.writeConversation(conversation);

        // TODO: Add more logging...
        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     *
     * @param conversation The conversation to write.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, String whetherToHideCardAndPhoneNumbers) throws Exception {
        // TODO: Do we need both to be resources, or will buffered writer close the stream?
        try (OutputStream os = new FileOutputStream(outputFilePath, true);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {


            Report report = new Report();
            List<String> activeUserList = report.makeReport(conversation);

            if (whetherToHideCardAndPhoneNumbers.equals("yes")) {
                List<Message> messageList = new ArrayList<>();
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

                Gson g = gsonBuilder.create();


                conversation.messages.forEach(s -> {
//                    s.message = s.message.replaceAll(".*\\d.*", "redacted");
                    String[] split = s.message.split( "\\b");
                    StringBuilder censoredWords = new StringBuilder();
                    for (String word : split) {
                        if (word.matches(".*\\d.*")) {
                            censoredWords = censoredWords.append(removeCredentials(word));

                        } else {
                            censoredWords = censoredWords.append(word);
                        }

                    }
                    s.message = censoredWords.toString();
                    Message m = new Message((s.unix_timestamp), s.username, s.message);
                    messageList.add(m);


                });
                Conversation filteredConvo = new Conversation(conversation.conversation_name, messageList, activeUserList);


                bw.write(g.toJson(filteredConvo));
                bw.close();
                os.close();

            }
            // TODO: Maybe reuse this? Make it more testable...

            else {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

                Gson g = gsonBuilder.create();
                Conversation convoWithReport = new Conversation(conversation.conversation_name, conversation.messages, activeUserList);
                bw.write(g.toJson(convoWithReport));
                bw.close();
                os.close();

            }
        } catch (FileNotFoundException e) {
            // TODO: Maybe include more information?
            throw new IllegalArgumentException("The output file was not found so the writing of the conversation could not be completed. Please ensure the output file is correct.");
        } catch (IOException e) {
            // TODO: Should probably throw different exception to be more meaningful :/
            throw new Exception("Input Output error: the file was not created.");
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
            //System.out.println("Full match: " + matcher.group(0));
            for (int i = 1; i <= matcher.groupCount(); i++) {

                //System.out.println("Group " + i + ": " + matcher.group(i));
                return "*redacted*";

            }
        }
        return word;
    }

    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     *
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation readConversation(String whetherToObfuscateUserIds) throws Exception {
        try (InputStream is = new FileInputStream(inputFilePath);
             BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<Message>();

            conversation_name = r.readLine();
            String line;


            while ((line = r.readLine()) != null) {
                String[] split = new String[3];

                final String regex = "^(\\d+) ([A-Za-z]+) (.+)$";
                //System.out.println(line);

                final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
                final Matcher matcher = pattern.matcher(line);

                while (matcher.find()) {
                    //System.out.println("Full match: " + matcher.group(0));
                    int j = 0;
                    for (int i = 1; i <= matcher.groupCount(); i++) {
                        //System.out.println("Group " + i + ": " + matcher.group(i));
                        split[j] = matcher.group(i);
                        j++;

                    }
                }
                if (whetherToObfuscateUserIds.equals("yes")) {
                    split[1] = UUID.nameUUIDFromBytes(split[1].getBytes()).toString();
                    Message m = new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]);
                    messages.add(m);
                } else {
                    Message m = new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]);
                    messages.add(m);
                    //System.out.println(messages.size());
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

    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
