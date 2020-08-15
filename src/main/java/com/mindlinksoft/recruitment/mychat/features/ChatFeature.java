package com.mindlinksoft.recruitment.mychat.features;

import com.mindlinksoft.recruitment.mychat.model.Conversation;
import com.mindlinksoft.recruitment.mychat.model.Message;

/**
 * Interface to represent various features to be implemented
 *
 */
public interface ChatFeature 
{
	/**
	 * Use this function to apply feature at Message level
	 * @param msg Message to be altered
	 */
	void applyMessageFeature(Message msg);
	
	/**
	 * Use this function to apply feature at Conversation level
	 * @param convo Conversation to be altered
	 */
	void applyConversationFeature(Conversation convo);
}
