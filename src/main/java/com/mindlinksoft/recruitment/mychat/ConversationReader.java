package com.mindlinksoft.recruitment.mychat;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Reader class for raw format conversations.*/
class ConversationReader implements Closeable {

	//associated reader
	private final BufferedReader bufferedReader;
	
	/**
	 * @param in an input stream*/
	public ConversationReader(Reader in) {
		if(null == in)
			throw new NullPointerException("Conversation reader constructor was"
					+ "passed a null pointer to an input stream");
		this.bufferedReader = new BufferedReader(in);
		
	}
	
	/**
	 * @return a Conversation that has been read from the input stream the 
	 * instance of ConversationReader class was provided as a parameter to its 
	 * constructor.
	 * @throws IOException when an error occurs reading from the stream*/
	Conversation readConversation() throws IOException {
		
		List<Message> messages = new ArrayList<Message>();

		String conversationName = bufferedReader.readLine();
		String line;

		while ((line = bufferedReader.readLine()) != null) {
			String[] split = line.split(" ", 3);
			messages.add(new Message(
					Long.parseUnsignedLong(split[0]),
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
