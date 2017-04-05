package com.mindlinksoft.recruitment.mychat.conversation;

/**
 * Interface for a Conversation formatter which performs formatting operations
 * on an {@link IConversation}. 
 */
public interface IConversationFormatter {

	/**
	 * Applies formatting on an {@link IConversation}.
	 * @param conversation
	 * @return Formatted conversation.
	 */
	IConversation format(IConversation conversation);
	
}
