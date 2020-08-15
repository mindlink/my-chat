package com.mindlinksoft.recruitment.mychat.utils;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;

import com.mindlinksoft.recruitment.mychat.features.ChatFeature;
import com.mindlinksoft.recruitment.mychat.model.Conversation;
import com.mindlinksoft.recruitment.mychat.model.Message;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 * 
 * Flags:
 * -u	Filter messages by specific user
 * -k	Filter messages by specific keyword
 * -b	Provide comma delimited list of words to be redacted in messages
 * -o	Obfuscate username in messages
 */
public class ConversationExporter {
	
	public final boolean debug = true;

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(String inputFilePath, String outputFilePath, ArrayList<ChatFeature> features) throws Exception 
    {
    	if(debug) System.out.println("Reading conversation...");
        Conversation conversation = this.readConversation(inputFilePath, features);
        if(debug) System.out.println("Reading conversation...DONE");
        
        if(debug) System.out.println("Writing conversation...");
        this.writeConversation(conversation, outputFilePath);
        if(debug) 
        {
        	System.out.println("Reading conversation...DONE");
        	System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
        }
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws FileNotFoundException Thrown when output file can't be opened.
     * @throws IOException Thrown when output file can't be written to. 
     */
    public void writeConversation(Conversation conversation, String outputFilePath) 
    		throws FileNotFoundException, IOException 
    {
    	
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) 
        {
        	if(debug) System.out.println("Converting conversation to JSON...");
            bw.write(JSONConverter.convertConversationToJSON(conversation));
        } 
        catch (FileNotFoundException e) 
        {
            throw new FileNotFoundException("writeConversation: Could not find output file at - '" + outputFilePath + "'");
        } 
        catch (IOException e)
        {
            throw new IOException("writeConversation: Error while writing to - '" + outputFilePath + "'");
        }
    }

    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws FileNotFoundException Thrown when input file can't be opened.
     * @throws IOException Thrown when input file can't be read from. 
     */
    public Conversation readConversation(String inputFilePath, ArrayList<ChatFeature> features) 
    		throws FileNotFoundException, IOException
    {
        try(BufferedReader r = new BufferedReader(new FileReader(inputFilePath))) 
        {
            ArrayList<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;

            if(debug) System.out.println("Reading messages and applying message features...");
            while ((line = r.readLine()) != null) 
            {
                String[] split = line.split(" ", 3);
                
                Message msg = new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]);
                
                for(ChatFeature feature : features)
                {
                	feature.applyMessageFeature(msg);
                }
                
                messages.add(msg);
            }
            
            Conversation convo = new Conversation(conversationName, messages);
            
            if(debug) System.out.println("Applying conversation features...");
            for(ChatFeature feature : features)
            {
            	feature.applyConversationFeature(convo);
            }

            return convo;
        } 
        catch (FileNotFoundException e) 
        {
            throw new FileNotFoundException("readConversation: Could not find file at - '" + inputFilePath + "'\n" + e.toString());
        } 
        catch (IOException e) 
        {
            throw new IOException("readConversation: Error while reading - '" + inputFilePath + "'\n" + e.toString());
        }
    }
}
