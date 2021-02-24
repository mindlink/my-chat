package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import picocli.CommandLine;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.ParseResult;
import picocli.CommandLine.UnmatchedArgumentException;

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
     * The application entry point.
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception {
        // We use picocli to parse the command line - see https://picocli.info/
        ConversationExporterConfiguration configuration = new ConversationExporterConfiguration();
        CommandLine cmd = new CommandLine(configuration);

        try {
            ParseResult parseResult = cmd.parseArgs(args);
        
            if (parseResult.isUsageHelpRequested()) {
                cmd.usage(cmd.getOut());
                System.exit(cmd.getCommandSpec().exitCodeOnUsageHelp());
                return;
            }
            
            if (parseResult.isVersionHelpRequested()) {
                cmd.printVersionHelp(cmd.getOut());
                System.exit(cmd.getCommandSpec().exitCodeOnVersionHelp());
                return;
            }

            ConversationExporter exporter = new ConversationExporter();

            exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, configuration.userFilter, configuration.keywordFilter, configuration.blacklist, configuration.report);
            
            //TODO make new exportconversation for no args and one for all args
          

            
            System.exit(cmd.getCommandSpec().exitCodeOnSuccess());
        } catch (ParameterException ex) {
            cmd.getErr().println(ex.getMessage());
            if (!UnmatchedArgumentException.printSuggestions(ex, cmd.getErr())) {
                ex.getCommandLine().usage(cmd.getErr());
            }

            System.exit(cmd.getCommandSpec().exitCodeOnInvalidInput());
        } catch (Exception ex) {
            ex.printStackTrace(cmd.getErr());
            System.exit(cmd.getCommandSpec().exitCodeOnExecutionException());
        }
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param userFilter The user to filter by.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(String inputFilePath, String outputFilePath, String userFilter, String keywordFilter, List<String> blacklist, Boolean report) throws Exception {
        Conversation conversation = this.readConversation(inputFilePath);
        ConversationFilterer filter = new ConversationFilterer();
        ConversationRedacter redacter = new ConversationRedacter();
        
        //Generate report first so it isn't affected by any filters or blacklists
        if(report) {
        	conversation.addReport(this.createReport(conversation));
        	System.out.println("Report Generated");
        }
        
        
        //Filter by user
        
        
        if(userFilter != null) {
        	System.out.println("Filtered by User: " + userFilter);
        	conversation = filter.filterConversationByUser(conversation, userFilter);
        }
        
        //Filter by keyword before writing to JSON
        
        if(keywordFilter != null) {
        	System.out.println("Filtered by Keyword: " + keywordFilter);
        	conversation = filter.filterConversationByKeyword(conversation, keywordFilter);
        }
        
        
        //Hide BlackListed Words
        if(blacklist != null) {
        	System.out.print("BlackListed words: ");
        	blacklist.forEach((word) -> {
        		System.out.print(word + "/");
        	});
        	System.out.println();
        	conversation = redacter.blacklistConversation(conversation, blacklist);
        }
        
        
        this.writeConversation(conversation, outputFilePath);


        
        
        
        // TODO: Add more logging...
        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
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
