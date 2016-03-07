package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import Filter.Filter;
import Filter.FilterBlacklist;
import Filter.FilterNone;
import Filter.FilterKeyword;
import Filter.FilterParameters;
import Filter.FilterUser;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter
{
    /**
     * The application entry point.
     * @param args The command line arguments.
     * 
     * I've set the command line arguments in File > Project Properties > Run.
     * Current arguments are - [ chat.txt chat.json ]
     * FYI, I am using NetBeans IDE 8.0.2.
     * 
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception
    {
        System.out.print("Enter a number between 0 to 3 for the filter type\n" +
                                    "0 - No filter/Default.\n" +
                                    "1 - Filter by USERNAME.\n" +
                                    "2 - Filter by KEYWORD. \n" +
                                    "3 - Filter by BLACKLISTED-WORDS.\n");
        
        FilterParameters fp = new FilterParameters();
        int filterType = fp.GetFilterType();
        String[] filterQueries = fp.GetFilterQueries(filterType);
        
        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

        String input = configuration.GetInputFilePath();
        String output = configuration.GetOutputFilePath();
        exporter.exportConversation(input, output, filterType, filterQueries);
    }
    
    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param filterType The filter used to filter the messages, e.g. user, keyword, blacklist.
     * @param filterQuery The query used to filter search results.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(String inputFilePath, String outputFilePath, int filterType, String[] filterQuery) throws Exception
    {
        Conversation conversation = this.readConversation(inputFilePath);

        this.writeConversation(conversation, outputFilePath, filterType, filterQuery);

        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
        
        if (filterType > 0)
        {
            String type = "";
            if (filterType == 1)        type = "Username.";
            else if (filterType == 2)   type = "Keyword(s).";
            else                        type = "Blacklisted word(s).";

            System.out.println("Conversation was filtered by " + type);
        }
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @param filterType The filter used to filter the messages, e.g. user, keyword, blacklist. 
     * @param filterQuery The query used to filter search results.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, String outputFilePath, int filterType, String[] filterQuery) throws Exception
    {
        try (Writer fw = new FileWriter(outputFilePath);)
        {
            /* Did not fully understand the use of custom JSON serialiser, so
               ended up not using GSON stuff. I understand the implications of
               not using GSON - InstantDeserialiser test in ConversationExporterTests.java
               not checked.
            
               GsonBuilder gsonBuilder = new GsonBuilder();
               gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
               Gson g = gsonBuilder.create();
            */
            
            Filter cf;
            switch (filterType)
            {
                case 0:     cf = new FilterNone();
                            break;
                case 1:     cf = new FilterUser();
                            break;
                case 2:     cf = new FilterKeyword();
                            break;
                case 3:     cf = new FilterBlacklist();
                            break;
                default:    cf = new FilterNone();
                            break;
            }
            
            ArrayList<String> messages = cf.Filter(conversation.GetMessages(), filterQuery);
            
            fw.write(conversation.GetConvoName() + "\n");
            for (int i = 0; i < messages.size(); ++i)
                fw.write(messages.get(i));
        }
        catch (FileNotFoundException e)
        {
            throw new FileNotFoundException("The file " + "\"" + outputFilePath + "\"" +
                                                " was not found: " + e.getMessage());
        }
        catch (IOException e)
        {
            throw new IOException("Something IO-related went wrong: " + e.getMessage());
        }
    }

    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation readConversation(String inputFilePath) throws Exception
    {
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is)))
        {
            List<Message> messages = new ArrayList<>();

            String conversationName = r.readLine();
            String line = "";

            while ((line = r.readLine()) != null)
            {
                String[] split = line.split(" ");
                String message = "";
                
                if (split.length > 2)
                {
                    for (int i = 2; i < split.length; ++i)
                    {
                        message += split[i];
                        
                        if (i != split.length - 1)
                            message += " ";
                    }
                }

                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], message));
            }

            return new Conversation(conversationName, (ArrayList)messages);
        }
        catch (FileNotFoundException e)
        {
            throw new FileNotFoundException("The file " + "\"" + inputFilePath + "\"" +
                                                " was not found: " + e.getMessage());
        }
        catch (IOException e)
        {
            throw new IOException("Something IO-related went wrong: " + e.getMessage());
        }
    }

    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
