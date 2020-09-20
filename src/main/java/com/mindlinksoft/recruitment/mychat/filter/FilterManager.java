package com.mindlinksoft.recruitment.mychat.filter;

import java.util.TreeSet;
import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.Message;
/**
 * This class manages the application of filters
 * 
 * @author Mohamed Yusuf
 *
 */
public class FilterManager {
	/**
	 * This method uses the configuration data to determine and apply the relevant filters
	 * to a provided conversation. It then returns a filtered conversation.
	 * @param conversation the conversation to filter
	 * @param config configuration data to user
	 * @return filtered conversation
	 */
	public static TreeSet<Message> applyFilters(Conversation conversation, ConversationExporterConfiguration config) {
        TreeSet<Message> messages = new TreeSet<Message>();
        
        /**
         * Filter by user first then word, and retaining only what is common in both,
         * the intersection of the two sets.
         * 
         * Other wise filter only words or users
         */
        if(config.isFILTER_USER() && config.isFILTER_WORD()) {
        	messages.addAll(FilterFactory.getFilter(FilterType.FILTERUSER).filter(conversation.getMessages(), config));
        	messages.retainAll(FilterFactory.getFilter(FilterType.FILTERWORD).filter(messages, config));
        } else if(config.isFILTER_USER()) {	
        	messages.addAll(FilterFactory.getFilter(FilterType.FILTERUSER).filter(conversation.getMessages(), config));
        } else if(config.isFILTER_WORD()) {
        	messages.addAll(FilterFactory.getFilter(FilterType.FILTERWORD).filter(conversation.getMessages(), config));
        } else {
        	messages.addAll(conversation.getMessages());
        }
        
        /**
         * Additional transformations, will only uses the pre-filtered messages
         */
        if(config.isOBFS_USER())
        	messages.addAll(FilterFactory.getFilter(FilterType.OBFSUSER).filter(messages, config));
        if(config.isBLACKLIST_WORD())
        	messages.addAll(FilterFactory.getFilter(FilterType.BLACKLIST).filter(messages, config));       
        if(config.isOBFS_CREDIT_CARD())
        	messages.addAll(FilterFactory.getFilter(FilterType.OBFSCREDIT).filter(messages, config));
		
		return messages;
	}
}
