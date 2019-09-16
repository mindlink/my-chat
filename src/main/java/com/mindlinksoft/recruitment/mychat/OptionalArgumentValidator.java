package com.mindlinksoft.recruitment.mychat;

import java.util.HashMap;
import java.util.Map;

//Checks the validity of given optional arguments
public class OptionalArgumentValidator {
	public static OptionalArguments getValidatedArguments(String[] args) {
		
		Map<String, String> argMap = new HashMap<String, String>();
		
		for (String arg : args) {
			
			if (arg.length() < 3) {
				throw new IllegalArgumentException("Invalid optional argument detected!");
			}
			
			String key = arg.substring(0, 3);    		
			switch (key) {
				case "fun":
				case "fkw":				
				case "blk": //check list is separated by commas
					if (arg.charAt(3) == '-' && arg.length() >= 5) {
						String value = arg.substring(4, arg.length()).toLowerCase();
						argMap.put(key, value);
					} else {
						throw new IllegalArgumentException("fun, fkw, blk arguments must contain values after a hyphen! E.g. 'fun-bob'");
					}
					break;
					
				case "hcc":		
				case "hpn":
				case "oun":
					if (arg.length() == 3) {
						argMap.put(key, "");
					} else {
						throw new IllegalArgumentException("hcc, hpn, oun arguments cannot contain extra information!");
					}
					break;

				default:
					throw new IllegalArgumentException("Invalid optional argument detected!");
			}
		}
		
		OptionalArguments optionalArgs = new OptionalArguments();
		optionalArgs.addArgs(argMap);
		
		return optionalArgs;
	}
}