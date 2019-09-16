package com.mindlinksoft.recruitment.mychat;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.time.Instant;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ConversationWriter {

	/**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath));

        ConversationAndJsonConverter converter = new ConversationAndJsonConverter();
        String json = converter.conversationToJson(conversation);

        bw.write(json);	//Assuming you want the exporter to overwrite old files.
        bw.close();
    }	
}
