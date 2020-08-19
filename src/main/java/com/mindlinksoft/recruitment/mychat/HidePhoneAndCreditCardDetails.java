package com.mindlinksoft.recruitment.mychat;

import java.util.List;

public class HidePhoneAndCreditCardDetails {
	 String PhonePattern = "\\d{11}";
	/** The below regular expression was derived from https://www.regular-expressions.info/creditcard.html#:~:text=To%20find%20card%20numbers%20with,4%2C%205%20and%206%20digits. */
	  String CreditCardPattern = "\\b(?:\\d[ -]*?){13,16}\\b";

	public void CheckPhoneDetails(Conversation conversation,  List<Message> messages) {
		for (int i=0; i<conversation.messages.size();i++) {
		String[] Eachword = messages.get(i).content.toLowerCase().split("\\s+"); // splits by whitespace
			for (String currentWord : Eachword) {
				if (currentWord.matches(PhonePattern)) 
				{
					messages.get(i).content = messages.get(i).content.toLowerCase().replace(currentWord, "*redacted*");
				}
			}	
		}
	}
	
	/** the below method works when credit card details are entered as xxxxxxxxxxxxxxxx but not xxxx xxxx xxxx xxxx, this is due to the regular expression*/
	public void CheckCreditCardDetails(Conversation conversation,  List<Message> messages) {
		for (int i=0; i<conversation.messages.size();i++) {
		String[] Eachword = messages.get(i).content.toLowerCase().split("\\s+"); // splits by whitespace
			for (String currentWord : Eachword) {
				if (currentWord.matches(CreditCardPattern)) 
				{
					messages.get(i).content = messages.get(i).content.toLowerCase().replace(currentWord, "*redacted*");
				}
			}	
		}
	}
}
	
