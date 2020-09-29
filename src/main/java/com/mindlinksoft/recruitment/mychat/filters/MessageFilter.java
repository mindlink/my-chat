package com.mindlinksoft.recruitment.mychat.filters;

import java.util.List;

import com.mindlinksoft.recruitment.mychat.model.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.model.Message;

public interface MessageFilter {
	
	public List<Message> filterMessages(List<Message> messages, ConversationExporterConfiguration config);

}
