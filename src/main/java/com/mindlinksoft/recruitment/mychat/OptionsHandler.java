package com.mindlinksoft.recruitment.mychat;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.nio.charset.StandardCharsets; 
import java.nio.file.*; 

import org.apache.log4j.Logger;
/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class OptionsHandler {
	private static Logger logger = Logger.getLogger(OptionsHandler.class);

	/**
	* Returns remaining messages after applying optional filters
	* @param configuration Contains all command line arguments.
	* @param messages List of all messages in the chat.
	* @return List of Message objects which will add to conversation.
	*/
	public List<Message> options(ConversationExporterConfiguration configuration, List<Message> messages){
		List<Message> messages_to_convert = new ArrayList<Message>();
		for(Message m : messages){
			m.redact(configuration.blacklist);
			m.filterByWord(configuration.filter_word);
			m.filterByUser(configuration.filter_user);
			
			if(m.convert){
				messages_to_convert.add(m);
			}
			
		}
	return messages_to_convert;
	}
	
	/**
	* Creates a UserRep
	ort for each user and inserts the number of messages sent by that user
	* @param messages List of all messages in the chat
	* @return List of UserReport which will add to conversation
	*/
	public List<UserReport> generateReports(List<Message> messages){
		Map<String, Integer> messagecount = count_messages(messages);
		List<UserReport> reports = new ArrayList<UserReport>();
		for(Map.Entry e : messagecount.entrySet()){
			reports.add(new UserReport(e.getKey().toString(), (int)e.getValue()));
		}
		Collections.sort(reports);
		return reports;
	}
	
	/**
	* Creates a Map with the message sent count for each user
	* @param MList of all messages in the chat
	* @return Map with user name as key and number of messages sent as value
	*/
	public Map<String, Integer> count_messages(List<Message> messages){
		Map<String, Integer> messagecount = new HashMap<String, Integer>();
		for(Message m : messages){
			if(messagecount.containsKey(m.senderId)){
				messagecount.put(m.senderId, messagecount.get(m.senderId) + 1);
			}else{
				messagecount.put(m.senderId, 1);
			}
		}
		return messagecount;
	}



}
