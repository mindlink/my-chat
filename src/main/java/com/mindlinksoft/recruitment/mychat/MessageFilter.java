package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;

public class MessageFilter {
	
	//filters the List of inputted messages and removes any that aren't from the inputted user
    public List<Message> filterMessagesByUser(List<Message> messages, String user) {
    	System.out.println("Start, num messages: " + messages.size());
    	System.out.println("Messages are being filtered for the user: " + user);
    	for(int i=0; i< messages.size(); i++) {
    		System.out.println("Message " + i + ": sent by " + messages.get(i).getSenderId());
    	}
    	for(int i=0; i< messages.size(); i++) {
    		System.out.println("Message " + i + ": sent by " + messages.get(i).getSenderId());
    		if(!messages.get(i).getSenderId().equals(user)) {
    			messages.remove(i);
    			System.out.println("Message " + i + " removed num messages now: " + messages.size());
    			i--;
    		}
    		else {
    			System.out.println("Message " + i + " not removed num messages now: " + messages.size());
    		}
    	}
    	return messages;
    }
    
    //creates and returns a new List of messages that contain the inputted keyword
    //NEEDS TO REMOVE !,+ ETC.
    public List<Message> filterMessagesByKeyword(List<Message> messages, String keyword) {
    	List<Message> newMessages = new ArrayList<Message>();
    	for(int i=0; i< messages.size(); i++) {
    		String message = messages.get(i).getContent();
    		
    		String[] messageWords = message.split(" ");
    		for(int j=0; j < messageWords.length; j++) {
    			if(keyword.equals(messageWords[j].toLowerCase().replaceAll("[^a-zA-Z]", ""))) {
    				newMessages.add(messages.get(i));
    				j = messageWords.length;
    			}
    		}
    	}
    	return newMessages;
    }
    
    //returns a list of messages with the blacklisted words censored in the content of each message
    public List<Message> censorChosenWords(List<Message> messages, String[] blacklist) {
    	for(int i=0; i< messages.size(); i++) {
    		String messageContent = messages.get(i).getContent();
    		
    		
    		System.out.println("The initial content " + i + ": " + messageContent);
    		
    		String[] messageWords = messageContent.split(" ");
    		for(int j=0; j < messageWords.length; j++) {
    			for(int k=0; k<blacklist.length; k++) {
    				if(blacklist[k].equals(messageWords[j].toLowerCase().replaceAll("[^a-zA-Z]", ""))) {
    					//THINK ABOUT HOW TO MAKE THIS BETTER, ADD BACK IN GRAMMAR!!
        				messageWords[j] = "*redacted*";
        			}
    			}
    		}
    		
    		String censoredMessageContent = "";
    		for(int j=0; j<messageWords.length; j++) {
    			if(j != messageWords.length - 1) {
    				censoredMessageContent += messageWords[j] + " ";
    			}
    			else {
    				censoredMessageContent += messageWords[j];
    			}
    		}

    		System.out.println("The end content: " + censoredMessageContent);
    		
    		Message updatedMessage = messages.get(i);
    		updatedMessage.setContent(censoredMessageContent);
    		
    		messages.set(i, updatedMessage);

    	}
    	
    	
    	return messages;
    }
    
    //returns a list of messages with the blacklisted words censored in the content of each message
    public List<Message> censorPhoneAndCardNumbers(List<Message> messages) {
    	System.out.println("censorPhoneAndCardNumbers method has been activated...");
    	for(int i=0; i< messages.size(); i++) {
    		String messageContent = messages.get(i).getContent();
    		
    		String[] messageWords = messageContent.split(" ");
    		for(int j=0; j < messageWords.length; j++) {
    			String currentWord = messageWords[j].replaceAll("[^0-9]", "");
    			if(currentWord.length() == 16 || currentWord.length() == 11) {
    				messageWords[j] = "*redacted*";
    			}
    			System.out.println(currentWord);
    		}
    		
    		String censoredMessageContent = "";
    		for(int j=0; j<messageWords.length; j++) {
    			if(j != messageWords.length - 1) {
    				censoredMessageContent += messageWords[j] + " ";
    			}
    			else {
    				censoredMessageContent += messageWords[j];
    			}
    		}
    		
    		Message updatedMessage = messages.get(i);
    		updatedMessage.setContent(censoredMessageContent);
    		
    		messages.set(i, updatedMessage);
    	}
    	
    	System.out.println("censorPhoneAndCardNumbers method has ended!!!");
    	
    	return messages;
    	
    }
    
    public List<Message> obfuscateUserIds(List<Message> messages) {
    	System.out.println("obfuscateUserIds method has been activated...");
    	
    	List<String> userIds = new ArrayList<String>();
    	
    	System.out.println("There are " + messages.size() + " messages");
    	
    	for(int i=0; i<messages.size(); i++) {
    		System.out.println("i is equal to " + i);
    		if(userIds.size() == 0) {
    			System.out.println("First senderid has been identified: " + messages.get(i).getSenderId());
    			userIds.add(messages.get(i).getSenderId());
    			System.out.println("userIds list now has " + userIds.size() + " ids in it");
    			Message tempMessage = messages.get(i);
    			tempMessage.setSenderId("User1");
    			messages.set(i, tempMessage);
    		}
    		else if(userIds.size() > 0){
    			//System.out.println("Checking senderid of message " +  i);
    			int checker = -1;
    			for(int j=0; j<userIds.size(); j++) {
    				if(messages.get(i).getSenderId().equals(userIds.get(j))) {
    					int messagenumber = i+1;
    					System.out.println("The senderId of message " + messagenumber + " has already sent a message");
    					Message tempMessage = messages.get(i);
    					int id = j + 1;
    	    			tempMessage.setSenderId("User" + id);
    	    			messages.set(i, tempMessage);
    	    			checker=0;
    				}
    			}
    			if(checker == -1) {
    				userIds.add(messages.get(i).getSenderId());
    				Message tempMessage = messages.get(i);
        			tempMessage.setSenderId("User" + userIds.size());
        			messages.set(i, tempMessage);
    			}
    		}
    	}
    	
    	return messages;
    }
}
