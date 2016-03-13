package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import com.mindlinksoft.recruitment.mychat.ConversationExporter.InstantSerializer;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.StringUtils;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {
	private Gson gson;
	
	/**
     * The application entry point.
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception {
    	/**
    	 * We are passing the Gson object into the constructor to make it more testable.
    	 * This is because we can 'prepare' gsonBuilder instance in the tests
    	 * and pass it to ConversationExporter
    	 */
    	GsonBuilder gsonBuilder = new GsonBuilder().disableHtmlEscaping();
        gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
        Gson gson = gsonBuilder.create();
        
    	ConversationExporter exporter = new ConversationExporter(gson);
    	
        ConversationExporterConfiguration configuration = 
        		new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration);
    }
    
    public ConversationExporter(Gson gson) {
    	this.gson = gson;
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param configuration with all the command line arguments
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(ConversationExporterConfiguration configuration) throws Exception {
        Conversation conversation = this.readConversation(configuration.inputFilePath);
        
        if (configuration.username != null) {
        	conversation.applyFilter(AppConstant.USERNAME, configuration.username);
        }
        
        if (configuration.keyword != null) {
        	conversation.applyFilter(AppConstant.KEYWORD, configuration.keyword);
        }
        
        if (configuration.blacklist != null) {
        	conversation.blacklistKeyword(configuration.blacklist);
        }
        
        if (configuration.hideCC) {
        	conversation.hideCreditCard();
        }
        
        if (configuration.hideId) {
        	conversation.obfuscateId();
        }
        
        if (configuration.includeReport) {
        	conversation.includeReport();
        }
        
        this.writeConversation(conversation, configuration.outputFilePath);

        // TODO: Add more logging...
        System.out.println(AppConstant.CONVERSATION_EXPORTED +
        		configuration.inputFilePath + AppConstant.EXPORTED_TO + configuration.outputFilePath + "'");
    }

	/**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws Exception {
        // TODO: Do we need both to be resources, or will buffered writer close the stream?
    	try (OutputStream os = new FileOutputStream(outputFilePath, false);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

            bw.write(gson.toJson(conversation));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(outputFilePath + AppConstant.FIELD_SEPARATOR + 
            		AppConstant.NO_FILE_FOUND);
        } catch (IOException e) {
            // TODO: Should probably throw different exception to be more meaningful :/
            throw new Exception(e.getMessage());
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
                String[] fields = parseConversationLine(line);
                Instant time = Instant.ofEpochSecond(Long.parseUnsignedLong(fields[0]));
                messages.add(new Message(time, fields[1], fields[2]));
            }
            
            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(e.getMessage());
        } catch (NumberFormatException e) {
        	throw new NumberFormatException (e.getMessage() + AppConstant.INSTANT_CONVERSION_IMPOSIBLE);
        } catch (IOException e) {
            throw new Exception(e.getMessage());
        }
    }
    
    /**
     * A helper to parse a line of a conversation.
     * @param rawLine represents a raw line from input file.
     * @return String[] contains the parsed data.
     * @throws Exception Thrown when something bad happens.
     */
    public String[] parseConversationLine(String rawLine) throws Exception {
    	String[] fields = new String[3];
    	int firstSeparatorPosition = rawLine.indexOf(AppConstant.FIELD_SEPARATOR);
    	
    	if (firstSeparatorPosition < AppConstant.MINIMUM_SEPARATOR_POSITION) {
    		throw new Exception(AppConstant.EXCEPTION_MISSING_FIRST_FIELD);
    	}
    	
    	fields[0] = rawLine.substring(0, firstSeparatorPosition);
    	
    	int secondSeparatorPosition = rawLine.indexOf(AppConstant.FIELD_SEPARATOR, firstSeparatorPosition + 1);

    	if (secondSeparatorPosition < firstSeparatorPosition + AppConstant.MINIMUM_SEPARATOR_POSITION)
    		throw new Exception(AppConstant.EXCEPTION_MISSING_THIRD_FIELD);
    	
    	fields[1] = rawLine.substring(firstSeparatorPosition + 1, secondSeparatorPosition);
    	fields[2] = rawLine.substring(secondSeparatorPosition + 1);

    	return fields;
    }
    

    public static class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
