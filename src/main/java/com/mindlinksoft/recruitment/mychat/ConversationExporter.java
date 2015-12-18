package com.mindlinksoft.recruitment.mychat;
import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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

        exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath);
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(String inputFilePath, String outputFilePath) throws Exception {

        Conversation conversation = this.readConversation(inputFilePath);

        this.writeConversation(conversation, outputFilePath);

        // TODO: Add more logging...
        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter number)\n" +
                "1)Filter conversation by Username \n"+
                "2)Filter by specific word \n"+
                "3)Hide specific words(separate words by space) \n"+
                "4)Report \n"+
                "5)Exit \n");

        while (true) {
            System.out.print(">");
            int input = Integer.parseInt(br.readLine());

            if (input == 1) {
                System.out.print("Enter username:");
                String username = br.readLine();
                filterUsername(conversation,username,outputFilePath);
                System.out.println("Filtered by username.");
            }
            else if(input==2){
                System.out.print("Enter keyword:");
                String keyword = br.readLine();
                filterKeyword(conversation, keyword, outputFilePath);
                System.out.println("Filtered by specific word.");
            }
            else if(input==3){
                System.out.print("Enter specific keyword(separate words by space):");
                String keyword = br.readLine();
                String[] words=keyword.split(" ");
                redactWords(conversation, words, outputFilePath);
                System.out.println("Redacted words.");
            }
            else if (input ==4){
                report(conversation, outputFilePath);
                System.out.println("Redacted words.");
            }
            else{
                System.out.println("Exiting.");
                return;
            }
        }
    }

    /**
     *Creates report according to the input text file.
     */
    public void report(Conversation conversation, String outputFilePath) throws Exception {
        Report report = new Report();
        int count=0;
        String name="";
        for(Message m: conversation.messages) {
            name=m.senderId;
            for (Message msg : conversation.messages) {
                if(name.equals(msg.senderId)){
                    count++;
                }
            }
            report.put(name,count);
            count=0;
        }
        writeReport(report.sortValue(),outputFilePath);
    }

    /**
     * Write report to JSON.
     */
    public void writeReport(ArrayList counts,String outputFilePath) throws Exception {
        try {
            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(outputFilePath), "UTF8"));
            Gson gson = new GsonBuilder().create();
            gson.toJson(counts.toString(), out);
            out.close();
        }catch (FileNotFoundException e) {
            // TODO: Maybe include more information?
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            // TODO: Should probably throw different exception to be more meaningful :/
            throw new Exception("Something went wrong");
        }
    }

    /**
     Redact words from the conversation.
     */
    public void redactWords(Conversation conv,String[] words,String outputFilePath) throws Exception {
        ArrayList<Message> newList = new ArrayList<Message>();
        for (Message msg : conv.messages) {
            String  username=msg.senderId;
            String content=msg.content;
            for(String word:words) {
                if (msg.senderId.toLowerCase().equals(word.toLowerCase())) {
                    username = msg.senderId.replaceAll(msg.senderId, "*redacted*");
                }
                if(msg.content.toLowerCase().contains(word.toLowerCase())){
                    content= msg.content.replaceAll(msg.content, "*redacted*");
                }
            }
            Message newmsg = new Message(msg.timestamp,username,content);
            newList.add(newmsg);
        }

        Conversation newCov = new Conversation(conv.name,newList);
        writeConversation(newCov, outputFilePath);
    }

    /**
     Filter conversation by keyword. Creates new list of type Message from the specific keyword.
     */
    public void filterKeyword(Conversation conv,String keyword,String outputFilePath) throws Exception {
        ArrayList<Message> newList = new ArrayList<Message>();
        for(Message msg:conv.messages){
            if(msg.senderId.toLowerCase().equals(keyword.toLowerCase()) || msg.content.toLowerCase().contains(keyword.toLowerCase())){
                newList.add(msg);
            }
        }
        Conversation newCov = new Conversation(conv.name,newList);
        writeConversation(newCov,outputFilePath);
    }

    /**
    Filter conversation by username. Creates new list of type Message from the specific user.
     */
    public void filterUsername(Conversation conv,String username,String outputFilePath) throws Exception {
        ArrayList<Message> newList = new ArrayList<Message>();
        for(Message msg:conv.messages){
            if(msg.senderId.toLowerCase().equals(username)){
                newList.add(msg);
            }
        }
        Conversation newCov = new Conversation(conv.name,newList);
        writeConversation(newCov,outputFilePath);
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws Exception {
        // TODO: Do we need both to be resources, or will buffered writer close the stream?
        /*try (OutputStream os = new FileOutputStream(outputFilePath, true);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

            // TODO: Maybe reuse this? Make it more testable...
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

            Gson g = gsonBuilder.create();
            bw.write(g.toJson(conversation));
            System.out.println(g.toJson(conversation));

        } catch (FileNotFoundException e) {
            // TODO: Maybe include more information?
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            // TODO: Should probably throw different exception to be more meaningful :/
            throw new Exception("Something went wrong");
        }*/
        try {
            Writer writer = new FileWriter(outputFilePath);
            Gson gson = new GsonBuilder().create();
            gson.toJson(conversation, writer);
            writer.close();
        }catch (FileNotFoundException e) {
            // TODO: Maybe include more information?
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            // TODO: Should probably throw different exception to be more meaningful :/
            throw new Exception("Something went wrong");
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
            String message="";
            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ");
                if(split.length>2) {
                    for (int i = 2;i<split.length;i++) {
                        message = message+" "+split[i];
                    }
                }
                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], message));
                System.out.println(message);
                message="";
            }


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
