package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import com.mindlinksoft.recruitment.mychat.optionSettings.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.*;

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
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        System.out.println("Input file path is: " + configuration.getInputFilePath());
        System.out.println("Output file path is: " + configuration.getOutputFilePath());

        exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, configuration.options);
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param options
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(String inputFilePath, String outputFilePath, ArrayList<OptionSetting> options) throws Exception {
        Conversation conversation = this.readConversation(inputFilePath);

        this.writeConversation(conversation, outputFilePath, options);

        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws IOException Thrown when there is an error writing to the output file.
     */
    public void writeConversation(Conversation conversation, String outputFilePath, ArrayList<OptionSetting> options) throws IOException {
        try (OutputStream os = new FileOutputStream(outputFilePath, false);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

            Gson g = gsonBuilder.create();

            //System.out.println(options);

            for (OptionSetting option : options){
                //System.out.println(Option);
                if (option instanceof Users){
                    //System.out.println("Found Users filter");
                    Users user = (Users)option;

                    for (Iterator<Message> iterator = conversation.messages.iterator(); iterator.hasNext();){
                        Message mess1 = iterator.next();
                        mess1 = user.duringIteration(mess1);
                        if (mess1 == null) {
                            iterator.remove();
                        }
                    }

                }

                if (option instanceof Keywords){
                    //System.out.println("Found Keywords filter");
                    
                    Keywords keyword = (Keywords)option;

                    for (Iterator<Message> iterator = conversation.messages.iterator(); iterator.hasNext();){
                        Message mess1 = iterator.next();
                        mess1 = keyword.duringIteration(mess1);
                        if (mess1 == null) {
                            iterator.remove();
                        }
                    }

                }
                if (option instanceof Blacklist){
                    //System.out.println("Found Blacklist filter");

                    Blacklist blacklist = (Blacklist) option;

                    for (Iterator<Message> iterator = conversation.messages.iterator(); iterator.hasNext();){
                        Message mess1 = iterator.next();
                        mess1 = blacklist.duringIteration(mess1);
                        if (mess1 == null) {
                            iterator.remove();
                        }
                    }
                    /*for (Message mess1 : conversation.messages) {
                        System.out.println(mess1.content);
                    }*/
                }

                if (option instanceof Obfuscate){
                    System.out.println("Found Numbers filter");

                    Obfuscate obfuscate = (Obfuscate) option;

                    for (Iterator<Message> iterator = conversation.messages.iterator(); iterator.hasNext();){
                        Message mess1 = iterator.next();
                        mess1 = obfuscate.duringIteration(mess1);
                        if (mess1 == null) {
                            iterator.remove();
                        }
                    }
                    /*for (Message mess1 : conversation.messages) {
                        System.out.println(mess1.userID);
                    }*/
                }

                if (option instanceof Numbers){
                    //System.out.println("Found Numbers filter");

                    Numbers numberRedact = (Numbers) option;

                    for (Iterator<Message> iterator = conversation.messages.iterator(); iterator.hasNext();){
                        Message mess1 = iterator.next();
                        mess1 = numberRedact.duringIteration(mess1);
                        if (mess1 == null) {
                            iterator.remove();
                        }
                    }
                    /*for (Message mess1 : conversation.messages) {
                        System.out.println(mess1.userID);
                    }*/
                }
            }

            bw.write(g.toJson(conversation));
            /*try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) {
                bw.write((converter.convertToJSON(conversation)));
            }*/

        } catch (FileNotFoundException e) {
            // TODO: Maybe include more information?
            throw new FileNotFoundException("Unable to find output file at: " + outputFilePath);
        } catch (IOException e) {
            // TODO: Should probably throw different exception to be more meaningful :/
            throw new IOException("Error writing to the output file.");
        }
    }

    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when there is an error reading from the input file.
     */
    public Conversation readConversation(String inputFilePath) throws IOException {
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ");

                String[] subList = new String[split.length - 2];
                System.arraycopy(split, 2, subList, 0, split.length - 2);
                String contents = String.join(" ", subList);

                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], contents));
            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Unable to find input file at: " + inputFilePath);
        } catch (IOException e) {
            throw new IOException("Error reading from the input file.");
        }
    }

    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
