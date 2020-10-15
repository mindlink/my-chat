package com.mindlinksoft.recruitment.mychat;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.lang.reflect.Array;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Input-Output module
 *
 * Responsibilities:
 *      - read from .txt file and store in Conversation object
 *      - write Object to .JSON text file
 *
 */
public class IO {

    private String ifilePath;
    private String ofilePath;
    private Conversation conversationRead;


    protected IO(ConversationExporterConfiguration config){
        this.ifilePath = config.getInputFilePath();
        this.ofilePath = config.getOutputFilePath();
    }

    /**
     * Reads from input file, removes white spaces and newlines from beginning
     * and end of file and individual lines, stores input to {@code conversationRead}
     *
     * @throws Exception
     */
    protected void readConversation() throws Exception {
        try(InputStream is = new FileInputStream(this.ifilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<>();

            String line = new String();
            ArrayList<String> strArr = new ArrayList<>();

            //Remove white spaces from start and end of string
            while (((line = r.readLine()) != null)) {

                line = StringUtils.strip(line);

                if (!line.equals("")) {
                    strArr.add(line);
                }
            }

            //Read messages one-by-one and add them to "messages" list
            for (int i = 1; i < strArr.size(); i++) {

                String[] split = strArr.get(i).split(" ");

                for(int j = 3; j < split.length; j++) {
                    split[2] = split[2] + " " + split[j];
                }
                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
            }

            this.conversationRead = new Conversation(strArr.get(0), messages);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("The input file was not found.");
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Input file was found, but reading it failed.");
        }
    }

    /**
     * Writes Object to JSON file
     *
     * input: Object that will be written to file
     */
    protected void writeObjectToJsonFile(Object objectToWrite) throws Exception{

        String jsonString = convertToJSON(objectToWrite);

        try {
            this.writeToFile(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This is a printer.
     * Helper method to write the given string {@code toWrite} to the {@code outputFilePath}
     *
     * @param toWrite The string to write.
     *
     * @throws Exception Thrown when writing to file fails.
     */
    protected void writeToFile(String toWrite) throws Exception {
        // TODO: Do we need both to be resources, or will buffered writer close the stream?
        try (
                OutputStream os = new FileOutputStream(this.ofilePath, false);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {


            bw.write(toWrite);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("The output file was not found.");
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Output file was found, but writing to it failed.");
        }
    }

    /**
     * Represents a helper to convert an object to JSON string
     *
     * @param ObjectToConvert The object to convert.
     */
    protected String convertToJSON(Object ObjectToConvert){

        // TODO: Maybe reuse this? Make it more testable...
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Instant.class, new ConversationExporter.InstantSerializer());

        //Fixed Bug: Disable HTML Escaping -> treat ' as normal character
        Gson g = gsonBuilder.disableHtmlEscaping().setPrettyPrinting().create();
        return g.toJson(ObjectToConvert);
    }


    public String getIfilePath() {
        return ifilePath;
    }

    public void setIfilePath(String ifilePath) {
        this.ifilePath = ifilePath;
    }

    public String getOfilePath() {
        return ofilePath;
    }

    public void setOfilePath(String ofilePath) {
        this.ofilePath = ofilePath;
    }

    public Conversation getConversationRead() {
        return conversationRead;
    }

    public void setConversationRead(Conversation conversationRead) {
        this.conversationRead = conversationRead;
    }
}
