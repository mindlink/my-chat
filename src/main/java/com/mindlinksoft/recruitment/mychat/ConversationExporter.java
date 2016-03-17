package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;

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

        //exporter.exportConversation(configuration.getInputFilePath(), configuration.getOutputFilePath());
        
        
        
        String dummyString = "Hello".toLowerCase();
        
        exporter.exportConversationFilteredByKeyWord(configuration.getInputFilePath(), configuration.getOutputFilePath(), dummyString);
        System.out.println("Hello");
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
    
    private void exportConversationFilteredByKeyWord(String inputFilePath, String outputFilePath, String keyWord) throws Exception
    {
        //Get the whole conversation
       Conversation conversation = this.readConversation(inputFilePath);
       
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
       this.writeConversation(filteredConversation, outputFilePath);
    }
    
    private void exportConversationFilteredByUsername(String inputFilePath, String outputFilePath, String userName)throws Exception
    {
       //Get the whole conversation
       Conversation conversation = this.readConversation(inputFilePath);
       
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
       this.writeConversation(filteredConversation, outputFilePath);
    }

    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
