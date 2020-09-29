package com.mindlinksoft.recruitment.mychat.model;

public interface ConversationReader {
	public Conversation readConversation(ConversationExporterConfiguration configuration) throws Exception;
}
