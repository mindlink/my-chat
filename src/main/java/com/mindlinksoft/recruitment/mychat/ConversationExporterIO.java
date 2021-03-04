package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ConversationExporterIO {
    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation readConversation(String inputFilePath) throws Exception {
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ");
                StringBuilder content = new StringBuilder();

                // adds the rest of the splits to the message content
                for (int i = 2; i < split.length; i++) {
                    content.append(split[i]);
                    // adds spaces in between all the splits (makes sure an extra one isn't added to the end)
                    if (i < split.length - 1) {
                        content.append(" ");
                    }
                }

                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], content.toString()));
            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Cannot find file named \'" + inputFilePath + "\'.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Input buffer size is ef illegal value. Must be a non-zero value");
        } catch (IOException e) {
            throw new IOException("An I/O error has occurred.");
        } catch (Exception e) {
            throw new IOException("An error has occurred.");
        }
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws Exception {
        try (OutputStream os = new FileOutputStream(outputFilePath, false);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

            Gson g = gsonBuilder.create();
            bw.write(g.toJson(conversation));

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Cannot find file named \'" + outputFilePath + "\'.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Output buffer size is ef illegal value. Must be a non-zero value");
        } catch (IOException e) {
            throw new IOException("An I/O error has occurred.");
        } catch (Exception e) {
            throw new IOException("An error has occurred.");
        }
    }

    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
