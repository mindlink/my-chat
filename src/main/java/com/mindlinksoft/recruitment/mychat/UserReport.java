package com.mindlinksoft.recruitment.mychat;

public class UserReport{

	String userID;
	int counter;
	
	public UserReport(String userID, int counter){
		this.userID=userID;
		this.counter=counter;
	}
	
	public int getCounter(){
		return counter;
	}

	 
}
