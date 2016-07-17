package com.mindlinksoft.recruitment.mychat.bean;

import java.util.List;

/**
 * Holds the most active users.
 */
public class Report {

	private final List<UserActivity> topUsers_;

	public Report(List<UserActivity> topUsers) {
		topUsers_ = topUsers;
	}

	public List<UserActivity> getTopUsers() {
		return topUsers_;
	}
}
