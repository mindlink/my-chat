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

/**
 * This class acts as a model. provides methods to be used by commands.
 *
 * @author esteban
 */
public class Model {

    boolean done = false;
    private String inputFile;
    private String outputFile;

    /**
     * Boolean method used to handle the loop in controller.
     *
     * @return
     */
    public boolean getDone() {
        return done;
    }

    /**
     * Allows the system to set the boolean value to false from the exit command
     * in order to terminate the loop.
     *
     * @param dn true or false.
     */
    public void setDone(boolean dn) {

        this.done = dn;
    }

    /**
     * Model constructor takes String inputFile and String outputFile. and
     * initialises those parameters.
     *
     * @param inputFile
     * @param outputFile
     */
    public Model(String inputFile, String outputFile) {

        this.inputFile = inputFile;
        this.outputFile = outputFile;

    }

    /**
     *
     * @return the inputFile
     */
    public String getInputFile() {
        return inputFile;
    }

    /**
     *
     * @return the outputFile
     */
    public String getOutputFile() {
        return outputFile;
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

            bw.write(conversationBuilder(conversation));
        } catch (FileNotFoundException e) {
            // TODO: Maybe include more information?
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            // TODO: Should probably throw different exception to be more meaningful :/
            throw new Exception("Something went wrong", e);
        }
    }

    /**
     * Method used to build a conversation as a Json format.
     *
     * @param conversation
     * @return returns the conversation as json to be written.
     */
    public String conversationBuilder(Conversation conversation) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

        Gson g = gsonBuilder.create();
        return g.toJson(conversation);
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

            String conversationName = r.readLine();

            return new Conversation(conversationName, formatMessage(r));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            throw new Exception("Something went wrong", e);
        }
    }

    /**
     * Method used to split and format each message. if a different format is
     * required or the input changes this will be the method to look at.
     *
     * @param reader
     * @return
     * @throws Exception
     */
    public List<Message> formatMessage(BufferedReader reader) throws Exception {

        List<Message> messages = new ArrayList<Message>();
        String line;
        try {
            while ((line = reader.readLine()) != null) {

                String[] split = line.split(" ", 3);
                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
            }
        } catch (IOException e) {
            throw new IOException("Problem reading a line", e);
        }
        return messages;

    }

    class InstantSerializer implements JsonSerializer<Instant> {

        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
