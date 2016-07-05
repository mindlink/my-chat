package com.mindlinksoft.recruitment.mychat;

/**
 * Represents a report entry as a key-value pair. Implements {@link Comparable}
 * in order to help with sorting report entries based on their score/number of
 * message sent by the user.*/
class ReportEntry implements Comparable<ReportEntry>{
	String username;
	int score;
	
	ReportEntry(String username, int score) {
		this.username = username;
		this.score = score;
	}

	@Override
	public int compareTo(ReportEntry o) {

		return Integer.compare(o.score, this.score);
	}
	
	
}
