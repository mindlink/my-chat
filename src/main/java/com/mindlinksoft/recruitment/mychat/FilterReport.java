package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class FilterReport implements ConversationFilter {

	/*** Only the *top* NUM_USERS users are recorded in the report.*/
	private final int NUM_USERS = 5;
	
	private Set<String> userSet;
	private List<ReportEntry> report;
	
	FilterReport() {
		this.userSet = new TreeSet<String>();
		this.report = new ArrayList<ReportEntry>(NUM_USERS);
	}
	
	@Override
	public void apply(Conversation conversation) {

		populateUserSet(conversation);
		generateReportEntries(conversation);
		Collections.sort(report);
		//truncate to first five if more are present:
		report = report.subList(0, (report.size() > NUM_USERS ? NUM_USERS : report.size()));
		conversation.report = report.toArray(new ReportEntry[report.size()]);
	}
	
	/**Populates this instance's set of users based on the content of the 
	 * conversation*/
	private void populateUserSet(Conversation conversation) {
		for(Message message : conversation.messages) {
			userSet.add(message.senderId);
		}
	}
	
	/**Populates this instance's list of report entries based on the content of
	 * the conversation and the users stored in the user set*/
	private void generateReportEntries(Conversation conversation) {
		for(String currentUsername : userSet) {
			int accumulator = 0;
			
			for(Message message : conversation.messages) {
				if(currentUsername.compareTo(message.senderId) == 0)
					++accumulator;
			}
			
			report.add(new ReportEntry(currentUsername, accumulator));
		}
	}

}
