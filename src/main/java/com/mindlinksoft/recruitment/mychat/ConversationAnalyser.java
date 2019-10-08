package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Helper class to analyse how many message each user sends.
 */
public class ConversationAnalyser {
	
	/**
	 * Counts the number of messages sent by each user and orders the users accordingly.
	 * @param conversation The conversation to be analysed
	 * @return Conversation with user activity recorded
	 */
	public Conversation analyseConversation(Conversation conversation) {
		ArrayList<Message> messages = conversation.getMessages();
		HashMap<String, Integer> activityHashMap = new HashMap<>();
		
		for(Message message : messages) {
			Integer count = activityHashMap.containsKey(message.getSenderId()) ? activityHashMap.get(message.getSenderId()) : 0;
			activityHashMap.put(message.getSenderId(), count + 1);
		}
		
		ArrayList<UserActivity> activityArrayList = new ArrayList<UserActivity>();
		
		for (Map.Entry<String, Integer> entry : activityHashMap.entrySet()) {
			activityArrayList.add(new UserActivity(entry.getKey(), entry.getValue()));
		}
		
		Collections.sort(activityArrayList, Collections.reverseOrder());
		
		UserActivity[] activityArray = activityArrayList.toArray(new UserActivity[activityArrayList.size()]);
		
		return(new Conversation(conversation.getName(), messages, activityArray));
		
	}
}
