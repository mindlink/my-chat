package com.mindlinksoft.recruitment.mychat.loader;

import com.mindlinksoft.recruitment.mychat.conversation.Conversation;
import com.mindlinksoft.recruitment.mychat.conversation.ConversationInterface;
import com.mindlinksoft.recruitment.mychat.message.Message;
import com.mindlinksoft.recruitment.mychat.message.MessageInterface;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Represents the conversation loader
 */
public class ConversationLoader {

    /**
     * Method to load conversation from file
     * @param inputPath path to input file
     * @return a {@link Conversation} object that implements {@link ConversationInterface}
     * @throws IllegalArgumentException,IOException,ArrayIndexOutOfBoundsException,NumberFormatException
     */
    public ConversationInterface loadConversation(String inputPath) throws IllegalArgumentException, IOException, ArrayIndexOutOfBoundsException{
        try(InputStream is = new FileInputStream(inputPath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<MessageInterface> messages = new ArrayList<>();
            String conversationName = r.readLine();
            String line;
            String timeStamp;
            String userName;
            String messageText;

            while ((line = r.readLine()) != null) {

                String[] split = line.split(" ", 3);
                timeStamp = split[0];
                userName = split[1];
                messageText = split[2];

                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(timeStamp)), userName, messageText));
            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found.", e);
        } catch (IOException e) {
            throw new IOException("Something went wrong", e);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Malformed messages: Check for empty messages. Accepted format: \n<conversation_name><new_line>\n" +
                    "(<unix_timestamp><space><username><space><message><new_line>)*");
        } catch (NumberFormatException e){
            throw new NumberFormatException("Malformed messages: Check for missing timestamp.");
        }
    }
}
