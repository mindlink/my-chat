package com.mindlinksoft.recruitment.mychat;
import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
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
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);//I have set the program arguments in the Run->Edit configuration option.

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
        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);

        /*
        After the first part of file->JSON conversion. User can select other tasks.
         */
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter number)\n" +
                "1)Filter conversation by Username \n"+
                "2)Filter by specific word \n"+
                "3)Hide specific words(separate words by space) \n"+
                "4)Report \n"+
                "Any number to Exit \n");

        while (true) {
            System.out.print(">");
            int input=0;
            try {
                input = Integer.parseInt(br.readLine());
            }
            catch(NumberFormatException e){
                System.out.println("Enter number from the menu list.");
                continue;
            }

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
                System.out.println("Report generated.");
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
        boolean flag=false;
        for(Message m: conversation.messages) {
            name=m.senderId;
            for (Message msg : conversation.messages) {
                if(name.equals(msg.senderId)){
                    count++;
                }
            }
            for(User u:report.getUsersList()) {
                if(u.name.equals(name)){
                    flag=true;
                    break;
                }
            }
            if(!flag){
                User user = new User(name,count);
                report.addUsers(user);//Adds user if the username does not exists in the list.
            }
            flag=false;
            count=0;
        }
        Collections.sort(report.getUsersList());
        writeReport(report, outputFilePath);
    }

    /**
     * Write report to JSON.
     */
    public void writeReport(Report report,String outputFilePath) throws Exception {
        try {
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFilePath), "UTF8"));

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

            Gson gson = gsonBuilder.disableHtmlEscaping().create();
            gson.toJson(report, out);
            out.close();
        }catch (FileNotFoundException e) {
            throw new IllegalArgumentException("A FileNotFoundException was caught :"+e.getMessage());
        } catch (IOException e) {
            throw new IOException("An IOException was caught :"+e.getMessage());
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
        try {
            Writer writer = new FileWriter(outputFilePath);
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

            Gson g = gsonBuilder.disableHtmlEscaping().create();
            g.toJson(conversation, writer);
            writer.close();
        }catch (FileNotFoundException e) {
            throw new IllegalArgumentException("A FileNotFoundException was caught :"+e.getMessage());
        } catch (IOException e) {
            throw new IOException("An IOException was caught :"+e.getMessage());
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
            String line="";
            String message="";
            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ");
                if(split.length>2) {
                    message = split[2];
                    if(split.length>3) {
                        for (int i = 3; i < split.length; i++) {
                            message = message + " " + split[i];
                        }
                    }
                }
                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], message));
                message="";
            }
            return new Conversation(conversationName, messages);
        }catch (FileNotFoundException e) {
            throw new IllegalArgumentException("A FileNotFoundException was caught :"+e.getMessage());
        } catch (IOException e) {
            throw new IOException("An IOException was caught :"+e.getMessage());
        }
    }

    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
