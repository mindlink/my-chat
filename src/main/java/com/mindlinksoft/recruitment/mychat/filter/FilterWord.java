package com.mindlinksoft.recruitment.mychat.filter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mindlinksoft.recruitment.mychat.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.Message;

/**
 * This class ensures that the output only contains specified keywords
 * 
 * @author Mohamed Yusuf
 *
 */
public class FilterWord implements Filter {
	
	private static final Logger LOGGER = Logger.getLogger(FilterWord.class.getName());
	
	/**
	 * Overrides the filter method and defines custom filter behaviour.
	 * 
	 * In this cases, only adds messages with specific keyword in content.
	 * @param toFilter messages to filter.
	 * @param filter keywords to filter by.
	 */
	@Override
	public Set<Message> filter(Set<Message> toFilter, ConversationExporterConfiguration config) {
		String[] filters = config.getWordsToFilter();
		LOGGER.log(Level.INFO, "Filter by words: " + Arrays.toString(filters));
		Set<String> filterSet = Set.of(filters);
		Set<Message> messages = new HashSet<Message>();
		
		for(Message mess : toFilter) {
			String[] content = mess.getContent().split(" ");
			for(String word : content) {
				if(filterSet.contains(word)) {
					//System.out.println(mess.getContent());
					messages.add(mess);
				}
			}
		}	
		return messages;
	}
}
