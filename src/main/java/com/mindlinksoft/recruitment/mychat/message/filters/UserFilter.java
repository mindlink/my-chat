package main.java.com.mindlinksoft.recruitment.mychat.message.filters;

import main.java.com.mindlinksoft.recruitment.mychat.message.Message;

public class UserFilter implements IMessageFilter {
	private final String userFilter;
	
    public UserFilter(String userFilter) {
		if (userFilter == null || userFilter.equals("")) {
			throw new IllegalArgumentException("Cannot initialize a UserFilter with a null or empty userId");
		}
        this.userFilter = userFilter;
    }

	public boolean validate(Message candidate){
		if (candidate.getSenderId() == null) return false;
		
		return candidate.getSenderId().equals(userFilter);
	}
}