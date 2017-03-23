package com.mindlinksoft.recruitment.mychat.io;

import com.mindlinksoft.recruitment.mychat.convertors.ConversationToJson;
import com.mindlinksoft.recruitment.mychat.filter.Filter;
import com.mindlinksoft.recruitment.mychat.model.Conversation;
import com.mindlinksoft.recruitment.mychat.model.Message;
import com.mindlinksoft.recruitment.mychat.validation.Validator;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by dpana on 3/22/2017.
 */
public class IOUtils {

    private static final Logger logger = Logger
            .getAnonymousLogger();

    private static final boolean hideNumbers = false;
    private static final boolean obfuscate = false;

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */
    public static void writeConversation(Conversation conversation, String outputFilePath) {
        // The PrintStream will automatically close when done.
        try (PrintWriter out = new PrintWriter( outputFilePath )) {
            String json = ConversationToJson.convertConversationToJson(conversation);
            logger.info("Writing Json output:\n" + json);
            out.println(json);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Please provide a correct output file path in the command line arguments");
        }
    }

    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file.
     * @param keyword
     * @param user
     * @param blacklist @return The {@link Conversation} representing by the input file.
     * @throws IOException Thrown when something bad happens.
     */
    public static Conversation readConversation(String inputFilePath, String keyword, String user, String[] blacklist) throws IOException {
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ",3); // We limit the split array to 3 strings in order to get the whole message.
                // There was a bug and only the first word of the message was exported.

                Filter filter = new Filter(split[2], keyword, user, blacklist);
                filter.filterBlacklist();
                // Here we assume that we do not output messages that contain words in the blacklist
                // and defined as keywords. These words are already ******
                if (filter.filterByKeyword() || filter.filterByUser(split[1])) continue;
                String message = filter.getMessage();

                if (hideNumbers) {
                    String[] words = message.split("\\s+");
                    for (int i = 0 ; i < words.length ; i++) {
                        if (words[i].matches("\\d+") && Validator.checkValidCC(words[i]) || Validator.checkPhoneNo(words[i])) {
                            message = message.replace(words[i] , "**********");
                        }
                    } // Hide Credit Card and Phone numbers
                }

                if (obfuscate) {
                    String userOb = "";
                    for (char c : split[1].toCharArray())
                        userOb += c + 25; //Simple obfuscation method
                    split[1] = userOb;
                }

                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], message));
            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Please provide a correct input file path in the command line arguments");
        } catch (IOException e) {
            throw new IOException("Something went wrong while reading the input file...");
        }
    }

}
