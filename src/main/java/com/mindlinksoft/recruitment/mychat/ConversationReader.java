package com.mindlinksoft.recruitment.mychat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConversationReader {

	/**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file. optionalArgumentManager The optional filters to apply to the messages
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    public static Conversation readConversation(String inputFilePath, OptionalArgumentManager optionalArgumentManager) throws Exception {
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<Message>();
            String conversationName = r.readLine();
            String line;
            Map<String, Integer> userMessageFrequencyMap = new HashMap<String, Integer>();
        	
            while ((line = r.readLine()) != null) {			//read in messages line by line
            	Message message = lineToMessage(line);
            	
            	if (optionalArgumentManager.messagePassesFilters(message)) {		//check if message should be included
            		message = optionalArgumentManager.applyRedactions(message);		//if so reply necessary redactions
            		messages.add(message); 
            		userMessageFrequencyMap = updateMessagesPerUserMap(message.senderId, userMessageFrequencyMap);
            	}
            }
            
            //At end, add a message detailing users message counts
            if (!userMessageFrequencyMap.isEmpty()) {
            	String activeUserLogAsString = MapToStringConverter.convertMapToString((userMessageFrequencyMap));
                Message activeUsersMessage = new Message(activeUserLogAsString);
                messages.add(activeUsersMessage);
            }
            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
        	String eMsg = String.format("The file '%s' was not found.", inputFilePath);
            throw new IllegalArgumentException(eMsg);
        } catch (IOException e) {
            throw new Exception("Something went wrong");
        }
    }

    public static Message lineToMessage(String line) {
    	String[] split = line.split(" ", 3);

        Instant timestamp = Instant.ofEpochSecond(Long.parseUnsignedLong(split[0]));
        String username = split[1];
        String message = split[2];
        
        return new Message(timestamp, username, message);
    }
    
    //update messages per user map
    private static Map<String, Integer> updateMessagesPerUserMap(String username, Map<String, Integer> map) {
    	if (map.containsKey(username)) {	
    		map.put(username, map.get(username) + 1);
		} else {
			map.put(username, 1);
		}   
    	return map;
    }
}