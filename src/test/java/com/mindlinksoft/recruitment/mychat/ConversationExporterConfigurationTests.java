package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.mindlinksoft.recruitment.mychat.model.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.util.CommandLineArgumentParser;

public class ConversationExporterConfigurationTests {

	/**
	 * Tests that the correct conversation exporter configuration is created when no
	 * optional arguments are used.
	 */
	@Test
	public void testConversationConfigurationBasic() throws Exception {
		String[] arguments = { "chat.txt", "chat.json" };
		ConversationExporterConfiguration configuration = new CommandLineArgumentParser()
				.parseCommandLineArguments(arguments);
		assertEquals(configuration.getInputFilePath(), arguments[0]);
		assertEquals(configuration.getOutputFilePath(), arguments[1]);
		assertFalse(configuration.isUserActivityOn());
		assertFalse(configuration.isHidePersonalDeatilsOn());
		assertFalse(configuration.isObfuscateUsersOn());
		assertEquals(configuration.getUserFilter(), "");
		assertEquals(configuration.getKeywordFilter(), "");
		assertEquals(configuration.getBlacklist(), "");
	}

	/**
	 * Tests that the correct conversation exporter configuration is created when
	 * the string-based filtering optional arguments are used.
	 */
	@Test
	public void testConversationConfigurationFilteringOptions() throws Exception {
		String[] arguments = { "chat.txt", "chat.json", "-u", "bob", "-k", "thanks", "-b", "are" };
		ConversationExporterConfiguration configuration = new CommandLineArgumentParser()
				.parseCommandLineArguments(arguments);
		assertEquals(configuration.getInputFilePath(), arguments[0]);
		assertEquals(configuration.getOutputFilePath(), arguments[1]);
		assertEquals(configuration.getUserFilter(), arguments[3]);
		assertEquals(configuration.getKeywordFilter(), arguments[5]);
		assertEquals(configuration.getBlacklist(), arguments[7]);
		assertFalse(configuration.isUserActivityOn());
		assertFalse(configuration.isHidePersonalDeatilsOn());
		assertFalse(configuration.isObfuscateUsersOn());
	}

	/**
	 * Tests that the correct conversation exporter configuration is created when
	 * all optional arguments are used.
	 */
	@Test
	public void testConversationConfigurationAllOptions() throws Exception {
		String[] arguments = { "chat.txt", "chat.json", "-u", "bob", "-k", "thanks", "-b", "are", "-h", "-o", "-a" };
		ConversationExporterConfiguration configuration = new CommandLineArgumentParser()
				.parseCommandLineArguments(arguments);
		assertEquals(configuration.getInputFilePath(), arguments[0]);
		assertEquals(configuration.getOutputFilePath(), arguments[1]);
		assertEquals(configuration.getUserFilter(), arguments[3]);
		assertEquals(configuration.getKeywordFilter(), arguments[5]);
		assertEquals(configuration.getBlacklist(), arguments[7]);
		assertTrue(configuration.isUserActivityOn());
		assertTrue(configuration.isHidePersonalDeatilsOn());
		assertTrue(configuration.isObfuscateUsersOn());
	}

}
