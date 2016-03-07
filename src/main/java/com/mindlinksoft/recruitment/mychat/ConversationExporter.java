package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import com.google.gson.stream.MalformedJsonException;
import com.mindlinksoft.recruitment.mychat.filters.ConversationFilter;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {

    /**
     * The application entry point.
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception {
    	
        ConversationExporter exporter = new ConversationExporter();
        
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        
        exporter.exportConversation(configuration);
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(ConversationExporterConfiguration config) throws Exception {
    	System.out.println("Exporting conversation...");
    	
    	Conversation conversation = this.readConversation(config.inputFilePath);
    	System.out.println("Reading conversation...");
    	
    	//We store the messages, excluding the conversation title.
    	Collection<Message> filteredMessages = conversation.getMessages(); 
    	
    	//To make sure the report doesn't give out sensitive data, we write it first so it can be filtered through later.
    	if (config.flagReport){
    		ReportGenerator reportGen = new ReportGenerator();
    		filteredMessages = reportGen.generateReport(filteredMessages);
    		System.out.println("Report added to conversation.");
    		conversation.setMessages(filteredMessages);
    	}
    	
    	//We filter the messages.
    	if (config.filters != null) {
    		for (ConversationFilter filter : config.filters){
    			filteredMessages = filter.useFilter(filteredMessages);
    		}
    		Conversation filteredConversation = new Conversation("Filtered: " + conversation.getName(), filteredMessages);
    		this.writeConversation(filteredConversation, config.outputFilePath);
    		System.out.println("Conversation exported and filtered from '" + config.inputFilePath + "' to '" + config.outputFilePath);		
    	}
    	
    	//If there were no filters specified, it just writes the file to JSON.
    	else {
    		this.writeConversation(conversation, config.outputFilePath);
    		System.out.println("Conversation exported from '" + config.inputFilePath + "' to '" + config.outputFilePath);
    	}

    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws Exception {
        
        try (OutputStream os = new FileOutputStream(outputFilePath, true);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

           
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

            Gson g = gsonBuilder.create();

            bw.write(g.toJson(conversation));
            
            bw.close();
            
        } catch (MalformedJsonException e) {
            throw new MalformedJsonException("JSON Malformed:  " + e.getMessage());              
        } catch (FileNotFoundException e) {  
            throw new IllegalArgumentException("The file was not found.");  
        } catch (IOException e) {
            throw new Exception("IO Exception: " + e.getMessage());   
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation readConversation(String inputFilePath) throws Exception {
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {

            	//We now do the line split correctly, adding the number of strings we want:
                String[] split = line.split(" ", 3);
                
                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            throw new Exception("Something went wrong");
        }
    }

    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
