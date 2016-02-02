package mychat;

import com.google.gson.*;
import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ConversationExporter {

    public static void main(String[] args) throws Exception {

        ConversationExporter exporter = new ConversationExporter();


        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        exporter.exportConversation(configuration.getInputFilePath(), configuration.getOutputFilePath(), configuration.getUserCommandOptions());
    }


    
    public void exportConversation(String inputFilePath, String outputFilePath, CommandOptions commandOptions) throws Exception {
        Conversation conversation = this.readConversation(inputFilePath, commandOptions);

        this.writeConversation(conversation, outputFilePath);

        // TODO: Add more logging...
        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
    }


    
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

    
    public Conversation readConversation(String inputFilePath, CommandOptions commandOptions) throws Exception {
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String conversationName = reader.readLine();

            List<Message> messages = new ArrayList<Message>();

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] lineSplit = line.split(" ");
                StringBuilder messagecontent = new StringBuilder();
                for (int i = 2; i < lineSplit.length; i++) {
                    messagecontent.append(lineSplit[i]);
                }
                Message message = new Message (Instant.ofEpochSecond(Long.parseUnsignedLong(lineSplit[0])), lineSplit[1], messagecontent.toString());
                if (this.processMessage(message, commandOptions)) {
                    messages.add(message);
                }
                else {
                    continue;
                }

            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            throw new Exception("Something went wrong");
        }
    }

    public boolean processMessage(Message message, CommandOptions commandOptions) {
        FilterHelper filterHelper = new FilterHelper(commandOptions.getConversationFilterOptions());
        MessagePostProcessor postProcessor = new MessagePostProcessor(commandOptions.getConversationPostProcessOptions());
        if (filterHelper.filterMessage(message)) {
            postProcessor.processMessage(message);
            return true;
        }
        else {
            return false;
        }
    }



    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
