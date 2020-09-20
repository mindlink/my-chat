package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * This class tests various aspects of the CommandLineArgumentParser
 * 
 * @author Mohamed Yusuf
 *
 */
public class CommandLineArgumentParserTest {

	@Test
	public void FilterUserFlagTest() {
		String[] args = {"chat.txt", "output.json", "-fu", "mike"};
		
		ConversationExporterConfiguration config = new CommandLineArgumentParser().parseCommandLineArguments(args);
		assertTrue(config.isFILTER_USER());
	}
	
	@Test
	public void FilterUserContentTest() {
		String[] args = {"chat.txt", "output.json", "-fu", "mike"};
		
		ConversationExporterConfiguration config = new CommandLineArgumentParser().parseCommandLineArguments(args);
		assertEquals(config.getUsersToFilter()[0], args[3]);
	}
	
	@Test
	public void FilterUserMultipleContentTest() {
		String[] args = {"chat.txt", "output.json", "-fu", "mike", "john", "abby"};
		
		ConversationExporterConfiguration config = new CommandLineArgumentParser().parseCommandLineArguments(args);
		for(int i = 3;i < args.length;i++) {
			assertEquals(config.getUsersToFilter()[i-3], args[i]);
		}
	}
	
	@Test
	public void FilterWordFlagTest() {
		String[] args = {"chat.txt", "output.json", "-fw", "pie"};
		
		ConversationExporterConfiguration config = new CommandLineArgumentParser().parseCommandLineArguments(args);
		assertTrue(config.isFILTER_WORD());
	}
	
	@Test
	public void FilterWordContentTest() {
		String[] args = {"chat.txt", "output.json", "-fw", "pie"};
		
		ConversationExporterConfiguration config = new CommandLineArgumentParser().parseCommandLineArguments(args);
		assertEquals(config.getWordsToFilter()[0], args[3]);
	}
	
	@Test
	public void FilterWordMultipleContentTest() {
		String[] args = {"chat.txt", "output.json", "-fw", "pie", "smith", "apple"};
		
		ConversationExporterConfiguration config = new CommandLineArgumentParser().parseCommandLineArguments(args);
		for(int i = 3;i < args.length;i++) {
			assertEquals(config.getWordsToFilter()[i-3], args[i]);
		}
	}
	
	@Test
	public void FilterBlackListFlagTest() {
		String[] args = {"chat.txt", "output.json", "-b", "pie"};
		
		ConversationExporterConfiguration config = new CommandLineArgumentParser().parseCommandLineArguments(args);
		assertTrue(config.isBLACKLIST_WORD());
	}
	
	@Test
	public void FilterBlackListContentTest() {
		String[] args = {"chat.txt", "output.json", "-b", "pie"};
		
		ConversationExporterConfiguration config = new CommandLineArgumentParser().parseCommandLineArguments(args);
		assertEquals(config.getWordsToBlacklist()[0], args[3]);
	}
	
	@Test
	public void FilterBlackListMultipleContentTest() {
		String[] args = {"chat.txt", "output.json", "-b", "pie", "smith", "apple"};
		
		ConversationExporterConfiguration config = new CommandLineArgumentParser().parseCommandLineArguments(args);
		for(int i = 3;i < args.length;i++) {
			assertEquals(config.getWordsToBlacklist()[i-3], args[i]);
		}
	}
	
	@Test
	public void FilterOBFSUserFlagTest() {
		String[] args = {"chat.txt", "output.json", "-ou"};
		
		ConversationExporterConfiguration config = new CommandLineArgumentParser().parseCommandLineArguments(args);
		assertTrue(config.isOBFS_USER());
	}
	
	@Test
	public void FilterOBFSCreditFlagTest() {
		String[] args = {"chat.txt", "output.json", "-oc"};
		
		ConversationExporterConfiguration config = new CommandLineArgumentParser().parseCommandLineArguments(args);
		assertTrue(config.isOBFS_CREDIT_CARD());
	}
	
	@Test
	public void FilterReportFlagTest() {
		String[] args = {"chat.txt", "output.json", "-r"};
		
		ConversationExporterConfiguration config = new CommandLineArgumentParser().parseCommandLineArguments(args);
		assertTrue(config.isGEN_REPORT());
	}
}
