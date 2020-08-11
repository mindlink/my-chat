package com.mindlinksoft.recruitment.mychat;

import java.io.*;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;


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
        try {
	        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
	        exporter.exportConversation(configuration);
        }catch(ArrayIndexOutOfBoundsException e) {
        		System.out.println("Arguments not present.");
        }
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(ConversationExporterConfiguration configuration) throws Exception {
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
    public void writeConversation(Conversation conversation, String outputFilePath) throws Exception {
        
        try (OutputStream os = new FileOutputStream(outputFilePath, true);
        		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))){

        		GsonInit gson = new GsonInit();
            bw.write(gson.g.toJson(conversation));
        } catch (FileNotFoundException e) {
            System.out.println("The directory doesn't exist");
        } catch (IOException e) {
            
            throw new Exception("Cannot find the file to read");
        }
    }

    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation readConversation(ConversationExporterConfiguration configuration) throws Exception {
        try(InputStream is = new FileInputStream(configuration.inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

        		//Set over list so messages don't include duplicates
        		Set<Message> messages = new HashSet<Message>();
        		
            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
            		//Splits line by space limiting the number of split strings to 3
                String[] split = line.split("\\s", 3);
                //If no commands where specified export chat without applying any filter
                if(configuration.isFunctionalityEmpty()) {
                		messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
                }
                else {
                		//For each command specified
                		for(Functionality functionality : configuration.functionality) {
                			// Apply the filter
                			if(functionality.applyFunctionality(split[functionality.getRequiredChatField()])){
                				// Checks if the filter was applied on user field
                				if(functionality.getMessage().equals("")) {
                					messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
                				}
                				// Checks if the filter was applied on message field
                				else {
                					messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], functionality.getMessage()));
                				}
                				
                			}
                		}
        		
                }
      
            }
            
            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            throw new Exception("Something went wrong");
        }
    }
   
}
