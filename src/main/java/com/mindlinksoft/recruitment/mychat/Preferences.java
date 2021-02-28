package com.mindlinksoft.recruitment.mychat;

import java.util.List;

public final class Preferences {

	public final String userFilter;
	public final String keywordFilter;
	public final List<String> blacklist;
	public final Boolean report;
	
	
	public Preferences(String userFilter, String keywordFilter, List<String> blacklist, Boolean report){
		
		this.userFilter = userFilter;
		this.keywordFilter = keywordFilter;
		this.blacklist = blacklist;
		this.report = report;
	}
}
