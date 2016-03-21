
package com.mindlinksoft.recruitment.mychat.Services;

import com.google.gson.*;
import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.Message;
import com.mindlinksoft.recruitment.mychat.Resources;
import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Orry Edwards
 * Handles the reading and writing to files
 */
public class IOFileServices {
    
    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when an unexpected error happens.
     */
    public Conversation readConversation(String inputFilePath) throws Exception
    {
        try
        {
            InputStream is = new FileInputStream(inputFilePath);
        
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            ArrayList<Message> messages = new ArrayList();

            String conversationName = br.readLine();
            String line;

            while ((line = br.readLine()) != null) {
               
                String[] split = line.split(" ", 3);

                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
            }
            
            br.close();
            is.close();
            
            return new Conversation(conversationName, messages);
        }
        catch (FileNotFoundException e){
            throw new IllegalArgumentException(Resources.FILEWASNOTFOUND + "\n" + e.getMessage());
        } 
        catch (IOException e){
            throw new Exception(e.getMessage());
        }
    }
    
    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param config The object holding configuration values 
     * @throws Exception Thrown when an unexpected error happens
     */
    public void writeConversation(Conversation conversation, ConversationExporterConfiguration config) throws Exception {
        try (OutputStream os = new FileOutputStream(config.getOutputFilePath(), true);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

            //Create a constructor to pass in the gson builder for testing purposes
            GsonBuilder gsonBuilder = new GsonBuilder().disableHtmlEscaping();
            gsonBuilder.registerTypeAdapter(Instant.class, new IOFileServices.InstantSerializer());
            
            Gson g = gsonBuilder.create();
            
            //Write the Conversation name first
            bw.write(conversation.getName() + "\n");
            
            //Write the actual content
            bw.write(g.toJson(conversation));
            
            bw.close();
            
        System.out.println("Conversation exported from '" + config.getInputFilePath() + "' to '" + config.getOutputFilePath());
            
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(config.getOutputFilePath() +  "file was not found.");
        } catch (IOException e) {
            throw new Exception(e.getMessage());
        }
    }
    
     /**
     * Checks to see if the input path specified actually exists
     * @param inputPath The entered input path specified by the user
     * @return a boolean representing if the file path is valid or not
     */
    public boolean isFilePathValid(String inputPath)
    {
        File file = new File(inputPath); //doesn't create file, creates a object the refers to the file
 
        // if file doesn't exists, then create it
	if (file.exists() == false) 
	{
            return false;
	}
        
        return true;
    }
    
    class InstantSerializer implements JsonSerializer<Instant> {
    @Override
    public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(instant.getEpochSecond());
    }
    }
    
}
