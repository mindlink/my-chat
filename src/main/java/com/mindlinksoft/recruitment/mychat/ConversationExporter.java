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
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(ConversationExporterConfiguration configuration) throws Exception {
        Conversation conversation = this.readConversation(configuration);

        this.writeConversation(conversation, configuration.outputFilePath);

        // TODO: Add more logging...
        System.out.println("Conversation exported from '" + configuration.inputFilePath + "' to '" + configuration.outputFilePath);
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws Exception {
        // TODO: Do we need both to be resources, or will buffered writer close the stream?
        try (OutputStream os = new FileOutputStream(outputFilePath, true);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

            // TODO: Maybe reuse this? Make it more testable...
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

            Gson g = gsonBuilder.setPrettyPrinting().create();

            bw.write(g.toJson(conversation));
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
    public Conversation readConversation(ConversationExporterConfiguration configuration) throws Exception {
		Map<String, Integer> messagecount = new HashMap<String, Integer>();
        try(InputStream is = new FileInputStream(configuration.inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;
			//add stuff for if message dont have all things
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
					
					//Check if report should be generated
					if(configuration.report){
						if(messagecount.containsKey(senderId)){
							messagecount.put(senderId, messagecount.get(senderId) + 1);
						}else{
							messagecount.put(senderId, 1);
						}
					}
					
					//Check if messages needs to be filtered by user
					if(configuration.filter_user != null){
						if(!senderId.toLowerCase().equals(configuration.filter_user.toLowerCase())){
							continue;
						}
					}
					
					//Check if messages needs to be filtered by word
					if(configuration.filter_word != null){
						if(!split.stream().anyMatch(configuration.filter_word::equalsIgnoreCase)){
							continue;
						}
					}
					
					//Check if messages needs to be removed of blacklisted words
					if(configuration.blacklist != null){
						split = blacklistMessages(split, configuration.blacklist);
					}
					
					
					String content = "";
					for(String s : split){
						content += s + " ";
					}
					
					messages.add(new Message(timestamp, senderId, content));
				}
            }
			if(configuration.report){
				return new Conversation(conversationName, messages, getReports(messagecount));
			}else{
				return new Conversation(conversationName, messages);
			}
            
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            throw new Exception("Something went wrong");
        }
    }
	
	public List<UserReport> getReports(Map<String, Integer> messagecount) throws Exception {
		List<UserReport> reports = new ArrayList<UserReport>();
		for(Map.Entry e : messagecount.entrySet()){
			reports.add(new UserReport(e.getKey().toString(), (int)e.getValue()));
		}
		Collections.sort(reports);
		return reports;
	}
	
	public List<String> blacklistMessages(List<String> message, String[] blacklist) throws Exception {
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
