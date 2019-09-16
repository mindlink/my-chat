package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MessageFilter {

	//Replace matched blacklisted keywords in a string with *redacted
    public static String redactBlacklistedKeywords(String msg, Set<String> blacklistedWords) {
		for (String blacklistedWord : blacklistedWords) {
	        if (msg.toLowerCase().contains(blacklistedWord.toLowerCase())) {
	        	msg = msg.replaceAll("(?i)\\b"+blacklistedWord+"\\b", "*redacted*");	//(?i) = case insensitive, \\b = whole blacklise word
	        }
	     }
		return msg;
    }
    
    //Assume phone numbers start with a + and are 12 digits long +447951245659
    //Replace detected phone numbers with *redacted*
    public static String redactPhoneNumbers(String str) {
    	int count = 0;
    	boolean checkingForNumber = false;
    	List<String> phoneNumbers = new ArrayList<String>();
    	
		for (int i = 0; i < str.length(); i++) {	
			char c = str.charAt(i);
			
			
			if (c == '+') {												//check for beginning of phone number
				checkingForNumber = true;
				count = 0;
			} else if (checkingForNumber && Character.isDigit(c)) {		//check if number is a digit in a digit sequence
				count += 1;
			} else {
				checkingForNumber = false;
				count = 0;
			}
			
			if (checkingForNumber && count == 12) {						//check if a complete phone number has been found
				String number = str.substring(i-12, i + 1);
				phoneNumbers.add(number);
				count = 0;
				checkingForNumber = false;
			}
		}

		for (String num : phoneNumbers) {
			str = str.replaceAll("\\"+num, "*redacted*");				//replace all phone numbers with *redacted*
		}
		
		return str;
    }
    
    //Assume Credit Card numbers are in the format xxxx-xxxx-xxxx-xxxx
    //Replace redacted credit card numbers with *redacted*
    public static String redactCreditCards(String str) {
    	int count = 0;
    	boolean checkingForNumber = false;
    	List<String> ccNumbers = new ArrayList<String>();
    	
		for (int i = 0; i < str.length(); i++) {	
			char c = str.charAt(i);
			
			if (!checkingForNumber && Character.isDigit(c)) {			//check for starting digit of cc num
				checkingForNumber = true;
				count = 1;
			} else if (checkingForNumber && Character.isDigit(c)) {		//check for digit chain
				count += 1;
			} else if (c == '-' && count % 4 == 0) {					//check for - after 4 digits e.g. xxxx-
				//don't add on to count as it keeps the % working
			} else {
				checkingForNumber = false;
				count = 0;
			}
			
			if (checkingForNumber && count == 16) {						//check for full cc num
				String number = str.substring(i-18, i + 1);
				ccNumbers.add(number);
				count = 0;
				checkingForNumber = false;
			}
		}

		for (String num : ccNumbers) {
			str = str.replaceAll(num, "*redacted*");					//replace all cc nums with *redacted
		}
		
		return str;
    }
    
    //ROT13 encryption
    //Apply a ROT13 encryption to a string
    public static String obfuscateUsername(String username) {
	   StringBuilder sb = new StringBuilder();
	   sb.append("HiddenUser-");
	   for (int i = 0; i < username.length(); i++) {
	       char c = username.charAt(i);
	       if       (c >= 'a' && c <= 'm') c += 13;
	       else if  (c >= 'A' && c <= 'M') c += 13;
	       else if  (c >= 'n' && c <= 'z') c -= 13;
	       else if  (c >= 'N' && c <= 'Z') c -= 13;
	       sb.append(c);
	   }
	   return sb.toString();
	}
}
