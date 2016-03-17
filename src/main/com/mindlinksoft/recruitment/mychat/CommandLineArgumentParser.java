package com.mindlinksoft.recruitment.mychat;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.BasicParser;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
    /**
     * Parses the given {@code arguments} into the exporter configuration.
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line arguments.
     */
    public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) throws InvalidArgumentException {
        String inputFilePath = null;
        String outputFilePath = null;
        String username = null;
        String keyword = null;
        String blacklist = null;
        boolean hideNumbers = false;
        boolean obfuscateID = false;
        Options options = new Options();

        // defined flags for the command line
        options.addOption("h", "help", false, "Use the style: -flag1 arg1 -flag2 arg2 ...");
        options.addOption("i", "input", true, "Input file");
        options.addOption("o", "output", true, "Output file");
        options.addOption("u", "username", true, "Username to filter");
        options.addOption("k", "keyword", true, "Keyword to filter");
        options.addOption("b", "blacklist", true, "Value to filter");
        options.addOption("n", "hidenumbers", false, "Number to be hidden");
        options.addOption("f", "obfuscate", false, "Obfuscate ids");

        // parse
        try {
            CommandLineParser parser = new BasicParser();
            CommandLine cmd = parser.parse(options, arguments);

            if (cmd.hasOption("h"))
                System.out.println("Use the style: -flag1 arg1 -flag2 arg2 ...");
            else {
                if (cmd.hasOption("i"))
                    inputFilePath = cmd.getOptionValue("i");
                if (cmd.hasOption("o"))
                    outputFilePath = cmd.getOptionValue("o");
                if (cmd.hasOption("u"))
                    username = cmd.getOptionValue("u");
                if (cmd.hasOption("k"))
                    keyword = cmd.getOptionValue("k");
                if (cmd.hasOption("b"))
                    blacklist = cmd.getOptionValue("b");
                if (cmd.hasOption("n"))
                    hideNumbers = true;
                if (cmd.hasOption("f"))
                    obfuscateID = true;
            }
        } catch (ParseException e) {
            throw new InvalidArgumentException("Parser error: Argument no valid");
        }
        if (inputFilePath == null || outputFilePath == null)
            throw new InvalidArgumentException("Parse error: Not enough arguments");

        return new ConversationExporterConfiguration(inputFilePath, outputFilePath, username, keyword, blacklist, hideNumbers, obfuscateID);
    }
}
