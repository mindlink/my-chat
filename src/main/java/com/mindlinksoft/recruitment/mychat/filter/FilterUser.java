package com.mindlinksoft.recruitment.mychat.filter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mindlinksoft.recruitment.mychat.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.Message;

/**
 * This class ensures that the output only contains specified users.
 * 
 * @author Mohamed Yusuf
 *
 */
public class FilterUser implements Filter {
	
	private static final Logger LOGGER = Logger.getLogger(FilterUser.class.getName());
	
	/**
	 * Overrides the filter method and defines custom filter behaviour.
	 * 
	 * In this case only adds messages from specific users.
	 * @param toFilter messages to filter.
	 * @param filter users to filter by.
	 */
	@Override
	public Set<Message> filter(Set<Message> toFilter, ConversationExporterConfiguration config) {
		String[] filters = config.getUsersToFilter();
		LOGGER.log(Level.INFO, "Filter by users: " + Arrays.toString(filters));
		Set<String> filterSet = Set.of(filters);
		Set<Message> messages = new HashSet<Message>();
	
		for(Message mess : toFilter) {
			if(filterSet.contains(mess.getUsername())) {
				//System.out.println(mess.getUsername());
				messages.add(mess);
			}
		}
		return messages;
	}
}
