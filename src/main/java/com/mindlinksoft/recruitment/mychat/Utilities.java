package com.mindlinksoft.recruitment.mychat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * Contains all helper methods for package.
 *
 * Inherited by:
 *              - ConversationExporter
 *              - ConversationExporterTests
 *              - ObjectToBeExported
 *              - Report
 */
public abstract class Utilities {

    /**
     * Represents a helper to preprocess the given input file from {@code inputFilePath}.
     * It trims white spaces and new lines from start and end of each line and removes lines without any characters
     *
     * @param inputFilePath The path to the input file.
     * @throws Exception Thrown when IO processes fail.
     * @return trimmed file path
     */
    protected static String preprocessInputFile(String inputFilePath) throws Exception {

        /*Take line from input file, trim it, write it in output file
        * input file: inputFilePath.txt
        * output file: preprocessedinputFilePath.txt (i.e. an intermediary file)
        */
        String preprocessedFilePath = "preprocessed" + inputFilePath + ".txt";
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            OutputStream os = new FileOutputStream(preprocessedFilePath, false);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

            String line = new String();
            while (((line = r.readLine()) != null)) {

                    //Remove white spaces from start and end of string
                    line = StringUtils.strip(line);
                    if(!line.equals("")) {
                        bw.write(line + "\n");
                    }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("The input file was not found.");
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("The reading/writing operation failed on the found file.");
        }

        return preprocessedFilePath;
    }

    /**
     * Carries out the main business of the program, by calling all needed functions.
     *      - Preprocesses input file
     *      - Uses the preprocessed file to fill a Conversation object with the conversation existing on the file
     *      - Creates the ObjectToBeExported with the original/filtered conversation and the optional report
     *      - Converts the ObjectToBeExported to JSON
     *      - Writes JSON to output file

     * @param config The conversation configuration settings.
     * @throws Exception Thrown when something bad happens.
     */
    protected static void exportConversation(ConversationExporterConfiguration config) throws Exception {

        String processedFile = preprocessInputFile(config.getInputFilePath());

        //TODO: Why am I not getting an error when I ignore the Conversation return value of readConversation?

        //Program Business below
        Conversation conversation = readConversation(processedFile); //read Conversation
        ObjectToBeExported report = new ObjectToBeExported(conversation, config); //generate ObjectToBeExported
        String reportInJSON = convertToJSON(report); //convert ObjectToBeExported -> JSON string
        writeToFile(reportInJSON, config.getOutputFilePath()); //print JSON string report to output file
    }

    /**
     * Represents a helper to convert an object to JSON string
     *
     * @param ObjectToConvert The object to convert.
     */
    protected static String convertToJSON(Object ObjectToConvert){

        // TODO: Maybe reuse this? Make it more testable...
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Instant.class, new ConversationExporter.InstantSerializer());

        //Fixed Bug: Disable HTML Escaping -> treat ' as normal character
        Gson g = gsonBuilder.disableHtmlEscaping().setPrettyPrinting().create();
        return g.toJson(ObjectToConvert);
    }
    /**
     * This is a printer.
     * Helper method to write the given string {@code toWrite} to the {@code outputFilePath}
     *
     * @param toWrite The string to write.
     * @param outputFilePath The file path where the string should be written.
     *
     * @throws Exception Thrown when writing to file fails.
     */
    protected static void writeToFile(String toWrite, String outputFilePath) throws Exception {
        // TODO: Do we need both to be resources, or will buffered writer close the stream?
        try (
                OutputStream os = new FileOutputStream(outputFilePath, false);
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
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     *
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing the input file information.
     *
     * @throws Exception Thrown when IO operations fail.
     */
    protected static Conversation readConversation(String inputFilePath) throws Exception {
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ");
                for(int i = 3; i < split.length; i++) {
                    split[2] = split[2] + " " + split[i];
                }

                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("The input file was not found.");
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Input file was found, but reading it failed.");
        }
    }

    /**
     * Function that filters the given {@code conversation} by a user, as found in {@code config}
     *
     * @param conversation The conversation to be MUTATED by being filtered.
     * @param config The configuration settings that contain the user name to filter by.
     */
    protected void filterByUser(Conversation conversation, ConversationExporterConfiguration config){

        if(config.getUser() != null){
            String user = config.getUser();
            List<Message> tempMsgCollection = new ArrayList<>();
            for(Message msg : conversation.messages){
                if(msg.senderId.equals(user)){
                    tempMsgCollection.add(msg);
                }
            }
            conversation.messages = tempMsgCollection;
        }
    }

    /**
     * Function that filters the given {@code conversation} by a keyword, as found in {@code config}
     *
     * @param conversation The conversation to be MUTATED by being filtered.
     * @param config The configuration settings that contain the keyword to filter by.
     */
    protected void filterByKeyword(Conversation conversation, ConversationExporterConfiguration config){

        if(config.getKeyword() != null){
            String keyword = config.getKeyword();
            List<Message> tempMsgCollection = new ArrayList<>();
            for(Message msg : conversation.messages){
                if(msg.content.contains(keyword)){
                    tempMsgCollection.add(msg);
                }
            }
            conversation.messages = tempMsgCollection;
        }
    }

    /**
     * Function that censors the given {@code conversaton} by words in a blacklist, as found in {@code config}
     * The function is case sensitive
     *
     * @param conversation The conversation to be MUTATED by being censored.
     * @param config The configuration settings that contain the blacklist of words to censor from.
     */
    protected void hideWords(Conversation conversation, ConversationExporterConfiguration config){
        if(config.getBlackList() != null){
            String[] blackList = config.getBlackList();
            for(String word:blackList){
                for(Message msg: conversation.messages){
                    if(msg.content.contains(word)){
                        msg.content = msg.content.replaceAll("(?i)" + word,"*redacted*");
                    }
                }
            }
        }
    }

    /**
     * Function that generates the {@code UserActivity} metric, by analyzing the {@code conversation}
     *
     * @param conversation The conversation to be analyzed, but NOT mutated.
     * @return {@code loopList}
     *         Returns a list of UserActivity objects, each one containing
     *         a user name and the frequency of their respective messages.
     */
    protected List<UserActivity> generateMetric1(Conversation conversation){
        List<UserActivity> loopList = new ArrayList<>();

        //First add all unique users to list
        boolean loopFlag = false;
        for(Message msg: conversation.messages){
            loopFlag = false;
            for(UserActivity item:loopList) {
                if (item.getSender().equals(msg.senderId)) {
                    loopFlag = true;
                    break;
                }
            }
            if(!loopFlag) {
                loopList.add(new UserActivity(msg.senderId, 0));
            }
        }

        //Then iterate through conversation to track frequency per user
        for(Message msg: conversation.messages){
            for(UserActivity item:loopList){
                if(item.getSender().equals(msg.senderId)){
                    Integer count = item.getCount();
                    item.setCount(count + 1);
                }
            }
        }

        return loopList;
    }
}
















