package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath} with options.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param preferences The {@link Preferences} object that determines what edits are to be made to the conversation
     * @throws FileNotFoundException Thrown when a given file is not found.
     * @throws IOException Thrown when a file failed to open
     */
    public void exportConversation(String inputFilePath, String outputFilePath, Preferences preferences) throws Exception {
        Conversation conversation = this.readConversation(inputFilePath);
        
        
        conversation = this.editConversation(conversation, preferences);
        
                
        this.writeConversation(conversation, outputFilePath);


        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
    }
    
    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath} with no options.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @throws FileNotFoundException Thrown when a given file is not found.
     * @throws IOException Thrown when a file failed to open
     */
    public void exportConversation(String inputFilePath, String outputFilePath) throws Exception {
    	Conversation conversation = this.readConversation(inputFilePath);
    	
    	this.writeConversation(conversation, outputFilePath);   

        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath + " with no filters");
    }
    
    
    
    /**
     * Edits the given conversation using the given preferences
     * @param conversation The conversation to be Edited
     * @param preferences The {@link Preferences} object that determines what edits are to be made to the conversation
     * @return
     */
    public Conversation editConversation(Conversation conversation, Preferences preferences) {
    	//Creates a new conversation with a report
        //Generate report first so it isn't affected by any filters or blacklists
        //Can easily be moved later to generate the report after filtering if needed
        if(preferences.report) {
        	conversation = new Conversation(conversation.name,conversation.messages,this.createReport(conversation));
        	System.out.println("Report Generated");
        }
        
        
        //Filter by user
                
        if(preferences.userFilter != null) {
        	System.out.println("Filtered by User: " + preferences.userFilter);
        	ConversationFilter filter = new UserFilter();
        	conversation = filter.filter(conversation, preferences.userFilter);
        }
        
        //Filter by keyword before writing to JSON
        
        if(preferences.keywordFilter != null) {
        	System.out.println("Filtered by Keyword: " + preferences.keywordFilter);
        	ConversationFilter filter = new KeywordFilter();
        	conversation = filter.filter(conversation, preferences.keywordFilter);
        }
        
        
        //Hide BlackListed Words
        if(preferences.blacklist != null) {
        	ConversationRedacter redacter = new ConversationRedacter();
        	System.out.print("BlackListed words: ");
        	preferences.blacklist.forEach((word) -> {
        		System.out.print(word + "/");
        	});
        	System.out.println();
        	conversation = redacter.blacklistConversation(conversation, preferences.blacklist);
        }
        
    	return conversation;
    }
    
    
    

    
    
    

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws FileNotFoundException Thrown when the given file is not found.
     * @throws IOException Thrown when the file failed to open
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws Exception {
        // TODO: Do we need both to be resources, or will buffered writer close the stream?
        try (OutputStream os = new FileOutputStream(outputFilePath, false); //No longer appends to the JSON file, Now writes to start of file 
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

            // TODO: Maybe reuse this? Make it more testable...
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

            Gson g = gsonBuilder.create();

            bw.write(g.toJson(conversation));
            

            
            
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file at " + outputFilePath + " was not found.",e);
        } catch (IOException e) {
            throw new IOException("File at" + outputFilePath + " Failed to open",e);
        }
    }

    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws FileNotFoundException Thrown when the given file is not found.
     * @throws IOException Thrown when the file failed to open
     */
    public Conversation readConversation(String inputFilePath) throws Exception {
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ", 3);
                //Previously splitting the message after the first word, fixed by setting a max of three strings to be returned by split 


                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file at " + inputFilePath + " was not found.",e);
        } catch (IOException e) {
            throw new IOException("File at" + inputFilePath + " Failed to open",e);
        }
    }

    

    
    

    
    
    /**
     * Generates a report about the given {@code conversation}
     * @param conversation The conversation to be reported
     * @return The collection of {@link Report} objects that make up the report
     */
    public Collection<Report> createReport(Conversation conversation){
    	
    	
    	System.out.println("******************** CREATING REPORT ********************");

    	
    	ArrayList<Report> activity = new ArrayList<Report>();
    	
    	
    	Iterator<Message> messageIterator = conversation.messages.iterator();
    
    	

    	while(messageIterator.hasNext()) {
    		Message message = messageIterator.next();
    		
    		System.out.print("User: " + message.senderId);
    		
    		Iterator<Report> reportIterator =  activity.iterator();
    		
        	Boolean found = false;
    		
    		while(reportIterator.hasNext()) {
    			Report report = reportIterator.next();
    			
    			if(message.senderId.contentEquals(report.sender)) {
    				report.count++;
    				found = true;
    				break;
    			}
    		}
    		
    		System.out.println(" found: " + found);
    		
    		if(!found) {
    			Report newSender = new Report(message.senderId,1);
    			activity.add(newSender);
    		}
    	}
    	
    	System.out.println();

    	
    	//Sorting the Report in Descending order
    	
    	Collections.sort(activity);
    	Collections.reverse(activity);

    	return activity;
    }
    
    
    
    
    
    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
