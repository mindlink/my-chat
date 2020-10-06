package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import picocli.CommandLine;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.ParseResult;
import picocli.CommandLine.UnmatchedArgumentException;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;
/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {
    private static Logger logger = Logger.getLogger(ConversationExporter.class);
	/**
     * The application entry point.
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception {
        // We use picocli to parse the command line - see https://picocli.info/
        ConversationExporterConfiguration configuration = new ConversationExporterConfiguration();
        CommandLine cmd = new CommandLine(configuration);

        try {
            ParseResult parseResult = cmd.parseArgs(args);
        
            if (parseResult.isUsageHelpRequested()) {
                cmd.usage(cmd.getOut());
                System.exit(cmd.getCommandSpec().exitCodeOnUsageHelp());
                return;
            }
            
            if (parseResult.isVersionHelpRequested()) {
                cmd.printVersionHelp(cmd.getOut());
                System.exit(cmd.getCommandSpec().exitCodeOnVersionHelp());
                return;
            }

            ConversationExporter exporter = new ConversationExporter();
			
            exporter.exportConversation(configuration);
            System.exit(cmd.getCommandSpec().exitCodeOnSuccess());
        } catch (ParameterException ex) {
            cmd.getErr().println(ex.getMessage());
            if (!UnmatchedArgumentException.printSuggestions(ex, cmd.getErr())) {
                ex.getCommandLine().usage(cmd.getErr());
            }

            System.exit(cmd.getCommandSpec().exitCodeOnInvalidInput());
        } catch (Exception ex) {
            ex.printStackTrace(cmd.getErr());
            System.exit(cmd.getCommandSpec().exitCodeOnExecutionException());
        }
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param configuration Contains all command line arguments
     * @throws Exception Thrown if reading or writing to file fails.
     */
    public void exportConversation(ConversationExporterConfiguration configuration) throws Exception {
		//Log options selected by user
		String options = getOptions(configuration);
		if(options.equals("")){
			options = "None";
		}
		logger.info("Exporting conversation with options: " + options);
		try{
        Conversation conversation = this.readConversation(configuration);
		this.writeConversation(conversation, configuration.outputFilePath);

        logger.info("Conversation exported from '" + configuration.inputFilePath + "' to '" + configuration.outputFilePath);
		}catch(NullPointerException e){
			logger.error("Conversation could not be read", e);
		}
    }
	
	/**
	* Helper method checking options for logging purposes
	* @param configuration Contains all command line arguments
	* @return String containing options set in command line
	*/
	public String getOptions(ConversationExporterConfiguration configuration){
		String options = "";
		if(configuration.filter_user != null){
			options += "User filter : " + configuration.filter_user + "\n"; 
		}
		if(configuration.filter_word != null){
			options += "Word filter : " + configuration.filter_word + "\n"; 
		}
		
		if(configuration.report){
			options += "Adding report\n"; 
		}
		
		if(configuration.blacklist != null){
			options += "Blacklisted words :";
			for(String w : configuration.blacklist){
				options += " " + w;
			}
			options += "\n";
		}
		return options;
	}
    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws Exception {
        try(Writer writer = new FileWriter(outputFilePath)){

            // TODO: Maybe reuse this? Make it more testable...
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

            Gson g = gsonBuilder.setPrettyPrinting().create();
			g.toJson(conversation, writer);
        } catch (FileNotFoundException e) {
			logger.error("The output file was not found, check the path.", e);
        } catch (IOException e) {
			logger.error("Error writing to file.", e);
        }
    }

    /**
     * Represents a helper to read a conversation from the given file.
     * @param ConversationExporterConfiguration Contains command line inputs.
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown file could not be read.
     */
    public Conversation readConversation(ConversationExporterConfiguration configuration) throws Exception {
		//Store number of messages sent for each user
		Map<String, Integer> messagecount = new HashMap<String, Integer>();
		logger.info("Reading conversation from " + configuration.inputFilePath);
        try(InputStream is = new FileInputStream(configuration.inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;
			
			
            while ((line = r.readLine()) != null) {
				List<String> split = new ArrayList<String>(Arrays.asList(line.split(" ")));
				if(split.size() > 1){
					Instant timestamp = Instant.ofEpochSecond(Long.parseUnsignedLong(split.get(0)));
					String senderId = split.get(1);
					split.remove(0);
					split.remove(0);
					
					/*
					Go through the options set by the user
					*/
					
					//Check if messages needs to be filtered by user
					if(configuration.filter_user != null){
						if(!senderId.toLowerCase().equals(configuration.filter_user.toLowerCase())){
							continue;
						}
					}
					
					//Check if messages needs to be filtered by word
					if(configuration.filter_word != null){
						boolean remove = true;
						for(String s : split){
							if(s.toLowerCase().contains(configuration.filter_word.toLowerCase())){
								remove = false;
							}
						}
						if(remove){
							continue;
						}
					}
					
					//Check if report should be generated and increment message count for user if yes
					if(configuration.report){
						if(messagecount.containsKey(senderId)){
							messagecount.put(senderId, messagecount.get(senderId) + 1);
						}else{
							messagecount.put(senderId, 1);
						}
					}
					
					//Check if messages needs to be removed of blacklisted words
					if(configuration.blacklist != null){
						split = blacklistMessages(split, configuration.blacklist);
					}
					
					
					String content = split.get(0);
					split.remove(0);
					for(String s : split){
						content += " " + s;
					}
					
					messages.add(new Message(timestamp, senderId, content));
				}else{
					logger.info("Chat entry missing information, moving on to next message");
				}
            }
			if(configuration.report){
				return new Conversation(conversationName, messages, getReports(messagecount));
			}else{
				return new Conversation(conversationName, messages);
			}
   
        } catch (FileNotFoundException e) {
			logger.error("The input file was not found, check the path.", e);
        } catch (IOException e) {
            logger.error("Error reading from file.", e);
			
        }
		return null;
    }
	/**
	* Creates a UserReport for each user and inserts the number of messages sent by that user
	* @param Map with user name as key and number of messages sent as value
	* @return List of UserReport which will add to conversation
	*/
	public List<UserReport> getReports(Map<String, Integer> messagecount){
		List<UserReport> reports = new ArrayList<UserReport>();
		for(Map.Entry e : messagecount.entrySet()){
			reports.add(new UserReport(e.getKey().toString(), (int)e.getValue()));
		}
		Collections.sort(reports);
		return reports;
	}
	
	/**
	* Creates a UserReport for each user and inserts the number of messages sent by that user
	* @param Map with user name as key and number of messages sent as value
	* @return List containing the message with blacklisted words redacted
	*/
	public List<String> blacklistMessages(List<String> message, String[] blacklist){
		List<String> blacklisted_message = new ArrayList<String>();
			for(String s : message){
				for(String word : blacklist){
					if(s.toLowerCase().contains(word.toLowerCase())){
						s = s.toLowerCase().replace(word.toLowerCase(), "*redacted*");
					}
				}
				blacklisted_message.add(s);
			}
		return blacklisted_message;
	}

    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
