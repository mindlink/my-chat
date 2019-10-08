package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.cli.*;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
    /**
     * Parses the given {@code arguments} into the exporter configuration. Outputs user friendly help
     * if given invalid arguments.
     * @param args The command line arguments.
     * @return The exporter configuration representing the command line arguments.
     */
    public ConversationExporterConfiguration parseCommandLineArguments(String[] args) {
 	
        Options options = new Options();

        Option input = new Option("i", "input", true, "input file path");
        input.setRequired(true);
        options.addOption(input);

        Option output = new Option("o", "output", true, "output file path");
        output.setRequired(true);
        options.addOption(output);
        
        Option user = new Option("u", "user", true, "user filter");
        user.setRequired(false);
        options.addOption(user);
        
        Option keyword = new Option("k", "keyword", true, "keyword filter");
        keyword.setRequired(false);
        options.addOption(keyword);
        
        Option blacklist = new Option("b", "blacklist", true, "blacklisted words");
        blacklist.setRequired(false);
        blacklist.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(blacklist);
        
        Option hideNumbers = new Option("h", "hideNumbers", false, "Hide credit card and phone numbers");
        hideNumbers.setRequired(false);
        options.addOption(hideNumbers);
        
        Option obfuscateIDs = new Option("f", "obfuscateIDs", false, "Obfuscate user IDs");
        obfuscateIDs.setRequired(false);
        options.addOption(obfuscateIDs);
        
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
            boolean hideNumbersFlag = cmd.hasOption("h");
            boolean obfuscateIDsFlag = cmd.hasOption("f");
            ArrayList<String> blacklistArray = null;
            if(cmd.hasOption("b")) {
            	blacklistArray = new ArrayList<String>(Arrays.asList(cmd.getOptionValue("b").split(" ")));
            }
            
            return new ConversationExporterConfiguration(cmd.getOptionValue("i"), cmd.getOptionValue("o"), cmd.getOptionValue("u"), 
            		cmd.getOptionValue("k"), blacklistArray, hideNumbersFlag, obfuscateIDsFlag);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("my-chat", options);
            System.exit(1);
        }
		return null;
    }
}
