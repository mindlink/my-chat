package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Concrete filter adding a user activity report at the end of the chat export.*/
public class FilterReport implements ConversationFilter {

	/*** Only the *top* NUM_USERS users are recorded in the report.*/
	private final int NUM_USERS = 5;
	
	private Set<String> userSet;
	private List<ReportEntry> report;
	
	FilterReport() {
		this.report = new ArrayList<ReportEntry>(NUM_USERS);
	}
	
	@Override
	public void apply(Conversation conversation) {

		userSet = UserSetPopulator.populateUserSet(conversation);
		generateReportEntries(conversation);
		sort(report);
		
		//truncate to first NUM_USERS if more are present:
		report = truncate(report);
		//set associated conversation instance formatted as array:
		conversation.setReport(report.toArray(new ReportEntry[report.size()]));
	}
	
	/**Populates this instance's list of report entries based on the content of
	 * the conversation and the users stored in the user set*/
	private void generateReportEntries(Conversation conversation) {
		for(String currentUsername : userSet) {
			int accumulator = 0;
			
			for(Message message : conversation.messages) {
				if(currentUsername.compareTo(message.getSenderId()) == 0)
					++accumulator;
			}
			
			report.add(new ReportEntry(currentUsername, accumulator));
		}
	}
	
	/**
	 * Applies sorting logic to the generated report of entries
	 * @param report the report to sort*/
	private void sort(List<ReportEntry> report) {
		Collections.sort(report);
	}
	
	/**
	 * Truncates the list of report entries keeping only the first NUM_USERS 
	 * entries.*/
	private List<ReportEntry> truncate(List<ReportEntry> report) {
		return report.subList(0, (report.size() > NUM_USERS ? NUM_USERS : report.size()));
	}

}
