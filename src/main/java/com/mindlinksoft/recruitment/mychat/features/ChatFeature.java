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
	
	/**
	 * Argument required for feature
	 * @param argument String argument required for feature
	 */
	void setArgument(String argument);
	
	/**
	 * Function to determine whether the feature requires an argument
	 * @return Boolean value to show whether an argument is required for the feature
	 */
	boolean argumentRequired();
}
