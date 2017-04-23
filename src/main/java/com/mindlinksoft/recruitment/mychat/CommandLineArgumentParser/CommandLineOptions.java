package com.mindlinksoft.recruitment.mychat.CommandLineArgumentParser;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

public class CommandLineOptions {
	
	public static final String COMMANDLINE_ARGUMENTS_INPUTFILEPATH  	  = "inputFilePath";
	public static final String COMMANDLINE_ARGUMENTS_OUTPUTFILEPATH  	  = "outputFilePath";
	public static final String COMMANDLINE_ARGUMENTS_USERNAME		 	  = "username";
	public static final String COMMANDLINE_ARGUMENTS_KEYWORD		  	  = "keyword";
	public static final String COMMANDLINE_ARGUMENTS_BLACKLISTEDWORDS	  = "blacklistedwords";
	public static final String COMMANDLINE_ARGUMENTS_HIDECCANDPHONENO	  = "hideccandphoneno";
	public static final String COMMANDLINE_ARGUMENTS_OBFUSCATEUSERID	  = "obfuscateuserid";
	public static final String COMMANDLINE_ARGUMENTS_HELP 		  	  	  = "help";
	
	private static CommandLineOptions instance = null;
	private final Options options;
	



	private CommandLineOptions ()
	{
		options=constructOptions();
	}
	
	public Options getOptions() {
		return options;
	}
	/**
	 * Construct ConversationExporterApplication Options.
	 * 
	 * @return Options expected from command-line .
	 */
	private  Options constructOptions() {

		return new Options().addOption(constructOption( COMMANDLINE_ARGUMENTS_INPUTFILEPATH, true,true, "Input File Path.(MANDATORY)"))
							.addOption(constructOption( COMMANDLINE_ARGUMENTS_OUTPUTFILEPATH, true, true,"Output File Path.(MANDATORY)"))
							.addOption(constructOption( COMMANDLINE_ARGUMENTS_USERNAME, false , true,"The user name to filter by.(OPTIONAL)"))
							.addOption(constructOption( COMMANDLINE_ARGUMENTS_KEYWORD, false , true,"The keyword to filter by.(OPTIONAL)"))
							.addOption(constructOption( COMMANDLINE_ARGUMENTS_BLACKLISTEDWORDS, false , true,"The list of black listed words to be filtered out (COMMA SEPARATED).(OPTIONAL)"))
							.addOption(constructOption( COMMANDLINE_ARGUMENTS_HIDECCANDPHONENO, false , false,"To filter out Cridet Cards and Phone numbers.(OPTIONAL)"))
							.addOption(constructOption( COMMANDLINE_ARGUMENTS_OBFUSCATEUSERID, false , false,"To Obfuscate User IDs.(OPTIONAL)"))
							.addOption(constructOption(  "help", false , false, "Show possible input parameters"));

	}
	
	/**
	 * Construct a new Options.
	 * @param optionName : The option name 
	 * @param isRequired : specifies if the option is mandatory
	 * @param hasArg : specifies if the option is expected to have a value
	 * @param desc : The option description
	 * @return Option created.
	 */
	private static Option constructOption(String optionName,Boolean isRequired,Boolean hasArg,String desc)
	{
		OptionBuilder.withLongOpt(optionName);
		OptionBuilder.isRequired(isRequired);
		OptionBuilder.hasArg(hasArg);
		OptionBuilder.withDescription(desc);
		return OptionBuilder.create();		
	}
	/**
	 * Returns the initialized singleton of CommandLineOptions
	 * @return
	 */
	public static CommandLineOptions getInstance()
	{
		if(instance == null)
		{
			instance = new CommandLineOptions();
		}
		return instance;
	}
}
