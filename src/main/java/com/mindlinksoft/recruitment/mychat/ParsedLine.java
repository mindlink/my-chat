package com.mindlinksoft.recruitment.mychat;

public class ParsedLine {
	private String timestamp;
	private String username;
	private String message;
	
	public ParsedLine(String timestamp, String username, String message) {
		this.timestamp = timestamp;
		this.username = username;
		this.message = message;
	}
	
	public String getTimestamp() {
		return this.timestamp;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void setMessage(String message) {
		this.message = message;
		return;
	}
	
}
