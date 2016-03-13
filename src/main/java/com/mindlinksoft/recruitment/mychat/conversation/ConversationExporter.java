package com.mindlinksoft.recruitment.mychat.conversation;

import com.mindlinksoft.recruitment.mychat.exception.ConverstaionReaderException;
import com.mindlinksoft.recruitment.mychat.exception.ConverstaionWriterException;
import com.mindlinksoft.recruitment.mychat.message.Message;
import com.mindlinksoft.recruitment.mychat.filter.Filter;
import com.mindlinksoft.recruitment.mychat.json.GsonWrapper;
import com.mindlinksoft.recruitment.mychat.message.MessageParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a conversation exporter that can read a conversation and write it
 * out in JSON.
 */
public class ConversationExporter {

    final private MessageParser messageParser;

    public ConversationExporter() {
        this.messageParser = new MessageParser();
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to
     * {@code outputFilePath}.
     *
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param filters The filters.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(final String inputFilePath, final String outputFilePath, final List<Filter> filters) throws Exception {
        System.out.println("Reading conversation from '" + inputFilePath + "' ...");
        Conversation conversation = this.readConversation(inputFilePath);

        System.out.println("Applying filters...");
        for (Filter filter : filters) {
            System.out.println("\t - " + filter.getClass().getSimpleName() + " applyed.");
            filter.apply(conversation);
        }

        // generate riport after filters
        System.out.println("Generating riport...");
        conversation.generateRiport();

        System.out.println("Writing conversation to '" + outputFilePath + "' ...");
        this.writeConversation(conversation, outputFilePath);

        System.out.println("Conversation export completed successfully with " + conversation.getMessages().size() + " messages!");
    }

    /**
     * Represents a helper to read a conversation from the given
     * {@code inputFilePath}.
     *
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation readConversation(final String inputFilePath) throws Exception {
        InputStream is = new FileInputStream(inputFilePath);
        // BufferedReader takes care of closing the reader it wraps.
        try (BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                messages.add(messageParser.parse(line));
            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The input file was not found at: \"" + inputFilePath + "\"");
        } catch (IOException e) {
            throw new ConverstaionReaderException(inputFilePath, e);
        }
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the
     * given {@code outputFilePath}.
     *
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be
     * written.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(final Conversation conversation, final String outputFilePath) throws Exception {
        OutputStream os = new FileOutputStream(outputFilePath, false);
        // BufferedWriter takes care of closing the writer it wraps.
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {
            String jsonConversation = GsonWrapper.getInstance().toJson(conversation);
            bw.write(jsonConversation);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The output file was not found at: \"" + outputFilePath + "\"");
        } catch (IOException e) {
            throw new ConverstaionWriterException(outputFilePath, e);
        }
    }
}
