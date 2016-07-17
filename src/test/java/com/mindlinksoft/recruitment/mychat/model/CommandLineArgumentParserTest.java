package com.mindlinksoft.recruitment.mychat.model;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mindlinksoft.recruitment.mychat.enums.CommandLineArgument;
import com.mindlinksoft.recruitment.mychat.model.CommandLineArgumentParser;

public class CommandLineArgumentParserTest {

	private CommandLineArgumentParser parser_;
	private Map<CommandLineArgument, String> map_;

	@Mock
	private CommandLine cmd_;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		parser_ = new CommandLineArgumentParser();
		map_ = new HashMap<>();
	}

	@Test
	public void testNullIsNull() {
		ConversationExporterConfiguration config = parser_.parseCommandLineArguments(null);
		Assert.assertNull(config);
	}

	@Test
	public void testEmptyIsNull() {
		ConversationExporterConfiguration config = parser_.parseCommandLineArguments(new String[] {});
		Assert.assertNull(config);
	}

	@Test
	public void testNoCharOptionsIsNull() {
		ConversationExporterConfiguration config = parser_
				.parseCommandLineArguments(new String[] { "chat.txt", "chat.json" });
		Assert.assertNull(config);
	}

	@Test
	public void testNoDashes() {
		ConversationExporterConfiguration config = parser_
				.parseCommandLineArguments(new String[] { "i", "a", "o", "b" });
		Assert.assertNull(config);
	}

	@Test
	public void testInputNoOutput() {
		ConversationExporterConfiguration config = parser_.parseCommandLineArguments(new String[] { "-i", "a" });
		Assert.assertNull(config);
	}

	@Test
	public void testInputNoOutputValue() {
		ConversationExporterConfiguration config = parser_.parseCommandLineArguments(new String[] { "-i", "a", "-o" });
		Assert.assertNull(config);
	}

	@Test
	public void testOutputNoInput() {
		ConversationExporterConfiguration config = parser_.parseCommandLineArguments(new String[] { "-o", "a" });
		Assert.assertNull(config);
	}

	@Test
	public void testOutputNoInputValue() {
		ConversationExporterConfiguration config = parser_.parseCommandLineArguments(new String[] { "-i", "-o", "a" });
		Assert.assertNull(config);
	}

	@Test
	public void testInputOutput() {
		ConversationExporterConfiguration config = parser_
				.parseCommandLineArguments(new String[] { "-i", "a", "-o", "b" });
		Assert.assertEquals(2, config.getSize());
		Assert.assertEquals("a", config.getInputFile());
		Assert.assertEquals("b", config.getOutputFile());
	}

	@Test
	public void testAddCommandLineArgumentMatch() {
		CommandLineArgument arg = CommandLineArgument.INPUT_FILE;
		Assert.assertTrue(arg.isValueRequired());
		Mockito.when(cmd_.hasOption(arg.getChar())).thenReturn(true);
		Mockito.when(cmd_.getOptionValues(arg.getChar())).thenReturn(new String[] { "bob" });
		parser_.addCommandLineArgument(map_, cmd_, arg);
		Assert.assertEquals(1, map_.size());
		Assert.assertEquals("bob", map_.get(arg));
	}

	@Test(expected = InvalidParameterException.class)
	public void testAddCommandLineArgumentMatchDuplicate() {
		CommandLineArgument arg = CommandLineArgument.INPUT_FILE;
		Assert.assertTrue(arg.isValueRequired());
		Mockito.when(cmd_.hasOption(arg.getChar())).thenReturn(true);
		Mockito.when(cmd_.getOptionValues(arg.getChar())).thenReturn(new String[] { "bob" });
		map_.put(arg, "jack");
		parser_.addCommandLineArgument(map_, cmd_, arg);
	}

	@Test
	public void testAddCommandLineArgumentMatchMultiple() {
		CommandLineArgument arg = CommandLineArgument.INPUT_FILE;
		Assert.assertTrue(arg.isValueRequired());
		Mockito.when(cmd_.hasOption(arg.getChar())).thenReturn(true);
		Mockito.when(cmd_.getOptionValues(arg.getChar())).thenReturn(new String[] { "bob", "john" });
		parser_.addCommandLineArgument(map_, cmd_, arg);
		Assert.assertEquals(1, map_.size());
		Assert.assertEquals("bob john", map_.get(arg));
	}

	@Test
	public void testAddCommandLineArgumentNoMatchNotRequired() {
		CommandLineArgument arg = CommandLineArgument.FILTER_USER;
		Assert.assertFalse(arg.isRequired());
		Assert.assertTrue(arg.isValueRequired());
		Mockito.when(cmd_.hasOption(arg.getChar())).thenReturn(false);
		parser_.addCommandLineArgument(map_, cmd_, arg);
		Assert.assertTrue(map_.isEmpty());
	}

	@Test(expected = InvalidParameterException.class)
	public void testAddCommandLineArgumentNoMatchRequired() {
		CommandLineArgument arg = CommandLineArgument.INPUT_FILE;
		Assert.assertTrue(arg.isValueRequired());
		Assert.assertTrue(arg.isRequired());
		Mockito.when(cmd_.hasOption(arg.getChar())).thenReturn(false);
		parser_.addCommandLineArgument(map_, cmd_, arg);
	}

	@Test
	public void testAddCommandLineArgumentNoValueRequired() {
		CommandLineArgument arg = CommandLineArgument.HIDE_CREDIT_CARD_AND_PHONE;
		Assert.assertFalse(arg.isValueRequired());
		Mockito.when(cmd_.hasOption(arg.getChar())).thenReturn(true);
		Mockito.when(cmd_.getOptionValues(arg.getChar())).thenReturn(null);
		parser_.addCommandLineArgument(map_, cmd_, arg);
		Assert.assertEquals(1, map_.size());
		Assert.assertEquals("", map_.get(arg));
	}

}
