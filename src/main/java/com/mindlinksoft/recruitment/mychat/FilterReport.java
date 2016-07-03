package com.mindlinksoft.recruitment.mychat;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import javax.print.attribute.standard.NumberUp;

public class FilterReport implements ConversationFilter {

	private Set<String> userSet;
//	private Map<String, Integer> scoresMap;//mapsolution
//	private ReportEntry[] report;//arraysolution
	private List<ReportEntry> report;
	/**
	 * Only the *top* NUM_USERS users are recorded in the report.*/
	private final int NUM_USERS = 5;
	
	FilterReport() {
		this.userSet = new TreeSet<String>();
//		this.scoresMap = new HashMap<String, Integer>();//mapsolution
		this.report = new ArrayList<ReportEntry>(NUM_USERS);
	}
	
	@Override
	public void apply(Conversation conversation) {
//		ReportEntry[] report;//mapsolution
		
		for(Message message : conversation.messages) {
			userSet.add(message.senderId);
		}
		
//		int index = 0;//arraysolution
		for(String currentUsername : userSet) {
			int accumulator = 0;
			
			for(Message message : conversation.messages) {
				if(currentUsername.compareTo(message.senderId) == 0)
					++accumulator;
			}
			
			report.add(new ReportEntry(currentUsername, accumulator));
//			report[index++] = new ReportEntry(currentUsername, accumulator);//arraysolution
//			scoresMap.put(currentUsername, accumulator);//mapsolution
		}
		
		Collections.sort(report);
		conversation.report = report.toArray(new ReportEntry[NUM_USERS]);
//		scoresMap.entrySet();//mapsolution

	}

}
