package com.mindlinksoft.recruitment.mychat;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.mindlinksoft.recruitment.mychat.exceptions.ReaderException;
import com.mindlinksoft.recruitment.mychat.exceptions.WriterException;
import com.mindlinksoft.recruitment.mychat.io.Reader;
import com.mindlinksoft.recruitment.mychat.io.Writer;

/**
 * 
 * @author Mohamed Yusuf
 *
 */
public class Main {
	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
	
    /**
     * The application entry point.
     * @param args The command line arguments.
     */
    public static void main(String[] args)  {
    	try {
			new ConversationExporter().exportConversation(new CommandLineArgumentParser().parseCommandLineArguments(args), new Reader(), new Writer());
    	}catch(ReaderException e) {
    		LOGGER.log(Level.SEVERE, "Could not read file!");
			e.printStackTrace();
		} catch (WriterException e) {
			LOGGER.log(Level.SEVERE, "Could not write file!");
			e.printStackTrace();
		}
    }
}
