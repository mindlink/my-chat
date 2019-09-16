package com.mindlinksoft.recruitment.mychat;

import java.util.HashMap;
import java.util.Map;

public class OptionalArgumentManager {
	
	private OptionalArguments optionalArgs;
	private Map<String, String> obfuscatedUsernames;
	
	public OptionalArgumentManager() {
		this.optionalArgs = new OptionalArguments();
		this.obfuscatedUsernames = new HashMap<String, String>();
	}
	
	public OptionalArgumentManager(OptionalArguments optionalArgs) {
		this.optionalArgs = optionalArgs;
		this.obfuscatedUsernames = new HashMap<String, String>();
	}
	
	//Check if a message should be included in the output file
    public boolean messagePassesFilters(Message msg) {
    	boolean include = true;
    	
    	if (optionalArgs.usernameToFilter != "")
    		include &= (optionalArgs.usernameToFilter.toLowerCase().equals(msg.senderId.toLowerCase()));
    		
		if (optionalArgs.keywordToFilter != "")
			include &= (msg.content.toLowerCase().contains(optionalArgs.keywordToFilter.toLowerCase()));
		
		return include;
    }
    
    //Apply any redactions to the message
    public Message applyRedactions(Message msg) {    	
    	if (!optionalArgs.blacklistedWords.isEmpty())
    		msg.content = MessageFilter.redactBlacklistedKeywords(msg.content, optionalArgs.blacklistedWords);
    	
    	if (optionalArgs.hideCreditCards)
    		msg.content = MessageFilter.redactCreditCards(msg.content);
    	
    	if (optionalArgs.hidePhoneNumbers)
    		msg.content = MessageFilter.redactPhoneNumbers(msg.content);
    	
    	if (optionalArgs.obfuscateUsernames) {
    		msg.senderId = obfuscateUsername(msg.senderId);
    		
    	}

    	return msg;
    }
    
    //Apply obfuscation to usernames. A map is used to store old encryptions to speed up process.
    private String obfuscateUsername(String username) {
    	if (obfuscatedUsernames.containsKey(username)) {
			return obfuscatedUsernames.get(username);
		} 

    	String obfuscatedId = MessageFilter.obfuscateUsername(username);;
		obfuscatedUsernames.put(username, obfuscatedId);
		return obfuscatedId;
    }
}
