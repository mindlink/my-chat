package com.mindlinksoft.recruitment.mychat.filter;

import java.util.HashSet;
import java.util.Set;

import com.mindlinksoft.recruitment.mychat.Message;

public class ObfuscateUser implements Filter {
	
	/**
	 * Overrides the filter method and defines custom filter behaviour.
	 * 
	 * In this case, the filter calls the obfuscate method on users,
	 * thus ensuring user names are obfuscated.
	 * @param toFilter messages to filter.
	 * @param filter users to filter by.
	 */
	@Override
	public Set<Message> filter(Set<Message> toFilter, String[] filters) {
		System.out.println("Obfuscate Users");
		Set<Message> messages = new HashSet<Message>();
		
		for(Message mess : toFilter) {
			mess.obfuscateUsername();
			messages.add(mess);
		}	
		return messages;
	}
}
