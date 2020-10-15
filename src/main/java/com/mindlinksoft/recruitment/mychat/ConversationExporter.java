package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import picocli.CommandLine;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.ParseResult;
import picocli.CommandLine.UnmatchedArgumentException;

import java.lang.reflect.Type;
import java.time.Instant;


/*
    15.10.2020

    Corrections from the previous version:
    1) New design: more careful delegation of responsibilities
    2) Functions that do not have to mutate their input, do not do so anymore.
       For instance, UserFilterer can filter the conversation given as input without
       mutating it. It creates a new (filtered) one and stores it in a private field.
       Same happens with KeywordFilter and WordRedacter (obviously with Reporter too).
    3) In the previous design the configuration object was passed around to almost every
       single function. I corrected this to only pass it where it is truly effective
       to do so. For instance, UserFilterer now accepts a String userName as input,
       instead of the configuration file which has all configuration settings.
    4) Pre-processing the file (trimming it from new lines and whitespaces) used
       to be an extra function that read the input file, trimmed and then exported
       to a new intermediary output file. The intermediary file was then used as input
       for the normal IO operation. That was a bad design.
       Now, trimming is performed while reading from the original input chat file.
       No new intermediate files created.
 */

/*

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
               eg. 1448470901      bob Hello there!  ----> fails
               Possible fix: Use a better regular expression that matches multiple white spaces followed by character
            4) Specifications say replace with /*redacted*/
/*            I am replacing with *redacted* because for some reason my escape \\ character on the backslash does not work.
              Fix that.
            5) Make report generation more efficient (double nested for loops probably not the most efficient way)
            6) Extend functionality to list of user names and keywords instead of single ones
            7) Extend functionality to more report metrics offered

 */


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

public class ConversationExporter {

    /**
     * The application entry point.
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception {

        ConversationExporterConfiguration configuration = new ConversationExporterConfiguration();
        CommandLine cmd = new CommandLine(configuration);

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

            //Main Business logic
            exportConversation(configuration);

            // TODO: Add more logging...
            System.out.println("Conversation exported from '" + configuration.getInputFilePath() + "' to '" + configuration.getOutputFilePath());

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

    /**
     * Function: Main business. Read from input file, process, write to output file.
     *
     * @param configuration
     * @throws Exception
     */
    protected static void exportConversation(ConversationExporterConfiguration configuration) throws Exception{

        IO ioProcessor = new IO(configuration);
        ioProcessor.readConversation();
        Conversation inputConversation = ioProcessor.getConversationRead();
        Processor processor = new Processor(inputConversation, configuration);
        processor.processConversation();
        ioProcessor.writeObjectToJsonFile(processor.getObjectToExport());
    }

    static class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
