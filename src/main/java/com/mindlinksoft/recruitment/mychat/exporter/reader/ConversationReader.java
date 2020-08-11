package com.mindlinksoft.recruitment.mychat.exporter.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Message;


public class ConversationReader implements ConversationReaderService {
    
    private final String inputFilePath;
    private final Conversation conversation;

    public ConversationReader(String inputFilePath) {
        this.inputFilePath = inputFilePath;
        this.conversation = new Conversation();
    }

    /**
     * Reads the file in inputFilePath, and returns a Conversation
     * object, complete with titles, map of active users and a list
     * of messages.
     * @return Conversation built from parsing input file
     */
    public Conversation read() {
        try (BufferedReader bReader = Files.newBufferedReader(Paths.get(inputFilePath))) {
            conversation.setName(bReader.readLine()); // header line
            Map<String, Long> frequencyMap = new HashMap<>();

            List<Message> messages = bReader.lines()
                .map(Message::parseLine)
                .peek((message) -> countSender(message, frequencyMap))
                .collect(Collectors.toList());

            conversation.setMessages(messages);
            conversation.setFrequencyMap(frequencyMap);
            return conversation;
        } catch (NoSuchFileException e) {
            throw new IllegalArgumentException("Input file could not be found at provided path.");
        } catch (IOException e) {
            throw new IllegalArgumentException("Input file could not be opened at provided path.");
        }
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