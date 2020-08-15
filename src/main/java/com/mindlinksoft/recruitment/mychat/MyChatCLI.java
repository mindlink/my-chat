package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.model.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.utils.CommandLineArgumentParser;
import com.mindlinksoft.recruitment.mychat.utils.ConversationExporter;

/**
 * Command line interface for the my-chat app
 *
 */
public class MyChatCLI 
{
    /**
     * The application entry point.
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception 
    {
        ConversationExporterConfiguration configuration = CommandLineArgumentParser.parseCommandLineArguments(args);

        ConversationExporter.exportConversation(configuration);
    }
}
