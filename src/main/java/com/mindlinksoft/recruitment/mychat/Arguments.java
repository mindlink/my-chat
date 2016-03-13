package com.mindlinksoft.recruitment.mychat;

import org.wymiwyg.commons.util.arguments.ArgumentsWithHelp;
import org.wymiwyg.commons.util.arguments.CommandLine;

/**
 *
 * @author Gabor
 */
public interface Arguments extends ArgumentsWithHelp {

    @CommandLine(
            longName = "input",
            shortName = {"I"},
            required = true,
            description = "Absolute path pointing to the input file. (Required)")
    public String getInputFilePath();

    @CommandLine(
            longName = "output",
            shortName = {"O"},
            required = true,
            description = "Absolute path pointing to the input file. (Required)")
    public String getOutputFilePath();

    @CommandLine(
            longName = "sender",
            shortName = {"S"},
            required = false,
            description = "Filter messages by a specific sender. (Optional)")
    public String getSenderFilter();

    @CommandLine(
            longName = "keyword",
            shortName = {"K"},
            required = false,
            description = "Filter messages by a specific keyword. (Optional)")
    public String getKeywordFilter();

    @CommandLine(
            longName = "blacklist",
            shortName = {"B"},
            required = false,
            description = "Blacklisted words are hidden from export. (Optional)")
    public String getBlacklistFilter();

    @CommandLine(
            longName = "hide_sensitive",
            shortName = {"HS"},
            description = "Flag for hiding sensitive information from messages. (Optional)",
            isSwitch = false)
    public boolean isSensitiveInfoHidden();

    @CommandLine(
            longName = "obfuscate_sender",
            shortName = {"OS"},
            description = "Flag for obfuscating sender IDs. (Optional)",
            isSwitch = false)
    public boolean isObfuscateSender();
}
