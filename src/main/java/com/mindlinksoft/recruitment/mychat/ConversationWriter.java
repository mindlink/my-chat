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

class ConversationWriter implements Closeable {

	private final BufferedWriter bufferedWriter;
	
	public ConversationWriter(Writer out) {
		this.bufferedWriter = new BufferedWriter(out);
	}

	void writeConversation(Conversation conversation) throws IOException {
		Gson gson = GsonInstanceFactory.getGson();
		bufferedWriter.write(gson.toJson(conversation));

	}

	@Override
	public void close() throws IOException {
		bufferedWriter.close();
		
	}
}
