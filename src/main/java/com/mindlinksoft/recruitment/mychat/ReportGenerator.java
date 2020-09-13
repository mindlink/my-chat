package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ReportGenerator {

	public HashMap<String, UserCount> userCount = new HashMap<String, UserCount>();
	
	public void addUser(String name) {
		if (userCount.containsKey(name.toLowerCase())) {
			userCount.get(name.toLowerCase()).occurrence++;
		} else {
			userCount.put(name.toLowerCase(), new UserCount(name.toLowerCase()));
		}
	}
	
	public List<UserCount> sortValues() {
		List<UserCount> usersByOccurance = new ArrayList<>(userCount.values());
		
		Collections.sort(usersByOccurance, Collections.reverseOrder());
		System.out.println(usersByOccurance);
		
		return usersByOccurance;
	}
}

