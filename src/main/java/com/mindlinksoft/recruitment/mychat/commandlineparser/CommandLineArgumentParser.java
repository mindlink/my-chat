package com.mindlinksoft.recruitment.mychat.commandlineparser;

import com.mindlinksoft.recruitment.mychat.properties.MyChatApplicationConstants;
import org.apache.commons.cli.*;

/**
 * Represents the command line arguments parser.
 */
public final class CommandLineArgumentParser {

    /**
     * Commons cli formatter that prints usage instructions
     */
    private HelpFormatter formatter = new HelpFormatter();

    /**
     * Parses the given {@code arguments} using commons cli parser.
     * @param arguments The command line arguments.
     * @return a {@link CommandLine} object from commons cli, used for argument interrogation
     * @throws IllegalArgumentException when argument parsing fails
     *
     */
    public CommandLine parseCommandLineArguments(String[] arguments) throws IllegalArgumentException {
        CommandLineParser parser = new DefaultParser();
        Options options = this.setupOptions();
        try {
            CommandLine cmd = parser.parse(options, arguments);
            if(cmd.hasOption(MyChatApplicationConstants.CLI_HELP_SHORT_OPTION)) {
                formatter.printHelp("java MyChatApplication [OPTION]... [PATH/TO/CONVERSATION.TXT] [PATH/TO/OUTPUT.JSON]", options);
                System.exit(0);
            }
            int argNumber = cmd.getArgs().length;
            if(argNumber != 2)
                throw new IllegalArgumentException("Wrong number of arguments - ("+ argNumber +") presented. 2 arguments required");
            return cmd;
        } catch (ParseException e){
            throw new IllegalArgumentException("Options parsing failed. Use \"java MyChatApplication -h\" or \"java MyChatApplication --help\" form a qualified directory for a list of available options.", e);
        }
    }

    /**
     * Setup commons cli Options object with available command line options.
     * @return The {@link Options} object
     */
    private Options setupOptions(){
        // create Options object - commons cli
        Options options = new Options();

        // add options
        //flags
        options.addOption(
                MyChatApplicationConstants.CLI_HELP_SHORT_OPTION,
                MyChatApplicationConstants.CLI_HELP_LONG_OPTION,
                MyChatApplicationConstants.CLI_HELP_HAS_ARGS,
                MyChatApplicationConstants.CLI_HELP_DESC );

        options.addOption(
                MyChatApplicationConstants.CLI_HIDE_SD_SHORT_OPTION,
                MyChatApplicationConstants.CLI_HIDE_SD_LONG_OPTION,
                MyChatApplicationConstants.CLI_HIDE_SD_HAS_ARGS,
                MyChatApplicationConstants.CLI_HIDE_SD_DESC );


        options.addOption(
                MyChatApplicationConstants.CLI_OBFUSCATE_SHORT_OPTION,
                MyChatApplicationConstants.CLI_OBFUSCATE_LONG_OPTION,
                MyChatApplicationConstants.CLI_OBFUSCATE_HAS_ARGS,
                MyChatApplicationConstants.CLI_OBFUSCATE_DESC );

        //options with arguments
        Option userName = Option.builder( MyChatApplicationConstants.CLI_USER_FILTER_SHORT_OPTION )
                .longOpt( MyChatApplicationConstants.CLI_USER_FILTER_LONG_OPTION )
                .desc( MyChatApplicationConstants.CLI_USER_FILTER_DESC )
                .hasArg()
                .argName( MyChatApplicationConstants.CLI_USER_FILTER_ARGNAME )
                .build();

        Option filterKeyword = Option.builder( MyChatApplicationConstants.CLI_KEYWORD_FILTER_SHORT_OPTION )
                .longOpt( MyChatApplicationConstants.CLI_KEYWORD_FILTER_LONG_OPTION )
                .desc( MyChatApplicationConstants.CLI_KEYWORD_FILTER_DESC )
                .hasArg()
                .argName( MyChatApplicationConstants.CLI_KEYWORD_FILTER_ARGNAME )
                .build();

        Option blackList = Option.builder( MyChatApplicationConstants.CLI_BLACKLIST_SHORT_OPTION )
                .longOpt( MyChatApplicationConstants.CLI_BLACKLIST_LONG_OPTION )
                .desc( MyChatApplicationConstants.CLI_BLACKLIST_DESC )
                .hasArg()
                .argName( MyChatApplicationConstants.CLI_BLACKLIST_ARGNAME )
                .valueSeparator(',')
                .build();

        options.addOption( userName );
        options.addOption( filterKeyword );
        options.addOption( blackList );

        return options;
    }
}
