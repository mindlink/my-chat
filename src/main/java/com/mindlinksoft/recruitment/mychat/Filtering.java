package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;
/**
 * class responsible for filtering the conversation
 * 
 * @author Asmaa
 *
 */
public final class Filtering {
	/**
	 * Method responsible for filtering conversation by a specified user
	 * 
	 * @param convo - the convo to be filtered
	 * @param user - only messages from this user will be displayed
	 */

	public Conversation byUser(Conversation convo, String user){
		// create a list to hold the filter messages 
		List<Message> msgs = new ArrayList<Message>();

		// loop for each message in the convo
		for (Message msg : convo.getMsg()) {
			// if the msgs sender ID is equal to the specified user
			if (msg.getSenderID().equals(user)){
				// add the msg to the list of filtered messages
				msgs.add(msg);
			}
		}

		return new Conversation(convo.getName(), msgs);
	}

	/**
	 * Method responsible for filtering conversation by a specified keyword
	 * 
	 * @param convo - the convo to be filtered
	 * @param user - only messages with the specified keyword will be displayed
	 */

	public Conversation byKeyword(Conversation convo, String keyword){
		List<Message> msgs = new ArrayList<Message>();

		for (Message msg : convo.getMsg()){
			if (msg.getContent().toLowerCase().contains(keyword.toLowerCase())){
				msgs.add(msg);
			}
		}

		return new Conversation(convo.getName(), msgs);
	}
}
