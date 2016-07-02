package com.mindlinksoft.recruitment.mychat;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.logging.Level;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Writer class for formatted output conversations.*/
class ConversationWriter implements Closeable {

	//associated writer
	private final BufferedWriter bufferedWriter;
	
	/**
	 * @param out output stream to write onto*/
	public ConversationWriter(Writer out) {
		if(null == out)
			throw new NullPointerException("Conversation writer constructor was"
					+ "passed a null pointer to an input stream");
		this.bufferedWriter = new BufferedWriter(out);
	}

	/**
	 * Writes the parameter conversation into json format in the output stream
	 * set at construction time of the ConversationWriter instance
	 * @param conversation the {@link Conversation} object to write as json
	 * output*/
	void writeConversation(Conversation conversation) throws IOException {
		Gson gson = GsonInstanceFactory.getGson();
		bufferedWriter.write(gson.toJson(conversation));

	}

	@Override
	public void close() throws IOException {
		bufferedWriter.close();
		
	}
}
