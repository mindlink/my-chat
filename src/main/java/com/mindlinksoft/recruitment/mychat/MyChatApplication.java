package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.commandlineparser.CommandLineArgumentParser;
import com.mindlinksoft.recruitment.mychat.conversation.Conversation;
import com.mindlinksoft.recruitment.mychat.conversation.ConversationInterface;
import com.mindlinksoft.recruitment.mychat.editor.ConversationEditor;
import com.mindlinksoft.recruitment.mychat.exporter.ConversationExporter;
import com.mindlinksoft.recruitment.mychat.loader.ConversationLoader;
import org.apache.commons.cli.CommandLine;


public class MyChatApplication {

    /**
     * The application entry point.
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception {

        //command line argument parsing
        CommandLine cli = new CommandLineArgumentParser().parseCommandLineArguments(args);
        String[] cliArgs = cli.getArgs();

        //load conversation from file using conversation loader
        ConversationInterface conversation = new ConversationLoader().loadConversation(cliArgs[0]);

        //create editor instance
        ConversationEditor editor = new ConversationEditor();
        //load filters and formatters using cli options
        editor.loadFiltersAndFormatters(cli);

        //apply filters and formatters on conversation
        conversation = editor.applyFilters(conversation);
        conversation = editor.applyFormatters(conversation);

        //generate activity report
        conversation.setupActiveUserReport();

        //export conversation
        new ConversationExporter().writeConversation(conversation, cliArgs[1]);
    }
}
