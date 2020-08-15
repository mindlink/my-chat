package com.mindlinksoft.recruitment.mychat.utils;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;

import com.mindlinksoft.recruitment.mychat.features.ChatFeature;
import com.mindlinksoft.recruitment.mychat.model.Conversation;
import com.mindlinksoft.recruitment.mychat.model.ConversationExporterConfiguration;
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
	
	public static final boolean debug = true;
	
    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @throws IOException 
     * @throws FileNotFoundException 
     * @throws Exception Thrown when something bad happens.
     */
    public static void exportConversation(ConversationExporterConfiguration config) 
    		throws FileNotFoundException, IOException
    {
    	if(debug) System.out.println("Reading conversation file...");
        Conversation conversation = readConversation(config.inputFilePath);
        if(debug) System.out.println("Reading conversation file...DONE");
        
        if(debug) System.out.println("Applying features...");
        Conversation updated_convo = applyFeatures(config, conversation);
        if(debug) System.out.println("Applying features...DONE");
        
        if(debug) System.out.println("Writing conversation...");
        writeConversation(updated_convo, config.outputFilePath);
        if(debug) 
        {
        	System.out.println("Writing conversation...DONE");
        	System.out.println("Conversation exported from '" + config.inputFilePath + "' to '" + config.outputFilePath);
        }
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws FileNotFoundException Thrown when output file can't be opened.
     * @throws IOException Thrown when output file can't be written to. 
     */
    public static void writeConversation(Conversation conversation, String outputFilePath) 
    		throws FileNotFoundException, IOException 
    {
    	
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) 
        {
        	if(debug) System.out.println("Converting conversation to JSON...");
            bw.write(JSONConverter.convertConversationToJSON(conversation));
        } 
        catch (FileNotFoundException e) 
        {
            throw new FileNotFoundException("writeConversation: Could not find output file at - '" + outputFilePath 
            		+ "'\n" + e.getMessage());
        } 
        catch (IOException e)
        {
            throw new IOException("writeConversation: Error while writing to - '" + outputFilePath 
            		+ "'\n" + e.getMessage());
        }
    }

    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws FileNotFoundException Thrown when input file can't be opened.
     * @throws IOException Thrown when input file can't be read from. 
     */
    public static Conversation readConversation(String inputFilePath) 
    		throws FileNotFoundException, IOException
    {
        try(BufferedReader r = new BufferedReader(new FileReader(inputFilePath))) 
        {
            ArrayList<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;

            if(debug) System.out.println("Reading messages...");
            while ((line = r.readLine()) != null) 
            {
                String[] split = line.split(" ", 3);
                
                Message msg = new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]);
                
                messages.add(msg);
            }
            if(debug) System.out.println("Reading messages...DONE");
            
            Conversation convo = new Conversation(conversationName, messages);
            
            return convo;
        } 
        catch (FileNotFoundException e) 
        {
            throw new FileNotFoundException("readConversation: Could not find file at - '" + inputFilePath 
            		+ "'\n" + e.getMessage());
        } 
        catch (IOException e) 
        {
            throw new IOException("readConversation: Error while reading - '" + inputFilePath 
            		+ "'\n" + e.getMessage());
        }
    }
    
    /**
     * Function to apply features to a conversation given a configuration file
     * @param config ConversationExporterConfiguration configuration file of features to be applied
     * @param convo Conversation to apply features to
     * @return New Conversation after features are applied
     */
    public static Conversation applyFeatures(ConversationExporterConfiguration config, Conversation convo)
    {
    	ArrayList<Message> changed_messages = new ArrayList<Message>();
    	
    	if(debug) System.out.println("Applying message features...");
    	for(Message msg : convo.messages)
    	{
        	Message changed_msg = msg;
    		for(ChatFeature feature : config.features)
            {
    			changed_msg = feature.applyMessageFeature(changed_msg);
            }
    		changed_messages.add(changed_msg);
    	}
    	if(debug) System.out.println("Applying message features...DONE");
    	
    	Conversation changed_convo = new Conversation(convo.name, changed_messages);
    	
    	if(debug) System.out.println("Applying conversation features...");
        for(ChatFeature feature : config.features)
        {
        	changed_convo = feature.applyConversationFeature(changed_convo);
        }
        if(debug) System.out.println("Applying conversation features...DONE");

    	return changed_convo;
    }
}
