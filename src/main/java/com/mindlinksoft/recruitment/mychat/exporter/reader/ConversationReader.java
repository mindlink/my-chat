package com.mindlinksoft.recruitment.mychat.exporter.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Message;

/**
 * Represents the reader which will transform text files into Conversation objects
 */
public class ConversationReader implements ConversationReaderService {

    private final String inputFilePath;
    private static final Logger LOGGER = Logger.getLogger(ConversationReader.class.getName());

    /**
     * Returns an instance of ConversationReader
     *
     * @param inputFilePath path to parse text document
     */
    public ConversationReader(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    /**
     * Reads the file in inputFilePath, and returns a Conversation
     * object with names and messages.
     *
     * @return Conversation built from parsing input file
     * @throws IllegalArgumentException if given file cannot be opened
     */
    public Conversation read() {
        try (BufferedReader bReader = Files.newBufferedReader(Paths.get(inputFilePath))) {
            String name = bReader.readLine(); // header line

            List<Message> messages = bReader.lines()
                    .map(this::parseLine)
                    .collect(Collectors.toList());

            return new Conversation(name, messages);
        } catch (IOException e) {
            // log exception, then exit program with error code
            LOGGER.severe(e.toString());
            System.exit(-1);
        }
        return null;
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

    public String getInputFilePath() {
        return inputFilePath;
    }
}