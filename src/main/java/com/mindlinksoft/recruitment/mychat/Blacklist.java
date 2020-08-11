package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;

public class Blacklist extends Functionality{

	private List<String> wordsToBan;
	
	
	public Blacklist() {
		super(true, 5, 2);
		this.wordsToBan = new ArrayList<String>();
	}
	
	// Process all the parameters of the command
	// Structure: <command>=<param1>,<param2>,etc
	public void processParameters(String parameters) {
		if(parameters.contains(",")) {
			String[] elements = parameters.split(",");
			for(int i = 0; i < this.supportedParameters; i++) {
				this.wordsToBan.add(elements[i]);
			}
		}
		else {
	
			this.wordsToBan.add(parameters);;
		}
		return;
	}

	public Boolean applyFunctionality(String chatField) {
		Boolean apply = true;
		for(String word : this.wordsToBan){
			//Finds the starting index of the words to ban
            int index = chatField.indexOf(word);
            //If the word is in the message
            while (index >= 0) {
            		//Apply *redacted* on the words and create a new string composed by the old message plus *redacted* on banned words
                String chatFieldBanned = chatField.substring(0, index-1) + " *redacted*" + chatField.substring(word.length()+index, chatField.length());
                // Search for words to ban after finding the previous ones
                index = chatFieldBanned.indexOf(word);
                chatField = chatFieldBanned;
                apply = true;
            }
		}
		//The message is now filtered by keyword and assigned to variable message
		this.message = chatField;
		return apply;
	}

}
