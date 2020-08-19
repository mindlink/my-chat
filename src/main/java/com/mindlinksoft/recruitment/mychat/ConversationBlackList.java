package com.mindlinksoft.recruitment.mychat;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ConversationBlackList {
	static boolean BlackList = false;
	
	public static Boolean AskBlackList() {
	  Scanner in = new Scanner(System.in);
      System.out.println("Would you like to create a blacklist of words");
	  String decision = in.nextLine();
	  	if (decision.toLowerCase().equals("yes"))
	    {
	    	BlackList = true;
	    }
	    else if (!(decision.toLowerCase().equals("yes") || decision.toLowerCase().equals("no")))
	    {
	    	AskBlackList();
	    }
	return BlackList;
	}
	
	public static String[] BlackList() throws Exception {
		String[] blacklist = null;
	try { Scanner in = new Scanner(System.in);
	 System.out.println("How many words would you like to enter into the blacklist?");
	 int n = in.nextInt();
      blacklist = new String[n];
     	for (int i = 0; i <n; i++) {
		    Scanner in2 = new Scanner(System.in);
		    System.out.println("Please enter a word you'd like to add");
		    String temp = in2.nextLine();
		    blacklist[i] = (temp);
		}
	}
    catch(InputMismatchException exception)
    {
      System.out.println("You did not enter a whole number, please try again");
      System.out.println("The program will restart...");
      ConversationExporter.main(null);
    }
    return blacklist;
	}
	
	public void CheckBlacklist(Conversation conversation,  List<Message> messages) throws Exception {
		String[] blacklist =  ConversationBlackList.BlackList();
   		for (int b = 0; b<blacklist.length;b++) {
   			for (int i=0; i<conversation.messages.size();i++) {
   				if (messages.get(i).content.toLowerCase().contains(blacklist[b].toLowerCase()))
   				{
   					messages.get(i).content = messages.get(i).content.toLowerCase().replace(blacklist[b].toLowerCase(), "*redacted*");
   					//System.out.println(messages.get(i).content.toLowerCase());
   				}
   			}
   		}
	}
}
