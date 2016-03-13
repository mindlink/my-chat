package com.mindlinksoft.recruitment.mychat.services;

import com.google.gson.*;
import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.models.Message;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Service to handle any file input or output operations.
 */
public class FileIOService {
	
    /**
     * Read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when it cannot read from the specified file.
     */
    public Conversation readConversation(String inputFilePath) throws Exception {
        try {
        	InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is));

            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;
            
            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ", 3);

                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
            }
            
            r.close();

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Could not find the file " + inputFilePath, e);
        } catch (IOException e) {
            throw new IOException("There was a problem reading the file " + inputFilePath, e);
        }
    }

    /**
     * Write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when it cannot write to the specified file.
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws Exception {
        // TODO: Do we need both to be resources, or will buffered writer close the stream?
        try {
        	OutputStream os = new FileOutputStream(outputFilePath, false);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

            // TODO: Maybe reuse this? Make it more testable...
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

            Gson g = gsonBuilder.create();
            
            bw.write(g.toJson(conversation));
            bw.close();
            
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Could not find the file " + outputFilePath);
        } catch (IOException e) {
            throw new IOException("There was a problem writing to the file " + outputFilePath, e);
        }
    }

    class InstantSerializer implements JsonSerializer<Instant> {
    	public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
