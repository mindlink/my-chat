// authors: ⓒ 2019 MindLinkSoft.com, Michail Chatzis

package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import picocli.CommandLine;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.ParseResult;
import picocli.CommandLine.UnmatchedArgumentException;

import java.lang.reflect.Type;
import java.time.Instant;

/*  07.10.2020
    CURRENT FUNCTIONALITY: As specified by MindLink Readme.md (any exceptions to that can be found below)
    Current tested functionality: Please see ConversationExporterTests class.

    FUTURE WORK:
            1) Exceptions, exceptions, exceptions!
               Improve Exception throwing and handling in all modules and functions.
            2) Organize files in project (eg. put test .txt files in separate folder)
            3) Include functionality for handling strange input formats.
               For instance: Although multiple white spaces, tabs and new lines can been handled
               when at beginning or end of messages and conversation,
               they are not handled when separating the timestamp, message and sender.
               eg. 1448470901      bob Hello there! :fails
               Possible fix: Use a better regular expression that matches multiple white spaces followed by character
            4) Specifications say replace with /*redacted*/
/*            I am replacing with *redacted* because for some reason my escape \\ character on the backslash does not work.
              Fix that.
            5) Make report generation more efficient (double nested for loops probably not the most efficient way)
            6) Extend functionality to list of user names and keywords instead of single ones
            7) Extend functionality to more report metrics offered
            8) Create automatically generated random inputs to feed into the
               parameterized testing functions.
            9) Include more property-based tests. For instance, a property of the
               filterByKeyword and hideWords functions is that at the output file
               one should never find those words. That could be a property test.


    Things I am not certain about and I would therefore ask help for:
        1) Why inner class InstantSerializer needs to be static? Is it okay to be static?
        2) Copyright issues for the idea I took from
            https://www.code4copy.com/java/compare-two-text-file-in-java/
        3) Specifications unclear on case sensitivity. My filtering is case sensitive.
           In real-life though I would guess that blackListed word filtering should be case insensitive,
           since the meaning of the word does not change with capital letters or small ones.
        4) Design choices:
                     - Putting all functions to abstract class Utilities
                     - making many methods static
                     - Having multiple open IO streams
     */

/**

 /**
 * Represents a conversation exporter that can read a conversation and write it out in JSON, processed or not.
 *
 * Exported json file contains:
 *          - An original or processed version of the conversation.
 *             Processing supported:
 *              a) filter by user
 *              b) filter by keyword
 *              c) censor specific words
 * Exported json file can contain:
 *          - Analysis metrics:
 *              a) frequency of messages by user
 *
 * Note: The application can be extended to include extra metrics and extra conversation processing
 **/

    public class ConversationExporter extends Utilities{

    /**
     * The application entry point.
     *
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception {

        // We use picocli to parse the command line - see https://picocli.info/
        ConversationExporterConfiguration config = new ConversationExporterConfiguration();
        CommandLine cmd = new CommandLine(config);

        try {
            ParseResult parseResult = cmd.parseArgs(args);

            if (parseResult.isUsageHelpRequested()) {
                cmd.usage(cmd.getOut());
                System.exit(cmd.getCommandSpec().exitCodeOnUsageHelp());
                return;
            }

            if (parseResult.isVersionHelpRequested()) {
                cmd.printVersionHelp(cmd.getOut());
                System.exit(cmd.getCommandSpec().exitCodeOnVersionHelp());
                return;
            }

            //All the business
            exportConversation(config);

            System.exit(cmd.getCommandSpec().exitCodeOnSuccess());
        } catch (ParameterException ex) {
            cmd.getErr().println(ex.getMessage());
            if (!UnmatchedArgumentException.printSuggestions(ex, cmd.getErr())) {
                ex.getCommandLine().usage(cmd.getErr());
            }

            System.exit(cmd.getCommandSpec().exitCodeOnInvalidInput());
        } catch (Exception ex) {
            ex.printStackTrace(cmd.getErr());
            System.exit(cmd.getCommandSpec().exitCodeOnExecutionException());
        }
    }

    static class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
