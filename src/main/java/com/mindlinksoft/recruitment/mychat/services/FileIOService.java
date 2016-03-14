package com.mindlinksoft.recruitment.mychat.services;

import com.google.gson.*;
import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Service to handle any file input or output operations.
 */
public final class FileIOService {
	
    /**
     * Read a conversation from the given {@code inputFilePath}.
     * 
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing the input file.
     * @throws IllegalArgumentException Thrown when it cannot find the specified file.
     * @throws IOException Thrown when it cannot read from the specified file.
     */
    public Conversation readConversation(String inputFilePath) throws IllegalArgumentException, IOException {
    	
    	BufferedReader reader = null;
    	
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath)));      
            List<Message> messages = new ArrayList<Message>();
            
            String conversationName = reader.readLine();
            String line; 
            
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(" ", 3);         
                if (split.length < 3) { continue; }
                
                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
            }
            
            reader.close();
            return new Conversation(conversationName, messages);
            
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Could not find the file " + inputFilePath, e);
        } catch (IOException e) {
            throw new IOException("There was a problem reading the file " + inputFilePath, e);
        } finally {
        	if (reader != null) reader.close();
        }
    }

    /**
     * Write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * 
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws IllegalArgumentException Thrown when it cannot find the specified file.
     * @throws IOException Thrown when it cannot write to the specified file.
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws IllegalArgumentException, IOException {
        
    	GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Instant.class, new _InstantSerializer());
        Gson gson = gsonBuilder.create();
        
    	BufferedWriter writer = null;
    	
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFilePath, false)));     
            writer.write(gson.toJson(conversation));
            writer.close();
            
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Could not find the file " + outputFilePath, e);
        } catch (IOException e) {
            throw new IOException("There was a problem writing to the file " + outputFilePath, e);
        } finally {
        	if (writer != null) writer.close();
        }
    }
    
    /**
     * Override serialize for writing the JSON
     */
    private class _InstantSerializer implements JsonSerializer<Instant> {
    	
    	// TODO: Investigate why the Override annotation is throwing an error...
    	public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
