package com.mindlinksoft.recruitment.mychat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

class ConversationReader extends BufferedReader {

	public ConversationReader(Reader in) {
		super(in);
		
	}

	public ConversationReader(Reader in, int sz) {
		super(in);
		
	}
	
	Conversation readConversation() throws IOException {
		
		List<Message> messages = new ArrayList<Message>();

		String conversationName = this.readLine();
		String line;

		while ((line = this.readLine()) != null) {
			String[] split = line.split(" ", 3);
			String messageContent = line.substring(line.indexOf(' ', 11) + 1);
			messages.add(new Message(
					Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])),
					split[1], 
					messageContent));
		}

		return new Conversation(conversationName, messages);
	}

}
