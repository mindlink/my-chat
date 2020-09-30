package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.mindlinksoft.recruitment.mychat.commands.FilterByKeywordCommand;
import com.mindlinksoft.recruitment.mychat.commands.FilterByUserCommand;
import com.mindlinksoft.recruitment.mychat.commands.HideNumbersCommand;
import com.mindlinksoft.recruitment.mychat.commands.HideBlacklistWordsCommand;
import com.mindlinksoft.recruitment.mychat.commands.IConversationExportCommand;
import com.mindlinksoft.recruitment.mychat.commands.ObfuscateUsersCommand;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
	
	/**
	 * maximum number of arguments for hiding words
	 */
	private static final int maxWordArgs = 10;
	
	
    /**
     * Parses the given {@code arguments} into the exporter configuration.
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line arguments.
     * @throws ParseException 
     */
    public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) throws ParseException {
    	if (arguments.length < 2) {
    		throw new ParseException("Not enough arguments. Minimum arguments are: <inputFilePath> <outPutfilePath>");
    	}
    	
    	Collection<IConversationExportCommand> commands = new ArrayList<IConversationExportCommand>();
    	
    	// create command line options using apache commons CLI
    	Options options = setUpOptions();
    	commands = parseOptions(options, arguments);

    	  	
        return new ConversationExporterConfiguration(arguments[0], arguments[1], commands);
    }
    
    /**
     * sets up the optional commands in an Options object
     * @return Options object containing all optional commands to accept
     */
    private Options setUpOptions() {
    	Options options = new Options();
    	options.addOption(OptionalCommand.FilterByUser.opt(),
    						true, 
    						"filter by user");
    	options.addOption(OptionalCommand.FilterByKeyword.opt(), 
    						true, 
    						"filter by keyword");
    	Option hideWordsOption = new Option(OptionalCommand.HideBlackListWords.opt(), 
    						true, 
    						"hide words");
    	hideWordsOption.setOptionalArg(true);
    	hideWordsOption.setArgs(maxWordArgs);   	
    	options.addOption(hideWordsOption);
    	
    	options.addOption(OptionalCommand.HideNumbers.opt(), 
    						false,
    						"hide credit card and phone numbers");
    	options.addOption(OptionalCommand.ObfuscateUsernames.opt(),
    						false, 
    						"Obfuscate usernames");
    	return options;
    }
    
    /**
     * Parses the given arguments into a collection of IConversationExportCommand
     *  according to given Options
     * @param arguments
     * @return Collection of IConversationExportCommand relating to the optional commands
     * @throws ParseException if there is an undefined option argument
     */
    private Collection<IConversationExportCommand> parseOptions(Options options, String[] arguments) throws ParseException{
    	Collection<IConversationExportCommand> commands = new ArrayList<IConversationExportCommand>();
    	
    	CommandLineParser parser = new DefaultParser();
    	// parse commands and add to commands list
    	try {
			CommandLine cmd = parser.parse(options, arguments);
			if(cmd.hasOption(OptionalCommand.FilterByUser.opt())) {
				String user = cmd.getOptionValue(OptionalCommand.FilterByUser.opt());
				if (user.isEmpty()) throw new ParseException("Filter by user argument not specified");
				commands.add(new FilterByUserCommand(user));
			}

			if(cmd.hasOption(OptionalCommand.FilterByKeyword.opt())) {
				String kw = cmd.getOptionValue(OptionalCommand.FilterByKeyword.opt());
				if (kw.isEmpty()) throw new ParseException("Filter by keyword argument not specified");
				commands.add(new FilterByKeywordCommand(kw));
			}

			if(cmd.hasOption(OptionalCommand.HideBlackListWords.opt())) {
				String[] words = cmd.getOptionValues(OptionalCommand.HideBlackListWords.opt());
				if (words == null || words.length < 1) throw new ParseException("Word(s) to hide not specified");
				commands.add(new HideBlacklistWordsCommand(words));
			}

			if(cmd.hasOption(OptionalCommand.HideNumbers.opt())) {
				commands.add(new HideNumbersCommand());
			}
			
			if(cmd.hasOption(OptionalCommand.ObfuscateUsernames.opt())) {
				commands.add(new ObfuscateUsersCommand());
			}
			
		} catch (ParseException e) {
			throw e;
		}
    	
    	return commands;
    }
}
