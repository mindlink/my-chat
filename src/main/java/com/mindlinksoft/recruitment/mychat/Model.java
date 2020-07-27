/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindlinksoft.recruitment.mychat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author esteban
 */
public class Model {

    boolean done = false;
    private String inputFile;
    private String outputFile;
    private Message message;

    public boolean getDone() {
        return done;
    }

    public void setDone(boolean dn) {

        this.done = dn;

    }

    public Model(String inputFile, String outputFile) {

        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.message = message;

    }

    public String getInputFile() {
        return inputFile;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public String getSenderID() {
        return message.getSenderId();
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to
     * {@code outputFilePath}.
     *
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(String inputFilePath, String outputFilePath) throws Exception {
        Conversation conversation = this.readConversation(inputFilePath);

        this.writeConversation(conversation, outputFilePath);

        // TODO: Add more logging...
        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the
     * given {@code outputFilePath}.
     *
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be
     * written.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws Exception {
        // TODO: Do we need both to be resources, or will buffered writer close the stream?
        try (OutputStream os = new FileOutputStream(outputFilePath, true);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

            // TODO: Maybe reuse this? Make it more testable...
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

            Gson g = gsonBuilder.create();

            bw.write(g.toJson(conversation));
        } catch (FileNotFoundException e) {
            // TODO: Maybe include more information?
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            // TODO: Should probably throw different exception to be more meaningful :/
            throw new Exception("Something went wrong");
        }
    }

    /**
     * Represents a helper to read a conversation from the given
     * {@code inputFilePath}.
     *
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation readConversation(String inputFilePath) throws Exception {
        try (InputStream is = new FileInputStream(inputFilePath);
                BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {

                //regex and limit fixed.
                String[] split = line.split(" ", 3);

                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            throw new Exception("Something went wrong");
        }
    }

    public Conversation filterConversationByUser(String inputFilePath, String userID) throws Exception {
        try (InputStream is = new FileInputStream(inputFilePath);
                BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {

                //regex and limit fixed.
                String[] split = line.split(" ", 3);
                if (split[1].equals(userID)) {
                    messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
                }
            }
            if (messages.isEmpty()) {
                System.out.println("Conversation has been exported empty, User does not exist.");
            } else {
                System.out.println("Conversation exported from " + getInputFile() + " to " + getOutputFile() + " by user: " + userID);
            }

            return new Conversation(conversationName, messages);

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            throw new Exception("Something went wrong");
        }
    }

    public Conversation filterConversationByKeyword(String inputFilePath, String keyword) throws Exception {
        try (InputStream is = new FileInputStream(inputFilePath);
                BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {

                //regex and limit fixed.
                String[] split = line.split(" ", 3);
                if (split[2].contains(keyword)) {
                    messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
                }
            }

            if (messages.isEmpty()) {
                System.out.println("Conversation has been exported empty, Keyword is not present within the messages.");
            } else {
                System.out.println("Conversation exported from " + getInputFile() + " to " + getOutputFile() + " filtered by: " + keyword);
            }

//            List<Message> key = new ArrayList<Message>();
//            key = messages.stream().filter(user -> user.getContent().contains(keyword)).collect(Collectors.toList());
            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            throw new Exception("Something went wrong");
        }
    }

    public Conversation hideKeyword(String inputFilePath, String keyword) throws Exception {
        try (InputStream is = new FileInputStream(inputFilePath);
                BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {

                //regex and limit fixed.
                String[] split = line.split(" ", 3);
                String hiddenKey = split[2].replaceAll(keyword, "*redacted*");

                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], hiddenKey));

            }

            /*List<Message> key = new ArrayList<Message>();
            key = messages.stream().filter(user -> user.getContent().contains(keyword)).collect(Collectors.toList());*/
            System.out.println("keyword" + keyword);
            System.out.println("messages" + messages);

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            throw new Exception("Something went wrong");
        }
    }

    public Conversation hidePhone(String inputFilePath) throws Exception {
        try (InputStream is = new FileInputStream(inputFilePath);
                BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {

                //regex and limit fixed.
                String[] split = line.split(" ", 3);

                char[] chars = split[2].toCharArray();
                StringBuilder nw = new StringBuilder();
                String hiddenKey = null;

                for (char c : chars) {
                    if (Character.isDigit(c)) {
                        nw.append(c);
                    }
                }
                String num = nw.toString();
                if (num.length() == 11) {
                    hiddenKey = split[2].replaceAll(num, "*redacted*");
                }

                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], hiddenKey));

            }

            /*List<Message> key = new ArrayList<Message>();
            key = messages.stream().filter(user -> user.getContent().contains(keyword)).collect(Collectors.toList());*/
//            System.out.println("keyword" + hiddenKey);
            System.out.println("messages" + messages);

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            throw new Exception("Something went wrong");
        }
    }

    class InstantSerializer implements JsonSerializer<Instant> {

        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }

}
