package com.mindlinksoft.recruitment.mychat;

public class UserCount implements Comparable<UserCount> {
	public String name;
	public int occurrence;
	
	public UserCount(String name) {
		this.name = name;
		this.occurrence = 1;
	}

	@Override
	public int compareTo(UserCount userCount) {
		// TODO Auto-generated method stub
		return (this.occurrence - userCount.occurrence);
	}
}