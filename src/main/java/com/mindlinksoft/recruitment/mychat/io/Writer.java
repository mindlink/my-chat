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
import com.mindlinksoft.recruitment.mychat.exceptions.WriterException;

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
     * @throws WriterException 
     */
    public void writeConversation(Conversation conversation, ConversationExporterConfiguration config) throws WriterException{
    	String outputFilePath = config.getOutputFilePath();
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFilePath, true)))){       	            
            bw.write(this.toJSONFormat(conversation));
        } catch (FileNotFoundException e) { 
        	throw new IllegalArgumentException("Could not find file " + config.getOutputFilePath(), e);
        } catch (IOException e) {
        	throw new WriterException("IO error when attempting to write file " + config.getOutputFilePath(), e);
        } 
    }
    
    /**
     * Helper method to convert a {@code conversation} into JSON format
     * @param conversation
     * @return JSON string of conversation
     */
    private String toJSONFormat(Conversation conversation) {
    	GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
       return gsonBuilder.setPrettyPrinting().create().toJson(conversation);
    }
}
