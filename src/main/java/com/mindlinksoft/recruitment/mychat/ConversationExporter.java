package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;

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
        
        ConversationExporterConfiguration configuration = new ConversationExporterConfiguration("chat.txt", "output.json");
        
        while(true)
        {
            exporter.menu(configuration);
        }
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param config The file paths.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(ConversationExporterConfiguration config) throws Exception {
        Conversation conversation = this.readConversation(config.getInputFilePath());

        this.writeConversation(conversation, config.getOutputFilePath());

        // TODO: Add more logging...
        System.out.println("Conversation exported from '" + config.getInputFilePath() + "' to '" + config.getOutputFilePath());
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */
    private void writeConversation(Conversation conversation, String outputFilePath) throws Exception {
        // TODO: Do we need both to be resources, or will buffered writer close the stream?
        try (OutputStream os = new FileOutputStream(outputFilePath, true);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

            // TODO: Maybe reuse this? Make it more testable...
            //Create a constructor to pass in the gson builder for testing purposes
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
            
            ArrayList<Message> messages = conversation.getMessages();
            Gson g = gsonBuilder.create();
            
            //Write the Conversation name first
            bw.write(conversation.getName() + "\n");
            
            //Write the actual content
            for(int x = 0; x < messages.size(); x++)
            {
                bw.write(g.toJson(messages.get(x).getTimestamp()));
                bw.write(" ");
                bw.write(g.toJson(messages.get(x).getSenderId()));
                bw.write(" ");
                bw.write(g.toJson(messages.get(x).getContent()));
                bw.write("\n");
            }
            bw.close();
            os.close();
        } catch (FileNotFoundException e) {
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
    private Conversation readConversation(String inputFilePath) throws Exception {
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            ArrayList<Message> messages = new ArrayList<Message>();

            String conversationName = br.readLine();
            String line;

            while ((line = br.readLine()) != null) {
                String[] split = new String[2];
               
                split = line.split(" ", 3);

                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
            }
            br.close();

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            throw new Exception(e.getMessage());
        }
    }
    
    private void exportConversationFilteredByKeyWord(ConversationExporterConfiguration config, String keyWord) throws Exception
    {
        //Get the whole conversation
       Conversation conversation = this.readConversation(config.getInputFilePath());
       
       //Create a array to hold only the filtered messages
       ArrayList<Message> filteredMessages = new ArrayList();
        
       //Cycle through the whole conversation and add only the messages with the key word to the filtered array
       for(Message convo : conversation.getMessages())
       {   
           if(convo.getContent().toLowerCase().contains(keyWord)){
               filteredMessages.add(convo);
           }
       }
       
       //Create a new conversation with the filtered messages and name
       Conversation filteredConversation = new Conversation(conversation.getName(), filteredMessages);
       
       //pass it through to be written to file
       this.writeConversation(filteredConversation, config.getOutputFilePath());
    }
    
    private void exportConversationFilteredByUsername(ConversationExporterConfiguration config, String userName)throws Exception
    {
       //Get the whole conversation
       Conversation conversation = this.readConversation(config.getInputFilePath());
       
       //Create a array to hold only the filtered messages
       ArrayList<Message> filteredMessages = new ArrayList();
        
       //Cycle through the whole conversation and add only the messages with the key word to the filtered array
       for(Message convo : conversation.getMessages())
       {   
           if(convo.getSenderId().toLowerCase().contains(userName)){
               filteredMessages.add(convo);
           }
       }    
       //Create a new conversation with the filtered messages and name
       Conversation filteredConversation = new Conversation(conversation.getName(), filteredMessages);
       
       //pass it through to be written to file
       this.writeConversation(filteredConversation, config.getOutputFilePath());
    }
    
    public void exportConversationByBlackList(ConversationExporterConfiguration config, ArrayList<String> blackList)throws Exception
    {
        String redacted = "*redacted*";
        Conversation conversation = this.readConversation(config.getInputFilePath());
        
        for(Message convo: conversation.getMessages())
        {
            for(String blackListedWord : blackList)
            {
                String filteredConvo = convo.getContent().replaceAll("(?i)" + blackListedWord, redacted);
                convo.setContent(filteredConvo);
            }
        }

        this.writeConversation(conversation, config.getOutputFilePath());
    }
    
    private void menu(ConversationExporterConfiguration config)throws Exception
    {
        System.out.println("Please select a menu option...");
        Scanner sc = new Scanner(System.in);
        String choice = sc.next().toLowerCase();
        
        switch(choice)
        {
            case "e":
                this.exportConversation(config);
                break;
            case "k":
                String keyWord = this.getKeyWord();
                this.exportConversationFilteredByKeyWord(config, keyWord);
                break;
            case "b":
                ArrayList<String> blackList = getBlackArrayList();
                this.exportConversationByBlackList(config, blackList);
                break;
            case "x":
                System.exit(0);
                break;
            default:
                System.out.println("Invalid menu option");
                break;
        }   
    }
    
    private ArrayList<String> getBlackArrayList()
    {
        ArrayList<String> blackList = new ArrayList<String>();
        boolean addAnother = true;
        
        while(addAnother)
        {
            System.out.println("Please enter word to add to black list...");
            Scanner sc = new Scanner(System.in);
            blackList.add(sc.nextLine());
            
            System.out.println("Do you wish to add another? (Y/N)");
            String choice = sc.nextLine().toLowerCase();
            
            if(choice.equals("n"))
                addAnother = false;
        }
        
        return blackList;
    }
    
    private String getKeyWord()
    {
        String keyWord;
        
        System.out.println("Please enter keyword to filter conversation by");
        Scanner sc = new Scanner(System.in);
        
        keyWord = sc.nextLine();
        
        return keyWord;
    }
    
    private String getInputPath()
    {
        String path;
        
        System.out.println("Please specific the INPUT path");
        Scanner sc = new Scanner(System.in);
        
        path = sc.nextLine();
        
        return path;
    }
    
    private String getOutputPath()
    {
        String path;
        
        System.out.println("Please specific the OUTPUT path");
        Scanner sc = new Scanner(System.in);
        
        path = sc.nextLine();
        
        return path;
    }

    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
