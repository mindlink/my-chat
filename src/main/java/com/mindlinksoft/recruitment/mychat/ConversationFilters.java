package com.mindlinksoft.recruitment.mychat;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConversationFilters {
	static boolean NameFilter = false;
	static boolean KeywordFilter = false;
	
	public static Boolean AskNameFilter() {
		try { Scanner in = new Scanner(System.in);
	    System.out.println("Would you like to filter the conversation by Name");
	    String decision = in.nextLine();
	    if (decision.toLowerCase().equals("yes"))
	    {
	    	NameFilter = true;
	    }
	    else if (!(decision.toLowerCase().equals("yes") || decision.toLowerCase().equals("no")))
	    {
	    	AskNameFilter();
	    }
		  } catch (NoSuchElementException e) {
			  System.out.println("Sorry, I didn't pick up your input, please try again");
			  AskNameFilter();
		    }
		  return NameFilter;
	}
	
	public static String NameFilter() {	
		String name = "";
	 try {   Scanner in = new Scanner(System.in);
		System.out.println("Which name would you like to filter?");
		 name = in.nextLine();
	  } catch (NoSuchElementException e) {
		  System.out.println("Sorry, I didn't pick up your input, please try again");
		  NameFilter();
	    }
		return name;
	}
	
	public static Boolean AskKeywordFilter() {
		try { Scanner in = new Scanner(System.in);
	    System.out.println("Would you like to filter the conversation by a specific keyword");
	    String decision = in.nextLine();
	    if (decision.toLowerCase().equals("yes"))
	    {
	    	KeywordFilter = true;
	    }
	    else if (!(decision.toLowerCase().equals("yes") || decision.toLowerCase().equals("no")))
	    {
	    	AskKeywordFilter();
	    }
		  } catch (NoSuchElementException e) {
			  System.out.println("Sorry, I didn't pick up your input, please try again");
			  AskKeywordFilter();
		    }
		 return KeywordFilter;
	}
	
	public static String KeyWordFilter() {	
		String keyword = "";
	    try { Scanner in = new Scanner(System.in);
		System.out.println("Which word would you like to filter?");
		 keyword = in.nextLine();
	    } catch (NoSuchElementException e) {
	    	System.out.println("Sorry, I didn't pick up your input, please try again");
	    	KeyWordFilter();
	    }
	    return keyword;
	}
	
	
}
