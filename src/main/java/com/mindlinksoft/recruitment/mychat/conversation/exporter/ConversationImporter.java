package com.mindlinksoft.recruitment.mychat.conversation.exporter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

import com.mindlinksoft.recruitment.mychat.conversation.Conversation;
import com.mindlinksoft.recruitment.mychat.conversation.IConversation;
import com.mindlinksoft.recruitment.mychat.message.IMessage;
import com.mindlinksoft.recruitment.mychat.message.Message;

/**
 * Class for an importing {@link IConversation} from a file
 *
 */
public class ConversationImporter {

	/**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file.
     * @return The {@link IConversation} representing by the input file.
     * @throws IllegalArgumentException If the input path is invalid.
     * @throws IOException If it fails to write to the input file.
     * 
     */
	public IConversation importConversation(String inputFilePath) throws IllegalArgumentException, IOException {
		System.out.println("Importing conversation from " + inputFilePath);
		try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            Collection<IMessage> messages = new ArrayList<IMessage>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ", 3);

                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Input file was not found.", e);
        } catch (IOException e) {
            throw new IOException("Error while reading input file.", e);
        }
	}
}
