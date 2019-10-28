package com.mindlinksoft.recruitment.mychat.Filters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

/**
 * Replaces the {@code senderId} of each user by UserX, where X is replaced by a
 * number. Extends {@link Filter} abstract class.
 */
public class ObfuscateIDFilter extends Filter {

	/**
	 * Filters the input {@code convo} by replacing senderIds and returns a new
	 * {@link Conversation} object with new senderIds for each person who had sent a
	 * message.
	 * 
	 * @param convo {@link Conversation} object to be filtered.
	 * @return New {@link Conversation} object with filtered messages.
	 */
	@Override
	public Conversation filterMessages(Conversation convo) {
		List<Message> filteredMessages = new ArrayList<Message>();
		String conversationName = convo.name;
		HashMap<String, String> usersHM = new HashMap<String, String>();

		for (Message m : convo.messages) {
			String obfName = "";
			if (!usersHM.containsKey(m.senderId)) {
				obfName = "User" + usersHM.size();
				usersHM.put(m.senderId, obfName);
			} else {
				obfName = usersHM.get(m.senderId);
			}
			filteredMessages.add(new Message(m.timestamp, obfName, m.content));
		}
		return new Conversation(conversationName, filteredMessages);
	}
}
