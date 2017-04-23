package com.mindlinksoft.recruitment.mychat.CommandLineArgumentParser;


import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.mindlinksoft.recruitment.mychat.conversation.ConversationExporterConfiguration;

/**
 * Represents a helper to parse command line arguments.
 */
public class CommandLineArgumentParser {
	
	/**
	 * Parses the given {@code arguments} into the exporter configuration.
	 * 
	 * @param arguments
	 *            The command line arguments.
	 * @return The exporter configuration representing the command line
	 *         arguments.
	 * @throws ParseException 
	 * @throws MissingOptionException
	 */
	public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments)
			throws ParseException, MissingOptionException {

		Options options = CommandLineOptions.getInstance().getOptions();

		String inputFileLocation = null;
		String outputFileLocation = null;
		String username = null;
		String keyword = null;
		String[] blacklistedwords = null;
		Boolean hideCCandPhoneno = null;
		Boolean obfuscateUserID = null;

		CommandLineParser parser = new BasicParser();

		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, arguments);

			if (cmd.hasOption(CommandLineOptions.COMMANDLINE_ARGUMENTS_HELP))
				help(options);

			inputFileLocation = cmd.getOptionValue(CommandLineOptions.COMMANDLINE_ARGUMENTS_INPUTFILEPATH);
			outputFileLocation = cmd.getOptionValue(CommandLineOptions.COMMANDLINE_ARGUMENTS_OUTPUTFILEPATH);
			username = cmd.getOptionValue(CommandLineOptions.COMMANDLINE_ARGUMENTS_USERNAME);
			keyword = cmd.getOptionValue(CommandLineOptions.COMMANDLINE_ARGUMENTS_KEYWORD);
			blacklistedwords = cmd.hasOption(CommandLineOptions.COMMANDLINE_ARGUMENTS_BLACKLISTEDWORDS)
					? cmd.getOptionValue(CommandLineOptions.COMMANDLINE_ARGUMENTS_BLACKLISTEDWORDS).split(",") : null;
			hideCCandPhoneno = cmd.hasOption(CommandLineOptions.COMMANDLINE_ARGUMENTS_HIDECCANDPHONENO);
			obfuscateUserID = cmd.hasOption(CommandLineOptions.COMMANDLINE_ARGUMENTS_OBFUSCATEUSERID);

		} catch (MissingOptionException e) {
			throw new MissingOptionException("Missing required option," + e);
		} catch (ParseException e) {
			throw new ParseException("Unable to parse the provided arguments," + e);
		}

		return new ConversationExporterConfiguration(inputFileLocation, outputFileLocation, username, keyword,
				blacklistedwords, hideCCandPhoneno, obfuscateUserID);

	}




	
	/**
	 * Prints the list of options available 
	 * 
	 */

	private static void help(Options options) {
		HelpFormatter formater = new HelpFormatter();
		formater.printHelp("ConversationExporterApplication Options", options);
		System.exit(0);
	}

}
