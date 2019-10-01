package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;

public class ConversationAnalysis {

	//returns a sorted ArrayList of String arrays that each contain a senderid and the number of messages they have sent
	public ArrayList<String[]> getUserActivity(List<Message> messages) {
		
		ArrayList<String> userList = new ArrayList<String>();
		ArrayList<Integer> activityList = new ArrayList<Integer>();
		
		for(int i=0; i<messages.size(); i++) {

    		if(userList.size() == 0) {
    			userList.add(messages.get(i).getSenderId());
    			activityList.add(1);
    		}
    		else if(userList.size() > 0){
    			int checker = -1;
    			for(int j=0; j<userList.size(); j++) {
    				if(messages.get(i).getSenderId().equals(userList.get(j))) {
    					int messageCount = activityList.get(j);
    					messageCount++;
    					activityList.set(j, messageCount);
    	    			checker=0;
    				}
    			}
    			if(checker == -1) {
    				userList.add(messages.get(i).getSenderId());
    				activityList.add(1);
    			}
    		}
    	}
		
		return sortUsersByActivity(userList, activityList);
	}
	
	//sorts an inputted arraylist of integers into highest first and sorts the arraylist of strings using the same swaps
	private ArrayList<String[]> sortUsersByActivity(ArrayList<String> userList, ArrayList<Integer> activityList) {
		int swapCount = 1;
		
		while(swapCount != 0) {
			swapCount = 0;
			
			for(int i=0; i<userList.size() - 1; i++) {
				if(activityList.get(i) < activityList.get(i+1)) {
					int tempInt = activityList.get(i);
					String tempString = userList.get(i);
					activityList.set(i, activityList.get(i+1));
					activityList.set(i+1, tempInt);
					userList.set(i, userList.get(i+1));
					userList.set(i+1, tempString);
					swapCount++;
				}
			}
		}
		
		ArrayList<String[]> userDataList = new ArrayList<String[]>();
		
		for(int i=0; i<userList.size(); i++) {
			String[] userData = { userList.get(i), Integer.toString(activityList.get(i)) };
			
			userDataList.add(userData);
		}
		
		return userDataList;
		
	}
}
