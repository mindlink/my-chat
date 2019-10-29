package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Adds a report at the end of the conversation, including the list of users.
 * ordered by number of messages sent.
 */
public class ActivityReport {

	public String user;

	public int messageCount;

	/**
	 * Initialises a new instance of the {@link ActivityReport} class.
	 * 
	 * @param option The array containing the filter option and the senderId.
	 */
	public ActivityReport(String user, int messageCount) {
		this.user = user;
		this.messageCount = messageCount;
	}

	/**
	 * Recreates a Conversation object, adding a report to it.
	 * 
	 * @param convo Conversation object to which a report will be added.
	 * @return New Conversation object with added report.
	 */
	public static Conversation addReport(Conversation convo) {
		List<ActivityReport> report = createReport(convo);
		Conversation convoWithReport = new Conversation(convo.name, convo.messages, report);
		return convoWithReport;
	}

	/**
	 * Creates a list of reports. Each report consists of a user and the number of
	 * messages sent by that user.
	 * 
	 * @param convo Conversation object from which the report will be created.
	 * @return List of ActivityReport objects.
	 */
	private static List<ActivityReport> createReport(Conversation convo) {
		Map<String, Integer> countHash = new HashMap<>();
		List<ActivityReport> report = new ArrayList<ActivityReport>();

		// Add the senderId to the list and add a corresponding counter.
		for (Message m : convo.messages) {
			if (!countHash.containsKey(m.senderId)) {
				countHash.put(m.senderId, 1);
			} else {
				int oldVal = countHash.get(m.senderId);
				countHash.replace(m.senderId, oldVal + 1);
			}
		}
		
		List<String> users = new ArrayList<String>(countHash.keySet());
		List<Integer> count = new ArrayList<Integer>(countHash.values());
		
		// Get max number and user, remove them from lists after adding to report
		while (!count.isEmpty()) {
			int max = Collections.max(count);
			int maxIndex = count.indexOf(max);
			String maxUser = users.get(maxIndex);
			ActivityReport element = new ActivityReport(maxUser, max);
			report.add(element);

			count.remove(maxIndex);
			users.remove(maxIndex);
		}
		return report;
	}
}
