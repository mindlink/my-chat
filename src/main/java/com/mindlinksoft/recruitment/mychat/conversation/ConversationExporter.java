package com.mindlinksoft.recruitment.mychat.conversation;

import com.google.gson.*;
import com.mindlinksoft.recruitment.mychat.filters.FiltersFactory;
import com.mindlinksoft.recruitment.mychat.filters.IFilter;
import com.mindlinksoft.recruitment.mychat.formatters.FormattersFactory;
import com.mindlinksoft.recruitment.mychat.formatters.IFormatter;
import com.mindlinksoft.recruitment.mychat.json.InstantSerializer;
import com.mindlinksoft.recruitment.mychat.json.JSONSerializer;
import com.mindlinksoft.recruitment.mychat.messages.Message;
import com.mindlinksoft.recruitment.mychat.messages.MessageParser;
import com.mindlinksoft.recruitment.mychat.reports.IReport;
import com.mindlinksoft.recruitment.mychat.reports.UserActivityReport;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {



    /**
     * Exports the conversation based on configurations {@code configuration} as JSON .
     * @param configuration Conversation Exporter Configuration.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(ConversationExporterConfiguration configuration) throws Exception {
	
        System.out.println("Conversation Exporter Started...");        
        Conversation conversation = readAndFilterConversation(configuration);
        
        System.out.println("Generating Reports...");  
        generateReports(conversation);
        
        System.out.println("Converting conversation to JSON ...");  
        String conversationJSON = new JSONSerializer().toJSON(conversation);

        
        System.out.println("Writing Conversation JSON to file:"+configuration.getOutputFilePath());  
        writeConversation(conversationJSON, configuration.getOutputFilePath());


        System.out.println("Conversation Exporter Completed..."); 
        System.out.println('\u263A');

    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */
	public void writeConversation(String conversationJSON, String outputFilePath) throws Exception {

		try (BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(outputFilePath, true)))) {

			bw.write(conversationJSON);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("The file was not found at:" + outputFilePath);
		} catch (IOException e) {
			throw new RuntimeException("Could not write to file:" + outputFilePath + ",Exception:" + e);
		}
	}

    /**
     * Reads a conversation from the input file and applies the filters and formatters
     * as specified in {@code configuration}.
     * 
     * @param configuration Containing the input file path and the filters and formatters config.
     * @return The {@link Conversation} created.
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation readAndFilterConversation(ConversationExporterConfiguration configuration) throws Exception {
    	

    	ArrayList<IFilter> filters = FiltersFactory.getFilters(configuration);
    	ArrayList<IFormatter> formatters = FormattersFactory.getFormatters(configuration);
    	
        System.out.println("Reading from file:"+configuration.getInputFilePath());
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(configuration.getInputFilePath())))) {

            List<Message> messages = new ArrayList<Message>();

            String conversationName = reader.readLine();
            String line;
            MessageParser messageParser = new MessageParser();
            
            System.out.println("Applying formatters & filters...");
            while ((line = reader.readLine()) != null) {
            	Message message = messageParser.parse(line);
            	Boolean messageValid = new Boolean(true);

            	for(IFilter filter:filters)
            	{

            		if(!filter.apply(message))
            		{
            			messageValid=false;
            			break;
            		}
            	}
            	if(messageValid)
            	{
                	for(IFormatter formatter:formatters)
                	{
                		formatter.apply(message);
                	}
            		messages.add(message);
            	}
            	
            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found at:"+configuration.getInputFilePath());
        } catch (IOException e) {
            throw new RuntimeException("Could not read from file:"+configuration.getInputFilePath()+",Exception:"+e);
        } 
    }

    /**
     * Generates conversation reports
     * @param conversation
     */
	private void generateReports(Conversation conversation) {

		new UserActivityReport().generateReport(conversation);

	}


}
