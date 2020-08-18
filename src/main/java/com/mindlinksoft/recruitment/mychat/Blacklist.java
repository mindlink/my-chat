package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;

public class Blacklist extends Functionality{

	private List<String> wordsToBan;
	
	
	public Blacklist() {
		super(true, 5);
		this.wordsToBan = new ArrayList<String>();
	}
	
	// Process all the parameters of the command
	// Structure: <command>=<param1>,<param2>,etc
	public void processParameters(String parameters) {
		
		if(parameters.contains(",")) {
			
			String[] elements = parameters.split(",");
			for(int i = 0; i < elements.length; i++) {
				this.wordsToBan.add(elements[i]);
			}
		}
		else {
			this.wordsToBan.add(parameters);
		}
		
		return;
	}

	public Boolean applyFunctionality(ParsedLine parsedLine) {
		
		Boolean apply = true;
		String messageToBlacklist = parsedLine.getMessage();
		for(String word : this.wordsToBan){
			//Finds the starting index of the words to ban
            int index = messageToBlacklist.indexOf(word);
            //If the word is in the message
            while (index >= 0) {
            		//Apply *redacted* on the words and create a new string composed by the old message plus *redacted* on banned words
                String chatFieldBanned = messageToBlacklist.substring(0, index-1) + " *redacted*" + messageToBlacklist.substring(word.length()+index, messageToBlacklist.length());
                // Search for words to ban after finding the previous ones
                index = chatFieldBanned.indexOf(word);
                messageToBlacklist = chatFieldBanned;
                apply = true;
            }
		}
		//The message is now filtered by keyword and assigned to the parsedLine object
		parsedLine.setMessage(messageToBlacklist);
		return apply;
	}
	
	public List<String> getWordsToBan(){
		return this.wordsToBan;
	}

}
