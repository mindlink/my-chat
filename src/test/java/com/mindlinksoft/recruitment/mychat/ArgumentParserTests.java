package test.java.com.mindlinksoft.recruitment.mychat;

import org.junit.Test;

import main.java.com.mindlinksoft.recruitment.mychat.CommandLineArgumentParser;
import main.java.com.mindlinksoft.recruitment.mychat.configuration.ConversationExporterConfiguration;
import main.java.com.mindlinksoft.recruitment.mychat.exceptions.InvalidArgumentListException;
import static org.junit.Assert.*;

/**
 * Tests for the {@link CommandLineArgumentParser}.
 */
public class ArgumentParserTests {
	CommandLineArgumentParser parser = new CommandLineArgumentParser();

    @Test
    public void testBasicVersion() throws InvalidArgumentListException, Exception {
    	String[] args = {"chat.txt", "chat.json"}; 
    	
    	ConversationExporterConfiguration config = parser.parseCommandLineArguments(args);
    	assertEquals("chat.txt", config.getInputFilePath());
    	assertEquals("chat.json", config.getOutputFilePath());
	}

    @Test
    public void testAllFlagsEnabled() throws InvalidArgumentListException, Exception {
    	String[] args = {
    			CommandLineArgumentParser.USER_STATS_FLAG,
    			CommandLineArgumentParser.HIDE_NUMBERS_FLAG,
    			CommandLineArgumentParser.OBFUSCATE_USERS_FLAG,
    			CommandLineArgumentParser.BLACKLIST_FLAG, "the", "there",
    			CommandLineArgumentParser.USER_FLAG, "bob",
    			CommandLineArgumentParser.OUTPUT_FLAG, "chat.json",
    			CommandLineArgumentParser.INPUT_FLAG, "chat.txt"
    	};
    	
    	ConversationExporterConfiguration config = parser.parseCommandLineArguments(args);
    	assertEquals(3, config.getEnabledProcessors().getAllProcessors().size());
    	assertEquals("chat.json", config.getOutputFilePath());
    	assertEquals("chat.txt", config.getInputFilePath());
	}
}
