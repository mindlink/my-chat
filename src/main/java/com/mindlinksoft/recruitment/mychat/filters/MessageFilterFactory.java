package com.mindlinksoft.recruitment.mychat.filters;

public class MessageFilterFactory {
	public final static String USER_FILTER = "USER";
	public final static String KEYWORD_FILTER = "KEYWORD";
	public final static String BLACKLIST_FILTER = "BLACKLIST";
	public final static String HIDE_DETAILS_FILTER = "DETAILS";
	public final static String OBFUSCATE_USERS_FILTER = "OBFUSCATE";
	
	public MessageFilter getFilter(String filterType) {
		switch (filterType) {
		case USER_FILTER:
			return new MessageFilterUser();
		case KEYWORD_FILTER:
			return new MessageFilterKeyword();
		case BLACKLIST_FILTER:
			return new MessageFilterBlacklist();
		case HIDE_DETAILS_FILTER:
			return new MessageFilterHideDetails();
		case OBFUSCATE_USERS_FILTER:
			return new MessageFilterObfuscateUsers();			
		default:
			return null;
		}
	}
	
}
