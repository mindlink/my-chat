package com.mindlinksoft.recruitment.mychat;

public class ReportEntry implements Comparable<ReportEntry>{
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
