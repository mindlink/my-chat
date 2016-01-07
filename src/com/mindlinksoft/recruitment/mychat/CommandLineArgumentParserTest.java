package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * Tests for the {@link CommandLineArgumentParser}.
 */
@RunWith(Parameterized.class)
public class CommandLineArgumentParserTest {
	private String[] args;
	private ConversationExporterConfiguration expectedConfiguration;
	private CommandLineArgumentParser commandLineArgumentParser;

	@Before
	public void initialize() {
		commandLineArgumentParser = new CommandLineArgumentParser();
	}

	public CommandLineArgumentParserTest(CommandLineArgumentParserTestCase testCase) {
		this.args = testCase.args;
		this.expectedConfiguration = testCase.expectedConfiguration;
	}

	@Parameterized.Parameters
	public static Collection<CommandLineArgumentParserTestCase> arguments() {

		List<CommandLineArgumentParserTestCase> testCases = new ArrayList<CommandLineArgumentParserTestCase>();

		ConversationExporterConfiguration config = new ConversationExporterConfiguration("in", "out");
		config.filterUser = false;
		config.username = null;
		config.filterKeyword = false;
		config.keyword = null;
		config.filterBlacklist = false;
		config.blacklist = null;
		config.filterNumbers = false;
		String[] case1 = { "in", "out" };
		testCases.add(new CommandLineArgumentParserTestCase(case1, config));

		config = new ConversationExporterConfiguration("in", "out");
		config.filterUser = true;
		config.username = "bob";
		config.filterKeyword = false;
		config.keyword = null;
		config.filterBlacklist = false;
		config.blacklist = null;
		config.filterNumbers = false;
		String[] case2 = { "in", "out", "-u", "bob" };
		testCases.add(new CommandLineArgumentParserTestCase(case2, config));

		config = new ConversationExporterConfiguration("in", "out");
		config.filterUser = false;
		config.username = null;
		config.filterKeyword = true;
		config.keyword = "pie";
		config.filterBlacklist = false;
		config.blacklist = null;
		config.filterNumbers = false;
		String[] case3 = { "in", "out", "-k", "pie" };
		testCases.add(new CommandLineArgumentParserTestCase(case3, config));
		
		config = new ConversationExporterConfiguration("in", "out");
		config.filterUser = false;
		config.username = null;
		config.filterKeyword = false;
		config.keyword = null;
		config.filterBlacklist = true;
		config.blacklist = "pie";
		config.filterNumbers = false;
		String[] case4 = { "in", "out", "-b", "pie" };
		testCases.add(new CommandLineArgumentParserTestCase(case4, config));
		
		config = new ConversationExporterConfiguration("in", "out");
		config.filterUser = false;
		config.username = null;
		config.filterKeyword = false;
		config.keyword = null;
		config.filterBlacklist = false;
		config.blacklist = null;
		config.filterNumbers = true;
		String[] case5 = { "in", "out", "-n" };
		testCases.add(new CommandLineArgumentParserTestCase(case5, config));
		
		config = new ConversationExporterConfiguration("in", "out");
		config.filterUser = true;
		config.username = "bob";
		config.filterKeyword = false;
		config.keyword = null;
		config.filterBlacklist = false;
		config.blacklist = null;
		config.filterNumbers = true;
		String[] case6 = { "in", "out", "-u", "bob", "-n" };
		testCases.add(new CommandLineArgumentParserTestCase(case6, config));
					
		return testCases;
	}

	@Test
	public void testCommandLineArgumentParser() throws Exception {

		ConversationExporterConfiguration configuration = this.commandLineArgumentParser
				.parseCommandLineArguments(args);

		assertEquals(expectedConfiguration.filterBlacklist, configuration.filterBlacklist);
		assertEquals(expectedConfiguration.blacklist, configuration.blacklist);

		assertEquals(expectedConfiguration.filterKeyword, configuration.filterKeyword);
		assertEquals(configuration.keyword, expectedConfiguration.keyword);

		assertEquals(expectedConfiguration.filterUser, configuration.filterUser);
		assertEquals(expectedConfiguration.username, configuration.username);

		assertEquals(expectedConfiguration.filterNumbers, configuration.filterNumbers);
	}
}

class CommandLineArgumentParserTestCase {

	public String[] args;
	public ConversationExporterConfiguration expectedConfiguration;

	public CommandLineArgumentParserTestCase(String[] args, ConversationExporterConfiguration expectedConfiguration) {
		this.args = args;
		this.expectedConfiguration = expectedConfiguration;
	}
}
