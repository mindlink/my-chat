package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {
	/** an arraylist is created to store the messages in a conversation */
    List<Message> messages = new ArrayList<Message>(); 
    /**
     * The application entry point.
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     * Command line arguments are given a fixed size so the input and output file paths can be stored within them and passed through
     * The command line arguments are parsed through a new instance of the ConversationExporterConfiguration and CommandLineArgumentParser classes
     * Instances of classes are created to avoid statically calling variables or methods which are not consistent for example, the messages in a conversation
     */
    public static void main(String[] args) throws Exception {
    	ConversationExporter exporter = new ConversationExporter();
    	args = new String[2];
    	args[0] = ConversationExporterConfiguration.GetInputPath();
    	args[1] = ConversationExporterConfiguration.GetOutputPath();
    	
    	if (args.length > 0)
    	{
    		ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args); 
    		exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath);
    	}
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when the input file is empty
     */
    public void exportConversation(String inputFilePath, String outputFilePath) throws Exception {
    	Conversation conversation = this.readConversation(inputFilePath);
    	if (conversation.messages.size() == 0)
        {
        	  throw new Exception("oops the input file is empty");
        }
        this.writeConversation(conversation, outputFilePath); 
        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
        ReadyToQuit();
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when the output file cannot be found
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws Exception { 
    	 HidePhoneAndCreditCardDetails PandC = new HidePhoneAndCreditCardDetails();
    	 ConversationBlackList CB = new ConversationBlackList();
    	 ConversationFilters CF = new ConversationFilters();
        try (OutputStream os = new FileOutputStream(outputFilePath, true);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

        	Boolean askName = ConversationFilters.AskNameFilter();
            Boolean askKeyWord = ConversationFilters.AskKeywordFilter();
            Boolean askBlackList = ConversationBlackList.AskBlackList();
     
            GsonBuilder gsonBuilder = new GsonBuilder(); // used to convert java to json or vice versa
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
            Gson g = gsonBuilder.create();        
            bw.write(g.toJson(conversation.name));
            bw.newLine();  
            PandC.CheckPhoneDetails(conversation, messages);
            PandC.CheckCreditCardDetails(conversation, messages);
            
      
            
            if (askBlackList == true)
            {
        	   CB.CheckBlacklist(conversation, messages);
            }
           if  (askName == true && askKeyWord == false)
           {
        	   String NameToFilter =  ConversationFilters.NameFilter();
        	   		for (int i=0; i<conversation.messages.size();i++) {
        	   			if (messages.get(i).senderId.toLowerCase().equals(NameToFilter.toLowerCase()))
        	   			{
        	   				bw.write(g.toJson(messages.get(i).StringTS + " " + messages.get(i).senderId + ": " + messages.get(i).content));
        	   				bw.newLine(); 
        	   			}
        	   		}
          }
          else if (askKeyWord == true && askName == false)
          {
        	  String Keyword =  ConversationFilters.KeyWordFilter();
        	  	for (int i=0; i<conversation.messages.size();i++) {
        	  		if (messages.get(i).content.toLowerCase().contains(Keyword.toLowerCase()))
        	  		{
        	  			bw.write(g.toJson(messages.get(i).StringTS + " " + messages.get(i).senderId + ": " + messages.get(i).content));
        	  			bw.newLine(); 
            	  }
        	  }
          }
          else if ((askKeyWord == true && askName == true))
          {
        	  String NameToFilter =  ConversationFilters.NameFilter();
        	  String Keyword =  ConversationFilters.KeyWordFilter();
        	  	for (int i=0; i<conversation.messages.size();i++) {
        	  		if (messages.get(i).senderId.toLowerCase().equals(NameToFilter.toLowerCase()) && messages.get(i).content.toLowerCase().contains(Keyword.toLowerCase()))
        	  		{
        	  			bw.write(g.toJson(messages.get(i).StringTS + " " + messages.get(i).senderId + ": " + messages.get(i).content));
        	  			bw.newLine(); 
        	  		}
        	  	}  
          }
          else 
          {
        	  for (int i=0; i<conversation.messages.size();i++) {
        		  bw.write(g.toJson(messages.get(i).StringTS + " " + messages.get(i).senderId + ": " + messages.get(i).content));
        		  bw.newLine(); 
        	  }
          }           
        } catch (FileNotFoundException e) {
        	 System.out.println("The output file was not found, please ensure you entered the file path correctly");
        	 System.out.println("The program will restart...");
        	 main(null);
        }
    }

    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when the input file cannot be found,
     */
    
    public Conversation readConversation(String inputFilePath) throws Exception { 
    	  String conversationName = "";
    	try(InputStream is = new FileInputStream(inputFilePath); 
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {  
            List<String> testing = new ArrayList<String>();
             conversationName = r.readLine();
            String line;
            String AttainWholeMessage = "";
            	while ((line = r.readLine()) != null) { 
            		String[] split = line.split(" "); 
            			for (int i = 2; i<split.length;i++) {
            				AttainWholeMessage += split[i] + " ";
            			}
            		int n = messages.size();    
            		String temp = AttainWholeMessage;
            		AttainWholeMessage = "";
            		messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], temp));
            	}
            } catch (FileNotFoundException e) {
            System.out.println("The input file was not found, please ensure you entered the file path correctly");
            System.out.println("The program will restart...");
            main(null);
            } catch (NumberFormatException e) {
            throw new IllegalArgumentException("The input file does not match the expected format of <timestamp><space><userID><space><message>");
            }
    	   return new Conversation(conversationName, messages);
    }
    
    public void ReadyToQuit() throws Exception {
    	Scanner in = new Scanner(System.in);
	    System.out.println("Would you like to export another conversation?");
	    String decision = in.nextLine();
	    if (decision.toLowerCase().equals("yes"))
	    {
	    	main(null);
	    }
	    else if (decision.toLowerCase().equals("no"))
	    {
	    	 System.out.println("Have a good day!");
	    	System.exit(0);
	    }
	    else
	    {
	    	ReadyToQuit();
	    }
    }
    	
    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
