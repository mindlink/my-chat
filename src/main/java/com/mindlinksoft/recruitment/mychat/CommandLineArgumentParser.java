package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
    /**
     * Parses the given {@code arguments} into the exporter configuration.
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line arguments.
     * @throws ArrayIndexOutOfBoundsException thrown when too few arguments given.
     */
    public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) throws Exception {
    	try {
    		String user = null;
    		String keyword = null;
    		ArrayList<String> blacklist = new ArrayList<String>();
    		
    		for (int i = 2; i < arguments.length && (user == null || keyword == null || blacklist.size() == 0); i++)
    		{
    			if ( user == null && arguments[i].startsWith("user:") && arguments[i].length() > 5 )
    				user = arguments[i].substring(5);
    			else if ( keyword == null && arguments[i].startsWith("keyword:") && arguments[i].length() > 8 )
    				keyword = arguments[i].substring(8);
    			else if ( blacklist.size() == 0 && arguments[i].startsWith("blacklist:") && arguments[i].length() > 10 )
    			{
    				int iBegin = 10;
    				int iEnd = arguments[i].indexOf(',', iBegin + 1);;
    				String str;
    				while ( iEnd != -1 )
    				{
    					if ( (str = arguments[i].substring(iBegin, iEnd)) != "" )
    						blacklist.add(str);
    					
    					iBegin = iEnd + 1;
    					iEnd = arguments[i].indexOf(',', iBegin + 1);
    				}
					if ( (str = arguments[i].substring(iBegin)) != "" )
						blacklist.add(str);
    			}
    		}
    		
    		String[] blacklistFinal = new String[blacklist.size()];
    		blacklistFinal = blacklist.toArray(blacklistFinal);
    		
    		return new ConversationExporterConfiguration(arguments[0], arguments[1], user, keyword, blacklistFinal);
    	} catch ( ArrayIndexOutOfBoundsException e ) {
            throw new IllegalArgumentException("Must give at least 2 arguments.");
    	}
    }
}
