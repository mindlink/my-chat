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

    /**
     * The application entry point.
     *
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception {

        System.out.println("Welcome to my-chat: the app which allows you to view saved conversations within" +
                "the Mindlink application. You may filter the conversations in the following ways. To filter by...\n" +
                "" +
                "- user: reply with the keyword 'username'\n" +
                "- a specific word: reply with 'specific word'\n" +
                "- hiding a specific word: reply with 'hide word'.\n" +
                "You may also view the whole conversation with no filter applied by replying with: 'no filter'. ");
        Scanner s1 = new Scanner(System.in);
        String filterMethod = s1.nextLine();
        CommandLineArgumentParser p = new CommandLineArgumentParser();
        p.parseCommandLineArguments(filterMethod);


        //CommandLineArgumentParser().parseCommandLineArguments(s);

        // exporter.exportConversation("chat.txt", "chat.json");
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     *
     * @param inputFilePath  The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(String inputFilePath, String outputFilePath) throws Exception {
        Conversation conversation = this.readConversation();

        this.writeConversation(conversation);

        // TODO: Add more logging...
        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     *
     * @param conversation   The conversation to write.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation) throws Exception {
        // TODO: Do we need both to be resources, or will buffered writer close the stream?
        try (OutputStream os = new FileOutputStream(outputFilePath, true);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {
            System.out.println("Would you also like to hide credit card and phone numbers?");
            Scanner s1 = new Scanner(System.in);
            String hideNumbersYesNo = s1.nextLine();

//            Report report = new Report();
//            List<String> activeUserList = report.makeReport(conversation);

            if (hideNumbersYesNo.equals("yes")) {
                List<Message> messageList = new ArrayList<>();
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

                Gson g = gsonBuilder.create();



                conversation.messages.forEach(s -> {
                    String[] split = s.message.split(" ");
                    StringBuilder censoredWords = new StringBuilder();
                    for (String word : split) {
                        if (word.matches(".*\\d.*")) {
                            censoredWords = censoredWords.append(" " + removeCredentials(word));

                        } else {
                            censoredWords = censoredWords.append(" " + word);
                        }

                    }
                    s.message = censoredWords.toString();
                    Message m = new Message((s.unix_timestamp), s.username, s.message);
                    messageList.add(m);


                });
                //Conversation filteredConvo = new Conversation(conversation.conversation_name, messageList, activeUserList);


                bw.write(g.toJson(conversation));

            }
            // TODO: Maybe reuse this? Make it more testable...

            else {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

                Gson g = gsonBuilder.create();
                //Conversation convoWithReport = new Conversation(conversation.conversation_name, conversation.messages, activeUserList );
                bw.write(g.toJson(conversation));

            }
        } catch (FileNotFoundException e) {
            // TODO: Maybe include more information?
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            // TODO: Should probably throw different exception to be more meaningful :/
            throw new Exception("Something went wrong");
        }
    }

    /**
     * Regex method which takes out the card and phone numbers from the messages.
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
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation readConversation() throws Exception {
        try (InputStream is = new FileInputStream(inputFilePath);
             BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;
            System.out.println("Would you like to obfuscate user IDs?");
            Scanner s1 = new Scanner(System.in);
            String obfuscate = s1.nextLine();



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
                if (obfuscate.equals("yes")){
                    split[1] =  UUID.nameUUIDFromBytes(split[1].getBytes()).toString();
                    Message m = new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]);
                    messages.add(m);
                }else {
                    Message m = new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]);
                    messages.add(m);
                    //System.out.println(messages.size());
                }

            }
            Report userActivityReport = new Report();
            List<String> userActivityList = userActivityReport.makeReport(new Conversation(conversationName, messages));

            return new Conversation(conversationName, messages, userActivityList);
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
