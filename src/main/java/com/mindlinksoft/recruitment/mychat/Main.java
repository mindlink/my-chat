package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.io.Reader;
import com.mindlinksoft.recruitment.mychat.io.Writer;

/**
 * 
 * @author Mohamed Yusuf
 *
 */
public class Main {
	
    /**
     * The application entry point.
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args)  {
    	new ConversationExporter().exportConversation(new CommandLineArgumentParser().parseCommandLineArguments(args), new Reader(), new Writer());
    }
}
