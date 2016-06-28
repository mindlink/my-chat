package com.mindlinksoft.recruitment.mychat;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

class ConversationReader implements Closeable {

	private final BufferedReader bufferedReader;
	
	public ConversationReader(Reader in) {
		if(null == in)
			throw new NullPointerException("Conversation reader constructor was"
					+ "passed a null pointer to an input stream");
		this.bufferedReader = new BufferedReader(in);
		
	}
	
	Conversation readConversation() throws IOException {
		
		List<Message> messages = new ArrayList<Message>();

		String conversationName = bufferedReader.readLine();
		String line;

		while ((line = bufferedReader.readLine()) != null) {
			String[] split = line.split(" ", 3);
			messages.add(new Message(
					Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])),//TODO refactor Message to get rid of this
					split[1], 
					split[2]));
		}

		return new Conversation(conversationName, messages);
	}

	@Override
	public void close() throws IOException {
		bufferedReader.close();
		
	}

}
