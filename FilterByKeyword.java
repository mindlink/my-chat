package com.mindlinksoft.recruitment.mychat;

public class FilterByKeyword extends Functionality{
	
	
	private String keywordToFilter;

	
	public FilterByKeyword() {
		// Boolean requireParameters, int supportedParameters, int requiredChatField
		super(true, 1, 2);
	}
	
	// Process all the parameters of the command
	// Structure: <command>=<param1>,<param2>,etc
	// Only supports one parameter for now but can be  implemented
	
	public void processParameters(String parameters) {
		if(parameters.contains(",")) {
			String[] elements = parameters.split(",");
			for(int i = 0; i < this.supportedParameters; i++) {
				this.keywordToFilter = elements[0];
			}
		}
		else {
			this.keywordToFilter = parameters;
		}
	}

	
	//Returns true if the keyword is found in the message
	public Boolean applyFunctionality(String chatField) {
		String[] messageWords = chatField.split(" ");
		for(String word : messageWords) {
			if(word.toLowerCase().contains(this.keywordToFilter.toLowerCase())){
				this.message = chatField;
                return true;
            }
        }
		return false;
	}

	public String getKeywordToFilter() {
		return this.keywordToFilter;
	}
}
