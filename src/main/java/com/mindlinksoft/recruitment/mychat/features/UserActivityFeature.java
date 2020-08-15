package com.mindlinksoft.recruitment.mychat.features;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.mindlinksoft.recruitment.mychat.model.Conversation;
import com.mindlinksoft.recruitment.mychat.model.Message;

/**
 * Chat feature to keep track of user activity in conversation
 *
 */
public class UserActivityFeature implements ChatFeature {

	/**
	 * HashMap to keep track of number of messages each user sends
	 */
	public Map<String, Integer> userActivity = new HashMap<String, Integer>();
	
	/**
	 * Keep track of number of messages per user
	 */
	@Override
	public Message applyMessageFeature(Message msg) 
	{
		if(!userActivity.containsKey(msg.senderId))
		{
			userActivity.put(msg.senderId, 1);
		}
		else
		{
			userActivity.put(msg.senderId, userActivity.get(msg.senderId) + 1);
		}
		return msg;
	}

	/**
	 * Once all messages have been read in, sort the activity map based on number of messages 
	 * and update ConversationMetadata
	 */
	@Override
	public Conversation applyConversationFeature(Conversation convo) 
	{
		//Convert Map to Arraylist
		ArrayList<Entry<String, Integer>> listToSort = new ArrayList<>(userActivity.entrySet());
		
		//Sort using List.sort()
		listToSort.sort(new Comparator<Entry<String, Integer>>()
				{
					@Override
					public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) 
					{
						return o2.getValue().compareTo(o1.getValue());
					}
				}
			);
		
		//Put Sorted list back into a new Map
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for(Entry<String, Integer> entry : listToSort)
		{
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		
		//Assign in Conversation
		return new Conversation(convo.name, convo.messages, sortedMap);
	}
}
