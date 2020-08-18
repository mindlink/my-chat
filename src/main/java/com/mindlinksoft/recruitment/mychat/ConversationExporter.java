package com.mindlinksoft.recruitment.mychat;

import java.io.*;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
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
    public static void main(String[] args){
        ConversationExporter exporter = new ConversationExporter();
        try {
	        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
	        exporter.exportConversation(configuration);
        }catch(Exception e) {
        		if(e.getCause() instanceof ArrayIndexOutOfBoundsException) {
        			e.printStackTrace();
        		}
        		
        }
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(ConversationExporterConfiguration configuration){
    		Conversation conversation = this.readConversation(configuration);
        this.writeConversation(conversation, configuration.outputFilePath);

        System.out.println("Conversation exported from '" + configuration.inputFilePath + "' to '" + configuration.outputFilePath + " at: " +ZonedDateTime.now());
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, String outputFilePath){
        
        try (OutputStream os = new FileOutputStream(outputFilePath, true);
        		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))){

        		GsonInit gson = new GsonInit();
            bw.write(gson.g.toJson(conversation));
        } catch (Exception e) {
        		if(e.getCause() instanceof IOException){
        			e.printStackTrace();
        		}
        		else if(e.getCause() instanceof FileNotFoundException) {
        			 e.printStackTrace();
        		}
        }
    }

    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation readConversation(ConversationExporterConfiguration configuration){
        try(InputStream is = new FileInputStream(configuration.inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

        		//Set over list so messages don't include duplicates
        		Set<Message> messages = new HashSet<Message>();
        		List<ParsedLine> parsedLineList = new ArrayList<ParsedLine>();
            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
            		//Splits line by space limiting the number of split strings to 3
                String[] split = line.split("\\s", 3);
                //Lines read from the document become a ParsedLine object and added to a list of ParsedLine objects
                parsedLineList.add(new ParsedLine(split[0],split[1],split[2]));
            }
            
            for(ParsedLine parsedLine : parsedLineList) {
            		//If no commands where specified export chat without applying any filter
            		if(configuration.isFunctionalityEmpty()) {
            			messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(parsedLine.getTimestamp())), parsedLine.getUsername(), parsedLine.getMessage()));
            		}
                else {
                		//For each command specified
                		for(Functionality functionality : configuration.functionality) {
                			// Apply the filter
                			if(functionality.applyFunctionality(parsedLine)){
                				messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(parsedLine.getTimestamp())), parsedLine.getUsername(), parsedLine.getMessage()));
                			}
                		}
        		
                }

            }
        return new Conversation(conversationName, messages);
        } catch (Exception e) {
	        	if(e.getCause() instanceof IOException){
	    			e.printStackTrace();
	    		}
	    		else if(e.getCause() instanceof FileNotFoundException) {
	    			 e.printStackTrace();
	    		}
        }
        return null;
    }
   
}
