package com.mindlinksoft.recruitment.mychat.reports;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.mindlinksoft.recruitment.mychat.conversation.Conversation;
import com.mindlinksoft.recruitment.mychat.messages.Message;

public class UserActivityReport implements IReport {

	@Override
	/**
	 * Generates User Activity report
	 */
	public Conversation generateReport(Conversation conversation) {
		Map<String, Integer> activityReport =  new HashMap<String, Integer>();	
		for(Message message : conversation.getMessages())
		{

			String senderID = message.getSenderId();
			if(activityReport.containsKey(senderID))
			{
				Integer messagesCount = activityReport.get(senderID);
				activityReport.put(senderID, messagesCount+1);
			}
			else
			{
				activityReport.put(senderID, new Integer(1));
			}
		}
        Map<String, Integer> result = new LinkedHashMap<>();
		activityReport.entrySet().stream()
						        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
						        .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));
		conversation.setUsersActivityReport(result);
		return conversation;
	}

}
