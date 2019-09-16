package com.mindlinksoft.recruitment.mychat;

import org.apache.commons.cli.*;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {

    /**
     * Builds the {@Link ConversationExporterConfiguration} object and returns it
     * @param args The CLI arguments
     * @return {@Link ConversationExporterConfiguration} config object
     */

    public ConversationExporterConfiguration buildConfig(String[] args){
        ConversationExporterConfiguration config = new ConversationExporterConfiguration();
        CommandLineParser parser = new DefaultParser();

        Options options = new Options();

        options.addRequiredOption("i", "input", true, "The conversation input file");
        options.addRequiredOption("o", "output", true, "The conversation output file");
        Option blacklist = new Option("blacklist", "Set a list of blacklisted words in the form -blacklist word secondword thirdword");
        blacklist.setArgs(Option.UNLIMITED_VALUES);
        options.addOption(blacklist);
        options.addOption("obfuscateinfo", false, "Obfuscate phone and credit card numbers");
        options.addOption("obfuscatenames", false, "Obfuscate sender names");
        options.addOption("filtersender", true, "Filter by a specified sender");
        options.addOption("filterkeyword", true, "Filter by a specified keyword");

        try {
            CommandLine cli = parser.parse( options, args );

            if (cli.hasOption("obfuscateinfo"))
                config.setObfuscateInfo(true);

            if(cli.hasOption("obfuscatenames"))
                config.setObfuscateUID(true);

            if(cli.hasOption("filtersender"))
                config.setFilterSender(new Sender(cli.getOptionValue("filtersender")));

            if(cli.hasOption("filterkeyword"))
                config.setFilterKeyword(cli.getOptionValue("filterkeyword"));

            if(cli.hasOption("blacklist"))
                config.setBlacklist(cli.getOptionValues("blacklist"));

            config.setInputFilePath(cli.getOptionValue("i"));
            config.setOutputFilePath(cli.getOptionValue("o"));

        }
        catch( ParseException exp ) {
            System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
            // automatically generate the help statement
            HelpFormatter formatter = new HelpFormatter();
            // Gets the current snapshot name for help message and prints the cli options
            formatter.printHelp(new java.io.File(ConversationExporter.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath())
                    .getName(), options);
            System.exit(1);
        }
        return config;
    }
}
