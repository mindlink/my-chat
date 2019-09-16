package com.mindlinksoft.recruitment.mychat;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class OptionalArguments {
	public String usernameToFilter;
	public String keywordToFilter;
	public Set<String> blacklistedWords;
	public boolean hideCreditCards;
	public boolean hidePhoneNumbers;
	public boolean obfuscateUsernames;
	
	public OptionalArguments() {
		usernameToFilter = "";
		keywordToFilter = "";
		blacklistedWords = new HashSet<String>();
		hideCreditCards = false;
		hidePhoneNumbers = false;
		obfuscateUsernames = false;
	}
	
	//Configures the OptionalArguments model based on given arguments
	public void addArgs(Map<String, String> argMap) {
		if (argMap.containsKey("fun"))
			usernameToFilter = argMap.get("fun");
		
		if (argMap.containsKey("fkw"))
			keywordToFilter = argMap.get("fkw");
		
		if (argMap.containsKey("blk")) {
			StringTokenizer st = new StringTokenizer(argMap.get("blk"), ",");
			while(st.hasMoreTokens())
				blacklistedWords.add(st.nextToken());
		}
		
		if (argMap.containsKey("hcc"))
			hideCreditCards = true;
		
		if (argMap.containsKey("hpn"))
			hidePhoneNumbers = true;
		
		if (argMap.containsKey("oun"))
			obfuscateUsernames = true;
	}
}
