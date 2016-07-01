package com.mindlinksoft.recruitment.mychat;



import java.util.List;
import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.mindlinksoft.recruitment.mychat.ConvoConfig;
/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {

	private final Options opt;

	/**
	 * Parses the given {@code arguments} into the exporter configuration.
	 * @param arguments The command line arguments.
	 * @return The exporter configuration representing the command line arguments.
	 */

	public CommandLineArgumentParser(){

		// create new Options object
		this.opt = new Options();

		// adds the possible command line options
		//opt.addOption(ConvoConfig.EXPORT.getValue(), false, "export file .. ");
		opt.addOption(ConvoConfig.INPUT.getValue(), true, "file path input");
		opt.addOption(ConvoConfig.OUTPUT.getValue(), true, "file path output");
		//opt.addOption(ConvoConfig.FILTER.getValue(), true, "type of filtering");
		opt.addOption(ConvoConfig.USER.getValue(), true, "filter by user");
		opt.addOption(ConvoConfig.KEYWORD.getValue(), true, "filter by keyword");
		opt.addOption(ConvoConfig.BLACKLIST.getValue(), true, "replace blacklist word with redacted");	


	}

	/**
	 * Method responsible for command line argument parsing
	 * 
	 * @param takes in a string array of arguments
	 */

	public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) throws ParseException {
		
		try{
			CommandLineParser par = new GnuParser();
			CommandLine ln = par.parse(opt, arguments);

			//check valid input (for both input file path and output file path)
			if (!ln.hasOption(ConvoConfig.INPUT.getValue()) || !ln.hasOption(ConvoConfig.OUTPUT.getValue())){
				return null;

			}

			List<String> bl = null;
			String blVal = ln.getOptionValue(ConvoConfig.BLACKLIST.getValue());

			//    	if (blVal == null){
			//    		// print error messaeg 
			//    	}

			// if blacklist value is not empty
			if(blVal != null){
				bl = Arrays.asList(blVal.split("\\s*,\\s*")); // splits by comma and gets rid of aditional whitespace around the word
			}


			return new ConversationExporterConfiguration(ln.getOptionValue(ConvoConfig.INPUT.getValue()),
					ln.getOptionValue(ConvoConfig.OUTPUT.getValue()),
					ln.getOptionValue(ConvoConfig.USER.getValue()),
					ln.getOptionValue(ConvoConfig.KEYWORD.getValue()), bl);
			//ln.getOptionValue(ConvoConfig.BLACKLIST.getValue()));
		}catch (ParseException e){
        	throw new IllegalArgumentException("error while parsing the configuration", e);

		}

	}
}
