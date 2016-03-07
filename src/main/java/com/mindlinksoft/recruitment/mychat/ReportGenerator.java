package com.mindlinksoft.recruitment.mychat;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 
 * A report is added to the conversation that details the most active users
 * The most active user in a conversation is the user who sent the most messages.
 * Most active users are added to the JSON output as an array ordered by activity.
 * The number of messages sent by each user is included.
 *
 */
public class ReportGenerator {
	
	public Collection<Message> generateReport(Collection<Message> conversation) {
		LinkedHashMap<String, Integer> activity = new LinkedHashMap<String, Integer>();
		//Make sure conversation is not null.
		if(conversation != null){
			for (Message message : conversation){
				String id = message.getSenderId();
				if (activity.containsKey(id)) {
					activity.replace(id, activity.get(id)+1);
				} else {
					activity.put(id, 1);
				}
			}	
		}
		
		ArrayList<Entry<String, Integer>> entries = new ArrayList<Entry<String, Integer>>(activity.entrySet());
		
		Collections.sort(entries, new Comparator<Entry<String, Integer>>() {

			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		
		});


		for (Entry<String, Integer> entry : entries) {

			Message reportLine = new Message(Instant.EPOCH, "Sender Id: " + entry.getKey(), "Messages sent :" + entry.getValue());
			conversation.add(reportLine);
		}
		return conversation;
	}
}
