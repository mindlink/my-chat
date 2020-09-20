package com.mindlinksoft.recruitment.mychat.filter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.mindlinksoft.recruitment.mychat.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.Message;

/**
 * Ensures blacklisted keywords are redacted in the output
 * 
 * @author Mohamed Yusuf
 *
 */
public class BlackList implements Filter {
	
	/**
	 * Overrides the filter method and defines custom filter behaviour.
	 * @param toFilter messages to filter.
	 * @param filter keywords to filter by.
	 */
	@Override
	public Set<Message> filter(Set<Message> toFilter, ConversationExporterConfiguration config) {
		String[] filters = config.getWordsToBlacklist();
		System.out.println("Blacklist words: " + Arrays.toString(filters));
		Set<String> filterSet = Set.of(filters);
		Set<Message> messages = new HashSet<Message>();
		
		for(Message mess : toFilter) {
			String[] content = mess.getContent().split(" ");
			for(String word : content) {
				if(filterSet.contains(word)) {
					mess.setContent(mess.getContent().replaceAll(word, config.getREDACT_REPLACMENT()));
					messages.add(mess);
				}
			}
			//messages.add(mess);
		}
		return messages;
	}
}
