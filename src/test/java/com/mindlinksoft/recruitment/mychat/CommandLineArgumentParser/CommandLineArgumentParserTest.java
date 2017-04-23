package com.mindlinksoft.recruitment.mychat.CommandLineArgumentParser;

import static org.junit.Assert.assertTrue;

import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.ParseException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mindlinksoft.recruitment.mychat.CommandLineArgumentParser.CommandLineArgumentParser;
import com.mindlinksoft.recruitment.mychat.conversation.ConversationExporterConfiguration;


public class CommandLineArgumentParserTest {
	
	private static CommandLineArgumentParser commandLineArgumentParser;
	
	@BeforeClass
	public static void Setup()
	{
		commandLineArgumentParser = new CommandLineArgumentParser();
	}
	
	@Test(expected=ParseException.class)
	public void parseCommandLineArguments_NoArguments_ThrowException() throws ParseException
	{
		commandLineArgumentParser.parseCommandLineArguments(null);
	}
	
	@Test(expected=MissingOptionException.class)
	public void parseCommandLineArguments_MandatoryArgumentMissing_ThrowException() throws ParseException
	{
		String[] arg ={"-outputFilePath","C:\\chatJSON.txt","-keyword","pie","-blacklistedwords","pie","Hello","-hideccandphoneno","-obfuscateuserid"};
		commandLineArgumentParser.parseCommandLineArguments(arg);
	}
	
	@Test
	public void parseCommandLineArguments_CurrectArguments_ConfigurationCreated() throws ParseException
	{
		String[] arg ={"-inputFilePath","C:\\chat.txt","-outputFilePath","C:\\chatJSON.txt","-keyword","pie","-blacklistedwords","pie,Hello","-hideccandphoneno","-obfuscateuserid"};
		ConversationExporterConfiguration configuration = commandLineArgumentParser.parseCommandLineArguments(arg);
		Assert.assertEquals("C:\\chat.txt", configuration.getInputFilePath());
		Assert.assertEquals("C:\\chatJSON.txt", configuration.getOutputFilePath());
		Assert.assertEquals("pie", configuration.getKeyword());
		Assert.assertArrayEquals(new String[]{"pie","Hello"},configuration.getBlacklistedWords());
		Assert.assertTrue(configuration.isHideCCandPhoneno());
		Assert.assertTrue(configuration.isObfuscateUserID());

	}
}
