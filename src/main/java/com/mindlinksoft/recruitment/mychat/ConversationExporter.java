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
		Utils ut = new Utils();
		OptionsHandler op = new OptionsHandler();
		try{	
		
			logger.info("Attempting to read from file");
			//Retrieve lines from file
			List<String> lines = ut.readChatFile(configuration.inputFilePath);
			
			logger.info("Reading conversation name from list created from file");
			//Get name of conversation from first line then remove it from list
			String conversationName = lines.get(0);
			lines.remove(0);
			
			logger.info("Generating list of messages from lines");
			//Get list of message objects from the lines of the file
			List<Message> messages = createMessageList(lines);
			
			logger.info("Applying optional filtering options");
			//Apply all options selected by user
			List<Message> messages_options = op.options(configuration, messages);
			
			//Assemble final Conversation object that will be converted to json. 
			Conversation c;
			if(configuration.report){
				logger.info("Creating final conversation with report");
				c = new Conversation(conversationName, messages, op.generateReports(messages_options));
			}else{
				logger.info("Creating final conversation without report");
				c = new Conversation(conversationName, messages_options);
			}
			
			//Call method to print conversation to file
			logger.info("Writing conversation to json file");
			ut.writeConversation(c, configuration.outputFilePath);

			logger.info("Conversation exported from '" + configuration.inputFilePath + "' to '" + configuration.outputFilePath);
		
		}catch(NullPointerException e){
			logger.error("Conversation could not be read", e);
		}

    }

	
	/**
     * Helper method to create a list of messages out of lines from file
     * @param lines Contains lines of the chat file
     * @return The list of messages representing by the input file.
     * @throws Exception error in iterating lines.
     */
    public List<Message> createMessageList(List<String> lines) throws Exception {
		List<Message> messages = new ArrayList<Message>();
			for(String line : lines){
				List<String> split = new ArrayList<String>(Arrays.asList(line.split(" ")));
				if(split.size() > 1){
					Instant timestamp = Instant.ofEpochSecond(Long.parseUnsignedLong(split.get(0)));
					String senderId = split.get(1);
					split.remove(0);
					split.remove(0);
					
					String content = "";
					
					for(String s : split){
						content += " " + s;
					}
					content = content.trim();
					
					messages.add(new Message(timestamp, senderId, content));
				}else{
					logger.info("Chat entry missing information, moving on to next message");
				}
			}
		return messages;
    }
}
