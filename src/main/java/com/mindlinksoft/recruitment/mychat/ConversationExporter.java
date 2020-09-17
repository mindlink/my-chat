package com.mindlinksoft.recruitment.mychat;

import java.util.Set;
import java.util.TreeSet;
import com.mindlinksoft.recruitment.mychat.filter.BlackList;
import com.mindlinksoft.recruitment.mychat.filter.FilterUser;
import com.mindlinksoft.recruitment.mychat.filter.FilterWord;
import com.mindlinksoft.recruitment.mychat.filter.ObfuscateCreditCard;
import com.mindlinksoft.recruitment.mychat.filter.ObfuscateUser;
import com.mindlinksoft.recruitment.mychat.io.Reader;
import com.mindlinksoft.recruitment.mychat.io.Writer;
import com.mindlinksoft.recruitment.mychat.reports.Report;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 * 
 * @author Mohamed Yusuf
 */
public class ConversationExporter {
    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(ConversationExporterConfiguration config, Reader reader, Writer writer) throws Exception {
        Conversation conversation = reader.readConversation(config);
        Conversation filteredConvo;
        
        TreeSet<Message> messages = new TreeSet<Message>();
        
        /**
         * Filter by user first then word, and retaining only what is common in both,
         * the intersection of the two sets.
         * 
         * Other wise filter only words or users
         */
        if(config.isFILTER_USER() && config.isFILTER_WORD()) {
        	messages.addAll(new FilterUser().filter(conversation.getMessages(), config.getUsersToFilter()));
        	messages.retainAll(new FilterWord().filter(messages, config.getWordsToFilter()));
        } else if(config.isFILTER_USER()) {
        	messages.addAll(new FilterUser().filter(conversation.getMessages(), config.getUsersToFilter()));
        } else if(config.isFILTER_WORD()) {
        	messages.addAll(new FilterWord().filter(conversation.getMessages(), config.getWordsToFilter()));
        } else {
        	messages.addAll(conversation.getMessages());
        }
        
        /**
         * Additional transformations, will only uses the pre-filtered messages
         */
        if(config.isOBFS_USER())
        	messages.addAll(new ObfuscateUser().filter(messages, config.getUsersToObfuscate()));
        if(config.isBLACKLIST_WORD())
        	messages.addAll(new BlackList().filter(messages, config.getWordsToBlacklist()));       
        if(config.isOBFS_CREDIT_CARD())
        	messages.addAll(new ObfuscateCreditCard().filter(messages, config));
        
        Set<User> report = new Report().generateReport(messages);
        
        
        if(config.isGEN_REPORT())
        	filteredConvo = new Conversation(conversation.getName(), messages, report);  
        else
        	filteredConvo = new Conversation(conversation.getName(), messages);  
 	
		writer.writeConversation(filteredConvo, config);
    	
        System.out.println("Conversation exported from '" + config.getInputFilePath() + "' to '" + config.getOutputFilePath());
    } 
}
