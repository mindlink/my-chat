package com.mindlinksoft.recruitment.mychat;

public class FilterByUser extends Functionality{
	
	private String userToFilter;
	
	public FilterByUser() {
		super(true, 1, 1);
		message = "";
	}

	// Process all the parameters of the command
	// Structure: <command>=<param1>,<param2>,etc
	// Only supports one parameter for now but can be  implemented
	public void processParameters(String parameters) {
		if(parameters.contains(",")) {
			String[] elements = parameters.split(",");
			for(int i = 0; i < this.supportedParameters; i++) {
				this.userToFilter = elements[i];
			}
		}
		else {
			this.userToFilter = parameters;
		}

		
	}
	
	//Checks the username to filter is the username who sent the message
	public Boolean applyFunctionality(String line) {
		if(this.userToFilter.equalsIgnoreCase(line)){
			return true;
		}
		return false; 
	}
	
	public String getUserToFilter() {
		return this.userToFilter;
	}

}
