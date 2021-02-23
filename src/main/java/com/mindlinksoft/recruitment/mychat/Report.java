package com.mindlinksoft.recruitment.mychat;

/**
 * 
 * Represents a report for a user
 *
 */
public class Report {
	
	/**
	 *  The sender
	 */
	public String sender;
	
	/**
	 * The number of messages sent
	 */
	public int count;
	
	/**
	 * Initialises a new instance of the {@link Report} class.
	 * @param sender The username of the sender
	 * @param count The number of messages sent by the user
	 */
	public Report(String sender, int count){
		this.sender = sender;
		this.count = count;
	}
}
