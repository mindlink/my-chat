package com.mindlinksoft.recruitment.mychat;

public abstract class Functionality {
	//Gets if commands needs parameters
	protected Boolean requireParameters;
	//Gets the number of supported parameters
	protected int supportedParameters;
	//Gets the processed message after applying command
	protected String message;
	//Gets the index of part of message needed e.g 0-timestamp, 1-userid, 2-message
	protected int requiredChatField;
	
	public Functionality(Boolean requireParameters, int supportedParameters, int requiredChatField) {
		this.requireParameters = requireParameters;
		this.supportedParameters = supportedParameters;
		this.requiredChatField = requiredChatField;
		
	}
	public abstract void processParameters(String parameters);
	public abstract Boolean applyFunctionality(String chatField);
	
	public Boolean getRequireParameters() {
		return this.requireParameters;
	}
	
	public int getRequiredChatField() {
		return this.requiredChatField;
	}
	
	public String getMessage() {
		return this.message;
	}
}
