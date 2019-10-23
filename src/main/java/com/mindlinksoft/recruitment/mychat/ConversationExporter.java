package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON with options defined in the command line.
 */
public class ConversationExporter {

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @throws IOException 
     * @throws FileNotFoundException 
     */
    public void exportConversation(String inputFilePath, String outputFilePath, ConversationFilter filter) throws FileNotFoundException, IOException {
    	
    	/**
    	 * Read in the conversation
    	 */
        Conversation conversation = this.readConversation(inputFilePath);
        System.out.println("DONE");

        /**
         * If a filter was requested in the command line argument
         */
        if (filter != null) {
        	/**
        	 * Filter the conversation
        	 */
        	conversation = filter.filterConversation(conversation);
        }
        /**
         * Write the final conversation to JSON
         */
        this.writeConversation(conversation, outputFilePath);

        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath + "'");
    }

    
    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws FileNotFoundException, IOException {

    	OutputStream os = new FileOutputStream(outputFilePath, true);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

        Gson g = buildGson();

        bw.write(g.toJson(conversation));
        bw.close();
    }

    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public Conversation readConversation(String inputFilePath) throws FileNotFoundException, IOException {
    	System.out.print("Reading conversation...");
    	BufferedReader br = new BufferedReader(new FileReader(inputFilePath));
		List<Message> messages = new ArrayList<Message>();
		String conversationName = br.readLine();
		String line;
		while ((line = br.readLine()) != null) {
			String[] split = line.split(" ", 3);
			messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
		}
		br.close();
		return new Conversation(conversationName, messages);

    }
    
    private Gson buildGson() {
    	 GsonBuilder gsonBuilder = new GsonBuilder();
         gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
         return gsonBuilder.setPrettyPrinting().create();
    }

    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
