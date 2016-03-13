package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

/**
 * Report class to get reports
 * @author Jimmy
 *
 */
public class Report {

	/**
	 * Gets a list of map containing the usernames sorted by number of messages
	 * @param messages Collection of messages
	 * @return List of map
	 */
	public static List<TreeMap<String, Integer>> getByUsername(Collection<Message> messages) {
		List<TreeMap<String, Integer>> report = new ArrayList<>();
		TreeMap<String, Integer> reportValues = new TreeMap<>();
		for (Message message : messages) {
			String username = message.senderId;
			if (reportValues.containsKey(username)) {
				reportValues.put(username, reportValues.get(username) + 1);
			} else {
				reportValues.put(username, 1);
			}
		}
		report.add(sortMapByValue(reportValues)); 
		
		return report;
	}

	/**
	 * Orders the report by value
	 * @param map The unsorted map with report
	 * @return sorted map
	 */
	private static TreeMap<String, Integer> sortMapByValue(TreeMap<String, Integer> map){
		Comparator<String> comparator = new ValueComparator(map);
		TreeMap<String, Integer> result = new TreeMap<String, Integer>(comparator);
		result.putAll(map);
		return result;
	}
}
