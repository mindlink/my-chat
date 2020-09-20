package com.mindlinksoft.recruitment.mychat;

import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mindlinksoft.recruitment.mychat.filter.FilterManager;
import com.mindlinksoft.recruitment.mychat.io.Reader;
import com.mindlinksoft.recruitment.mychat.io.Writer;
import com.mindlinksoft.recruitment.mychat.reports.Report;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 * 
 * @author Mohamed Yusuf
 */
public class ConversationExporter {
	
	private static final Logger LOGGER = Logger.getLogger(ConversationExporter.class.getName());
	
    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param config program config data.
     * @param reader Reader class to use.
     * @param writer Writer class to use.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(ConversationExporterConfiguration config, Reader reader, Writer writer) {
        Conversation conversation = reader.readConversation(config);
        Conversation filteredConvo;
        
        TreeSet<Message> messages = FilterManager.applyFilters(conversation, config);
        Set<User> report = new Report().generateReport(messages);    
        
        if(config.isGEN_REPORT())
        	filteredConvo = new Conversation(conversation.getName(), messages, report);  
        else
        	filteredConvo = new Conversation(conversation.getName(), messages);  
 	
		writer.writeConversation(filteredConvo, config);
    	
		LOGGER.log(Level.INFO, "Conversation exported from '" + config.getInputFilePath() + "' to '" + config.getOutputFilePath());
    } 
}
