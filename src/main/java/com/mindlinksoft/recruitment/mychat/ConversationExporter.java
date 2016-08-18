package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.mindlinksoft.recruitment.mychat.ConversationExporterTests.InstantDeserializer;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
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
    public static void main(String[] args) throws Exception {
    	ConversationExporter exporter = new ConversationExporter();
        //throw exception if input and output files are not provided
        try{
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        deleteFile(configuration.outputFilePath);
        exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, 
        		configuration.filteredUser, configuration.filtererWord, configuration.blackList);
        }
        catch (ArgumentsNumberException e){
        	System.out.println("Providing input and output file names is mandotory."
        			+ "\nPlease also note: maximum of 100 arguments is allowed");
        }
    }

    /**
     * Deletes any existing file with the same name as the chosen one. 
     * @param inputFile The input file path.
     */
    public static void deleteFile(String outputFile)
    {
    	Path path = Paths.get(outputFile); 
    	try {
    	    Files.delete(path);
    	} catch (NoSuchFileException x) {
    	    System.err.format("No such file or directory: " + path + "\n");
    	} catch (DirectoryNotEmptyException x) {
    	    System.err.format("No such file or directory: " + path + "\n");
    	} catch (IOException x) {
    	    // File permission problems are caught here.
    	    System.err.println(x);
    	}
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(String inputFilePath, String outputFilePath, String filteredUser,
    		String filteredWord, ArrayList<String> blackList) throws Exception {
        Conversation conversation = this.readConversation(inputFilePath);
        //System.out.println(conversation.toString());
        conversation = UserFilterer (conversation, filteredUser);
        //System.out.println(conversation.toString());
        conversation = WordFilterer (conversation, filteredWord);
        //System.out.println(conversation.toString());
        conversation = blackListChecker (conversation, blackList);
        System.out.println(conversation.toString());
        
        this.writeConversation(conversation, outputFilePath);
        System.out.println(blackList.toString());
        // TODO: Add more logging...
        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
        //System.out.println(conversation.toString());
        //System.out.println(conversation.messages.iterator().next().toString());
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
            	//Call processing function
                String[] split = parseLine(line);
                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
            }
            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The input file " + inputFilePath +" was not found.");
        } catch (IOException e) {
            throw new InputException();
        }
    }
    
    /**
     * Represents a helper to parse a read line from an input file.
     * @param line the line read from the the input file
     * @return The string array representing the message
     */
    public String[] parseLine (String line){
    	//Raw split
    	String [] split = line.split(" ");
    	
    	String [] processed = new String [3];
    	processed [0] = split[0];
    	processed [1] = split [1];
    	processed [2] = split [2];
    	//Assuming spaces are not allowed in usernames, concatenate all words in the content.
    	for (int i = 3 ; i < split.length ; i++) {
             processed[2] = processed[2] + " " + (split[i]);
         }
    	return processed;
    }
    
    /**
     * Represents a helper to filter messages from the given users.
     * @param conversation The unfiltered conversation
     * @param user The user on which to filter
     * @return conversation The filtered conversation
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation UserFilterer (Conversation conversation, String user) throws Exception {
    	//If the filtering is not used. "None" is just for testing purposes.
    	if (user.equals("") || user.toLowerCase().equals("none"))
    	{
    		return conversation;
    	}
    	 List<Message> messages = new ArrayList<Message>();
    	 
         for (Message mes : conversation.getMessages()) {
             if (mes.getSenderId().equals(user))
        	 messages.add(mes);
         }
         return new Conversation(conversation.getName(), messages);
    }
    
    /**
     * Represents a helper to filter messages with the given words.
     * @param conversation The unfiltered conversation
     * @param user The word on which to filter
     * @return conversation The filtered conversation
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation WordFilterer (Conversation conversation, String word) throws Exception {
    	if (word.equals("") || word.toLowerCase().equals("none"))
    	{
    		return conversation;
    	}
    	 List<Message> messages = new ArrayList<Message>();
    	 
         for (Message mes : conversation.getMessages()) {
             if (mes.getContent().toLowerCase().contains(word.toLowerCase()))
        	 messages.add(mes);
         }
         return new Conversation(conversation.getName(), messages);
    }
    
    /**
     * Represents a helper to edit messages and hide blacklisted words.
     * @param conversation The unfiltered conversation
     * @param blackList The words to replace
     * @return conversation The filtered conversation
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation blackListChecker (Conversation conversation, ArrayList<String> blackList) throws Exception {
    	if (blackList.size() == 1 && blackList.get(0) == "")
    	{
    		return conversation;
    	}
    	 List<Message> messages = new ArrayList<Message>();
    	 String temp = "";
         for (Message mes : conversation.getMessages()) {
        	 temp = mes.getContent();
        	 for (String word : blackList){
                 temp = temp.replaceAll("(?i)"+word, "*redacted*");
        	 }
        	 messages.add(new Message(mes.getTimestamp(), mes.getSenderId(),temp));
         }
         return new Conversation(conversation.getName(), messages);
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws Exception {
        // TODO: Do we need both to be resources, or will buffered writer close the stream?
        try (OutputStream os = new FileOutputStream(outputFilePath, true);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

            // TODO: Maybe reuse this? Make it more testable...
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
            //Avoid gson escape character changing
            gsonBuilder.disableHtmlEscaping();
            Gson g = gsonBuilder.create();
            //Write in the required format
            bw.write(g.toJson(conversation));
            
        } catch (FileNotFoundException e) {
            // TODO: Maybe include more information?
            throw new IllegalArgumentException("The file " + outputFilePath + " was not found.");
        } catch (IOException e) {
            // TODO: Should probably throw different exception to be more meaningful :/
            throw new OutputException();
        }
    }

    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
    
    class InstantDeserializer implements JsonDeserializer<Instant> {

        @Override
        public Instant deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (!jsonElement.isJsonPrimitive()) {
                throw new JsonParseException("Expected instant represented as JSON number, but no primitive found.");
            }

            JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();

            if (!jsonPrimitive.isNumber()) {
                throw new JsonParseException("Expected instant represented as JSON number, but different primitive found.");
            }

            return Instant.ofEpochSecond(jsonPrimitive.getAsLong());
        }
    }

}
