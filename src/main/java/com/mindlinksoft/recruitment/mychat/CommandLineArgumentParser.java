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
import com.mindlinksoft.recruitment.mychat.commands.HideWordsCommand;
import com.mindlinksoft.recruitment.mychat.commands.IConversationExportCommand;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {


	/**
	 * command for filtering by user
	 */
	private static final String filterByUserCommand = "u";
	
	/**
	 * command for filtering by keyword
	 */
	private static final String filterByKeywordCommand = "k";
	
	/**
	 * command for hiding words
	 */
	private static final String hideWordsCommand = "b";
	
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
    	Options options = new Options();

    	options.addOption(filterByUserCommand, true, "filter by user");
    	options.addOption(filterByKeywordCommand, true, "filter by keyword");
    	Option hideWordsOption = new Option(hideWordsCommand, true, "hide words");
    	hideWordsOption.setOptionalArg(true);
    	hideWordsOption.setArgs(maxWordArgs);
    	options.addOption(hideWordsOption);
    	
    	CommandLineParser parser = new DefaultParser();
    	// parse commands and add to commands list
    	try {
			CommandLine cmd = parser.parse(options, arguments);
			if(cmd.hasOption(filterByUserCommand)) { //returning false - why?
				String user = cmd.getOptionValue(filterByUserCommand);
				if (user.isEmpty()) throw new ParseException("Filter by user argument not specified");
				commands.add(new FilterByUserCommand(user));
			}

			if(cmd.hasOption(filterByKeywordCommand)) {
				String kw = cmd.getOptionValue(filterByKeywordCommand);
				if (kw.isEmpty()) throw new ParseException("Filter by keyword argument not specified");
				commands.add(new FilterByKeywordCommand(kw));
			}

			if(cmd.hasOption(hideWordsCommand)) {
				String[] words = cmd.getOptionValues(hideWordsCommand);
				if (words == null || words.length < 1) throw new ParseException("Word(s) to hide not specified");
				commands.add(new HideWordsCommand(words));
			}
			
		} catch (ParseException e) {
			throw e;
		}
    	
    	
        return new ConversationExporterConfiguration(arguments[0], arguments[1], commands);
    }
}
