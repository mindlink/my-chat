package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;

import org.kohsuke.args4j.*;

import com.mindlinksoft.recruitment.mychat.filters.BlacklistFilter;
import com.mindlinksoft.recruitment.mychat.filters.ConversationFilter;
import com.mindlinksoft.recruitment.mychat.filters.CreditCardPhoneFilter;
import com.mindlinksoft.recruitment.mychat.filters.KeywordFilter;
import com.mindlinksoft.recruitment.mychat.filters.ObfuscateFilter;
import com.mindlinksoft.recruitment.mychat.filters.UserFilter;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
	
	//We use the args4j library here:
	@Option(name="-i", aliases="--input-file", usage="Input file", required=true)
	private String inputFile;

	@Option(name="-o", aliases="--output-file", usage="Output file", required=true)
	private String outputFile;
	
	@Option(name="-u", aliases ="--user", usage="Specifies the userID")
    private String senderID;
	
	@Option(name="-k", aliases ="--keyword", usage="Specifies the keyword")
    private String keyword;
	
	@Option(name="-b", aliases ="--blacklist", usage="Specifies the blacklisted words")
    private ArrayList<String> blacklist;
	
	@Option(name="-h", aliases ="--hide", usage="Hides credit card and phone numbers")
    private boolean flagHideCCT;
	
	@Option(name="-o", aliases ="--obfuscate", usage="Obfuscates userIDs")
	private boolean flagObfuscateIDs;
	
	@Option(name="-r", aliases ="--report", usage="Adds report to the conversation")
	private boolean flagReport;
	
    /**
     * Parses the given {@code arguments} into the exporter configuration.
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line arguments.
     */
    public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) {
    	
        CmdLineParser parser = new CmdLineParser(this);
        
    	try {
    		parser.parseArgument(arguments);
    		
    	} catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
    	}
    	
    	ArrayList<ConversationFilter> filters = new ArrayList<ConversationFilter>();

    	
    	if (senderID != null){
    		filters.add(new UserFilter(senderID));
    	}
    	if (keyword != null){
    		filters.add(new KeywordFilter(keyword));
    	}
    	if (blacklist != null){
    		filters.add(new BlacklistFilter(blacklist));
    	}
    	if (flagHideCCT){
    		filters.add(new CreditCardPhoneFilter());
    	}
    	if (flagObfuscateIDs){
    		filters.add(new ObfuscateFilter());
    	}
    	

    	return new ConversationExporterConfiguration(inputFile, outputFile, filters, flagReport);
    }
}
