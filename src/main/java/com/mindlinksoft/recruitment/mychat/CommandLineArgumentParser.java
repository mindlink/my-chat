package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
    /**
     * Parses the given {@code arguments} into the exporter configuration.
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line arguments.
     */
	
    private String userFilter = null;
    private String keywordFilter = null;
    private String[] blacklist = null;
    private boolean censorNumbers = false;
    private boolean censorSenderIds = false;
    
    public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) {
    	
    	if(arguments.length > 2) {
    		for(int i=2; i<arguments.length; i++) {
        		if(arguments[i].equals("-s")) {
        			System.out.println("sender filter checker used");
        			userFilter = arguments[i + 1];
        		}
        		if(arguments[i].equals("-k")) {
        			System.out.println("keyword filter checker used");
        			keywordFilter = arguments[i + 1];
        		}
        		if(arguments[i].equals("-b")) {
        			System.out.println("blacklist checker used");
        			if(arguments[i+ 1].equals("[")) {
        				int start = i + 1;
        				int end = -1;
        				
        				System.out.println("Start of blacklist is: " + start);
        				
        				for(int k=i+1; k<arguments.length; k++ ) {
        					if(arguments[k].equals("]")) {
        						end = k;
        						System.out.println("End of blacklist is: " + k);
        					}
        				}
        				
        				if(end != -1) {
        					List<String> blacklistList = new ArrayList<String>();
            				
        					i=start+1;
        					for(int j=start+1; j<end; j++) {
        						blacklistList.add(arguments[j]);
        						System.out.println(arguments[j] + " added to blacklist");
        					}
            				blacklist = blacklistList.toArray(new String[0]);
            				
            				for(int j=0; j<blacklist.length; j++) {
            					System.out.println("Censored word " + j + ": " + blacklist[j]);
            				}
        				}
        				else {
        					System.out.println("Error: start to blacklist found but end not found!");
        				}
        				
        			}
        		}
        		if(arguments[i].equals("-n")) {
        			censorNumbers = true;
        		}
        		if(arguments[i].equals("-i")) {
        			censorSenderIds = true;
        		}
        	}
    		
    		return new ConversationExporterConfiguration(arguments[0], arguments[1], userFilter, keywordFilter, blacklist, censorNumbers, censorSenderIds);
    	}
    	else {
    		return new ConversationExporterConfiguration(arguments[0], arguments[1], null, null, null, false, false);
    	}
    	
        
    }
}
