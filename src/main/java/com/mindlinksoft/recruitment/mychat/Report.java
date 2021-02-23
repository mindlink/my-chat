package com.mindlinksoft.recruitment.mychat;

/**
 * 
 * Represents a report for a user
 * Implements Java.lang.Comparable to compare Reports using count to allow for sorting
 *
 */
public class Report implements Comparable<Report>{
	
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
	
	
	/**
	 * Compare {@link Report} based on count.
	 * @param otherReport The Report to be compared to
	 * @return A negative integer, zero, or a positive integer if this report is less than, equal, or greater than the given {@code otherReport}
	 */
	public int compareTo(Report otherReport) {
		return this.count - otherReport.count;
	}
}
