package com.mindlinksoft.recruitment.mychat.exporter.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Message;

/**
 * Represents the reader which will transform text files into Conversation objects
 */
public class ConversationReader implements ConversationReaderService {

    private final String inputFilePath;
    private final Conversation conversation;
    private static final Logger LOGGER = Logger.getLogger(ConversationReader.class.getName());

    public ConversationReader(String inputFilePath) {
        this.inputFilePath = inputFilePath;
        this.conversation = new Conversation();
    }

    /**
     * Reads the file in inputFilePath, and returns a Conversation
     * object, complete with titles, map of active users and a list
     * of messages.
     *
     * @return Conversation built from parsing input file
     */
    public Conversation read() {
        try (BufferedReader bReader = Files.newBufferedReader(Paths.get(inputFilePath))) {
            conversation.setName(bReader.readLine()); // header line

            List<Message> messages = bReader.lines()
                    .map(this::parseLine)
                    .collect(Collectors.toList());

            conversation.setMessages(messages);
            return conversation;
        } catch (NoSuchFileException e) {
            LOGGER.log(Level.WARNING, "Input file could not be found at provided path.");
            throw new IllegalArgumentException("Input file could not be found at provided path.");
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Input file could not be opened at provided path.");
            throw new IllegalArgumentException("Input file could not be opened at provided path.");
        }
    }

    /**
     * Returns a new Message object, called by ConversationReader
     *
     * @param line line of text from the input file
     * @return Message object with relevant sender, content and timestamp
     */
    public Message parseLine(String line) {
        String[] data = line.split(" ", 3);

        Instant timestamp = Instant.ofEpochSecond(Long.parseUnsignedLong(data[0]));
        String senderText = data[1];
        String content = data[2];

        return new Message(timestamp, senderText, content);
    }

    public void countSender(Message message, Map<String, Long> frequencyMap) {
        String senderText = message.getSenderText();
        long currentValue = frequencyMap.getOrDefault(senderText, 0L); // get current value if it exists, else get 0
        frequencyMap.put(senderText, ++currentValue); // increment previous value (or 0) and update (or put) in map
    }

    public String getInputFilePath() {
        return inputFilePath;
    }

    public Conversation getConversation() {
        return conversation;
    }

}