package com.mindlinksoft.recruitment.mychat.io;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.Instant;
import com.google.gson.GsonBuilder;
import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.ConversationExporterConfiguration;

/**
 * Writer class used to write conversations to a JSON file
 * @author Mohamed Yusuf
 *
 */
public class Writer {
    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param config Chat configurations.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, ConversationExporterConfiguration config) throws Exception {
    	String outputFilePath = config.getOutputFilePath();
    	BufferedWriter bw = null;
        try {
        	bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFilePath, true)));            
            bw.write(this.toJSONFormat(conversation));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file [" + outputFilePath + "] was not found. \n" + e.getMessage());
        } catch (IOException e) {
            throw new IOException("Something went wrong: " + e.getMessage());
        } finally {
        	bw.close();
        }
    }
    
    /**
     * Helper method to convert a {@code conversation} into JSON format
     * @param conversation
     * @return
     */
    private String toJSONFormat(Conversation conversation) {
    	GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
       return gsonBuilder.setPrettyPrinting().create().toJson(conversation);
    }
}
