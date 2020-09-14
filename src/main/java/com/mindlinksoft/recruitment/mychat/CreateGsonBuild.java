package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import com.mindlinksoft.recruitment.mychat.constructs.Conversation;
import com.mindlinksoft.recruitment.mychat.constructs.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.constructs.Message;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.*;

/**
 * Create the JSON string content for the JSON output file.
 */
public class CreateGsonBuild
{
    // The configuration for the exporter.
    private ConversationExporterConfiguration config;
    // The obfuscated user mappings, between original userIDs and generatedIDs.
    private Map<String, String> obfMappings;

    /**
     * Convert a {@link Conversation} to JSON string format.
     *
     * @param conversation The {@link Conversation} to be transformed into a JSON string.
     * @return The JSON string.
     */
    public String convert(Conversation conversation, ConversationExporterConfiguration conversationExporterConfiguration) throws Exception
    {
        this.config = conversationExporterConfiguration;
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
        if (config.isObf()) {
            return gsonBuilder.create().toJson(obfuscateUsers(conversation));
        } else {
            return gsonBuilder.create().toJson(conversation);
        }
    }

    /**
     * Obfuscates users in a {@link Conversation}.
     *
     * @param conversation The conversation to be obfuscated.
     * @return A newly obfuscated {@link Conversation}.
     */
    private Conversation obfuscateUsers(Conversation conversation) throws Exception
    {
        obfMappings = new HashMap<>();
        Set<String> generatedIDs = new HashSet<>();
        String name = conversation.getName();
        List<Message> messages = new ArrayList<>(conversation.getMessages());
        Random rnd = new Random();
        for (Message m : messages) {
            String sender = m.getSenderId();
            if (obfMappings.containsKey(sender)) {
                m.setSenderId(obfMappings.get(sender));
            } else {
                String senderObf;
                do {
                    senderObf = String.format("%06d", rnd.nextInt(999999));
                } while (generatedIDs.contains(senderObf));
                generatedIDs.add(senderObf);
                obfMappings.put(sender, senderObf);
                m.setSenderId(senderObf);
            }
        }
        writeObfuscatedUsers(obfMappings);
        return new Conversation(name, messages);
    }

    /**
     * Write to a file the list of obfuscated users.
     *
     * @param map The mapping between old senderID and generatedID for each obfucated user.
     * @throws Exception Thrown when something bad happens.
     */
    private void writeObfuscatedUsers(Map<String, String> map) throws Exception
    {
        // If the obfuscate file path exists, delete the old file and create a new one.
        File obfFile = new File(config.getOBF_FILE_PATH());
        if (obfFile.exists() && obfFile.isFile()) {
            obfFile.delete();
            obfFile.createNewFile();
        }
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(obfFile, true)))) {
            bw.write("Obfuscated User Mappings:\n");
            int index = 1;
            for (Map.Entry<String, String> mapEntry : new TreeMap<>(map).entrySet()) {
                String line = index + ") senderID: " + mapEntry.getKey() + " -> generatedID: " + mapEntry.getValue() + "\n";
                bw.write(line);
                index++;
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Obfuscate file path argument: '" + config.getOBF_FILE_PATH() + "', could not be found. More details:" + e.getCause());
        } catch (IOException e) {
            throw new IOException("Obfuscate file path argument: '" + config.getOBF_FILE_PATH() + "', could not be written to. More details: " + e.getCause());
        }
    }

    public Map<String, String> getObfMappings()
    {
        return obfMappings;
    }

    static class InstantSerializer implements JsonSerializer<Instant>
    {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext)
        {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
