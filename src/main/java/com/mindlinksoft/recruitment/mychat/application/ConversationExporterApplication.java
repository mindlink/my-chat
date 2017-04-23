
package com.mindlinksoft.recruitment.mychat.application;

import com.mindlinksoft.recruitment.mychat.CommandLineArgumentParser.CommandLineArgumentParser;
import com.mindlinksoft.recruitment.mychat.conversation.ConversationExporter;
import com.mindlinksoft.recruitment.mychat.conversation.ConversationExporterConfiguration;

public class ConversationExporterApplication {

    /**
     * The application entry point.
     * @param args The command line arguments 
     * Here is an example of Command Line Arguments
     * -inputFilePath,C:\TEMP\chat.txt
     * -outputFilePath,C:\TEMP\chatJSON.txt
     * -keyword,pie
     * -blacklistedwords,pie,Hello
     * -hideccandphoneno
     * -obfuscateuserid
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception {
    	    	
        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration configuration =new CommandLineArgumentParser().parseCommandLineArguments(args);
        exporter.exportConversation(configuration);
    }

}
