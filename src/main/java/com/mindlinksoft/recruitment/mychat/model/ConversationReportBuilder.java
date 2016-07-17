package com.mindlinksoft.recruitment.mychat.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.mindlinksoft.recruitment.mychat.bean.Message;
import com.mindlinksoft.recruitment.mychat.bean.Report;
import com.mindlinksoft.recruitment.mychat.bean.UserActivity;

/**
 * Gathers the most active users in a conversation and creates a report object
 */
public class ConversationReportBuilder {

	private static final int TOP_USER_COUNT = 10;

	private Collection<Message> messages_;
	private Map<String, Integer> map_;
	private List<String> topUsers_;
	private List<UserActivity> userActivity_;

	public ConversationReportBuilder(Collection<Message> messages) {
		messages_ = messages;
	}

	public Report build() {
		map_ = new HashMap<>();
		countUserActivity();
		getMostActiveUsers();
		createUserActivityList();
		return new Report(userActivity_);
	}

	private void countUserActivity() {
		if (messages_ == null)
			return;
		messages_.stream().forEach(this::addToActivity);
	}

	private void getMostActiveUsers() {
		topUsers_ = map_.keySet() //
				.stream() //
				.sorted((a, b) -> map_.get(b).compareTo(map_.get(a))) //
				.limit(TOP_USER_COUNT) //
				.collect(Collectors.toList());
	}

	private void createUserActivityList() {
		userActivity_ = new ArrayList<>();
		topUsers_.forEach(this::addToUserActivity);
	}

	private void addToUserActivity(String user) {
		userActivity_.add(new UserActivity(user, map_.get(user)));
	}

	private void addToActivity(Message m) {
		if (!map_.containsKey(m.getSenderId()))
			map_.put(m.getSenderId(), 0);
		map_.put(m.getSenderId(), map_.get(m.getSenderId()) + 1);
	}
}
