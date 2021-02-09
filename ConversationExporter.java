package mindlink;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
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
        //ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
       // String[] args_key = {"C:\\Users\\andyt\\Documents\\NetBeansProjects\\mindlink\\chat.txt", "C:\\Users\\andyt\\Documents\\NetBeansProjects\\mindlink\\keytest.json", "-k:pie", "-b:some,like,society,head"};
    
        
        //exporter.exportConversation(args_key);
        
        exporter.exportConversation(args);

    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(String[] args) throws IOException,IllegalArgumentException {
        Conversation conversation = this.readConversation(args[0]);
        ChatHandler2 ch = new ChatHandler2(args, conversation.getMessages());
        ArrayList<Message> messages = ch.applyFilters(args);
        conversation.messages = messages;
        HashMap<String, Integer> activity = ch.activity();
        //set conv with newly chopped messages
        conversation.setActivity(activity);
        this.writeConversation(conversation, args[1]);

        // TODO: Add more logging...
        System.out.println("Conversation exported from '" + args[0] + "' to '" + args[1]);
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws IOException,IllegalArgumentException {
        // TODO: Do we need both to be resources, or will buffered writer close the stream?
        try (OutputStream os = new FileOutputStream(outputFilePath, true);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

            
            JsonConverter jc = new JsonConverter();
            bw.write(jc.convToJson(conversation));
        } catch (FileNotFoundException e) {
            // TODO: Maybe include more information?
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            // TODO: Should probably throw different exception to be more meaningful :/
            throw new IOException("Unable to write to file at " + outputFilePath);
        }
    }

    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file. 
    * @throws Exception Thrown when something bad happens.
     */
    public Conversation readConversation(String inputFilePath) throws IOException  {
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            ArrayList<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ", 3);
                
                //here it should go through the filters

                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
                // conversationHandler takes original messages; it should have 2 lists: original messages and post-filter 
           }

            return new Conversation(conversationName,  messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File at " + inputFilePath + " not found.");
        } catch (IOException e) {
            throw new IOException("Unable to read from file at " + inputFilePath);
        }
    }

    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
