package com.mindlinksoft.recruitment.mychat.model;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;

import com.mindlinksoft.recruitment.mychat.ConversationExporter;
import com.mindlinksoft.recruitment.mychat.enums.CommandLineArgument;

/**
 * Helper class to parse command line arguments.
 */
public final class CommandLineArgumentParser {

	public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) {
		Options options = buildOptions();
		try {
			return new ConversationExporterConfiguration(parse(options, arguments));
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			printUsage(options);
			return null;
		}
	}

	private void printUsage(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(ConversationExporter.class.getSimpleName(), options);
	}

	private Map<CommandLineArgument, String> parse(Options options, String[] arguments) {
		Map<CommandLineArgument, String> map = new HashMap<>();
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine cmd = parser.parse(options, arguments);
			for (CommandLineArgument arg : CommandLineArgument.values())
				addCommandLineArgument(map, cmd, arg);
		} catch (ParseException e) {
			throw new InvalidParameterException("Failed to parse commandline arguments");
		}
		return map;
	}

	/**
	 * Adds command line argument to configuration map if it has been provided
	 * by the user
	 */
	protected void addCommandLineArgument(Map<CommandLineArgument, String> map, CommandLine cmd,
			CommandLineArgument arg) {
		if (cmd.hasOption(arg.getChar())) {
			if (map.containsKey(arg))
				throw new InvalidParameterException(arg + " specified more than once!");
			if (arg.isValueRequired())
				map.put(arg, StringUtils.join(getOptionValues(cmd, arg), " "));
			else
				map.put(arg, "");
		} else if (arg.isRequired())
			throw new InvalidParameterException(arg + " is required!");
	}

	private List<String> getOptionValues(CommandLine cmd, CommandLineArgument arg) {
		return Arrays.asList(cmd.getOptionValues(arg.getChar()));
	}

	private Options buildOptions() {
		Options options = new Options();
		for (CommandLineArgument arg : CommandLineArgument.values())
			options.addOption(arg.getChar().toString(), arg.isValueRequired(), arg.getDescription());
		return options;
	}

}
