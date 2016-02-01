package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {
	
	//methods
    /**
     * The application entry point.
     * @param args The command line arguments args: 
     * -input inputfilepath(required) 
     * -output outputfilepath(required) 
     * -senderId senderId to be filtered(optional) 
     * -keyword keyword to be filtered(optional) 
     * -blacklist comma-separated words to be redacted 
     * (example: -input chat.txt -output chat.json -senderId bob -keyword pie -blacklist like,society)
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.getInputFilePath(), configuration.getOutputFilePath(), configuration.getSenderId(), configuration.getKeyword(), configuration.getBlacklist());
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(String inputFilePath, String outputFilePath, String senderId, String keyword, List<String> blacklist) throws Exception {
    	
        Conversation conversation = this.readConversation(inputFilePath);
        
        //check if has to be filtered by senderID
        if(!(senderId==null)){

        	conversation.filterSenderId(senderId);
        }
        
        //check if has to be filtered by keyword
        if(!(keyword==null)){

        	conversation.filterKeyword(keyword);
        }
        
        //check if words have to be blacklisted
        if(blacklist.size()>0){

        	conversation.blacklist(blacklist);
        	
        }

        //writes to JSON file
        this.writeConversation(conversation, outputFilePath);

        //logging
        System.out.println("Conversation consisting of " + conversation.getMessages().size() + " messages sucessfully exported from '" + inputFilePath + "' to '" + outputFilePath+"'!");



    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws Exception {
        // Create output file with 
        // try-with-resources statement
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(outputFilePath) , "UTF-8")) {
        		
            //logging
            System.out.println("Writing to '" + outputFilePath + "'...");

            //initialize Gson instance with GsonBuilder and configure
            final Gson gsonObj = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping()
            		.registerTypeAdapter(Instant.class, new InstantSerializer()).create();
            
            //write to JSON
            writer.write(gsonObj.toJson(conversation));
            
            
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The following filepath was not found: " + outputFilePath);
        } catch (IOException e) {
        	throw new Exception(e);
        }
    }

    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation readConversation(String inputFilePath) throws Exception {
        // Reading file with 
        // try-with-resources statement
        try(InputStream is = new FileInputStream(inputFilePath);
        		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
        	
            List<Message> messages = new ArrayList<Message>();

            //logging
            System.out.println("Reading from '" + inputFilePath + "'...");
            
            String conversationName = br.readLine(); //read first line from inputFile and store as conversationName
            String line;

            //iterate through inputFile and extract information from each line to create messages
            while ((line = br.readLine()) != null) {
                String[] split = line.split(" ", 3);

                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
            }
            
         
            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The following file was not found: " + inputFilePath);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }
    
    
    /**
     * Helper method that deals with serialization of Instants for JSON
     * @throws Exception
     */
    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
