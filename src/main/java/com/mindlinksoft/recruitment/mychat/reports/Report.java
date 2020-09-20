package com.mindlinksoft.recruitment.mychat.reports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import com.mindlinksoft.recruitment.mychat.Message;
import com.mindlinksoft.recruitment.mychat.User;

/**
 * This class generates most active user reports.
 * 
 * @author Mohamed Yusuf
 *
 */
public class Report {
	
	/**
	 * This method makes use to the custom ordering provided by the,
	 * user class to calculate and return an ordered set of active
	 * users.
	 * 
	 * @param conv the conversation to generate report for.
	 * @return An ordered set of user activity
	 */
	public Set<User> generateReport(Set<Message> conv) {
		ArrayList<String> users = new ArrayList<String>();
		TreeSet<User> userScores = new TreeSet<User>();
		
		for(Message mess : conv) {
			users.add(mess.getUsername());
		}
		
		for(Message mess : conv) {
			String username = mess.getUsername();
			userScores.add(new User(username,  Collections.frequency(users, username)));
		}
		
		return userScores;
	}
}
