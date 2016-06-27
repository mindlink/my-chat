package com.mindlinksoft.recruitment.mychat;

import java.io.BufferedWriter;
import java.io.Writer;

class ConversationWriter extends BufferedWriter {

	public ConversationWriter(Writer out) {
		super(out);
		
	}

	public ConversationWriter(Writer out, int sz) {
		super(out);
		
	}
	
	void writeConversation(Conversation conversation) {
		//TODO set up gson instance!
		
	}
}
