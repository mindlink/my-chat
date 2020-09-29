package com.mindlinksoft.recruitment.mychat.model;

public interface ConversationWriter {
	public void writeConversation(Conversation conversation, String outputFilePath) throws Exception;
}
