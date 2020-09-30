package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.UnrecognizedOptionException;
import org.junit.Before;
import org.junit.Test;

import com.mindlinksoft.recruitment.mychat.commands.FilterByKeywordCommand;
import com.mindlinksoft.recruitment.mychat.commands.FilterByUserCommand;
import com.mindlinksoft.recruitment.mychat.commands.HideBlacklistWordsCommand;
import com.mindlinksoft.recruitment.mychat.commands.IConversationExportCommand;

public class CommandLineArgumentParserTests {

	private CommandLineArgumentParser cmdParser;
	Random r;
	
	@Test
	public void parseCommandLineArguments_noOptions_parsesCorrectly() throws ParseException {
		String inputFilePath =  UUID.randomUUID().toString();
		String outputFilePath =  UUID.randomUUID().toString();
		
		ConversationExporterConfiguration config = cmdParser.parseCommandLineArguments(
								new String[]{inputFilePath, outputFilePath});

		assertEquals(inputFilePath, config.getInputFilePath());
		assertEquals(outputFilePath, config.getOutputFilePath());
		assertEquals(0, config.getListOfCommands().size());

	}
	
	@Test
	public void parseCommandLineArguments_allOptions_parsesCorrectly() throws ParseException {
		String inputFilePath =  UUID.randomUUID().toString();
		String outputFilePath =  UUID.randomUUID().toString();
		String userFilterOption = "-u";
		String userFilter = "user";
		String keywordFilterOption = "-k";
		String keywordFilter = "keyword";
		String hideWordOption = "-b";
		String hideWord = "word";
		
		ConversationExporterConfiguration config = cmdParser.parseCommandLineArguments(
								new String[]{inputFilePath, outputFilePath, 
										userFilterOption, userFilter, 
										keywordFilterOption, keywordFilter,
										hideWordOption, hideWord});

		assertEquals(inputFilePath, config.getInputFilePath());
		assertEquals(outputFilePath, config.getOutputFilePath());
		
		Collection<IConversationExportCommand> commands = config.getListOfCommands();
		assertEquals(3, commands.size());
		
		// check class type of commands
		ArrayList<Class> classes = new ArrayList<Class>();
		commands.forEach(c -> classes.add(c.getClass()));

		assertTrue(classes.contains(FilterByKeywordCommand.class));
		assertTrue(classes.contains(FilterByUserCommand.class));
		assertTrue(classes.contains(HideBlacklistWordsCommand.class));
	}

	 @Test(expected = UnrecognizedOptionException.class)
	public void parseCommandLineArguments_badCommand_throwsParseException() throws ParseException {
		String inputFilePath =  UUID.randomUUID().toString();
		String outputFilePath =  UUID.randomUUID().toString();
		String badFilterOption = "-x";

		// this should throw the exception
		cmdParser.parseCommandLineArguments(
								new String[]{inputFilePath, outputFilePath, 
										badFilterOption});
	}

	 @Test(expected = ParseException.class)
	public void parseCommandLineArguments_noArgs_throwsException() throws ParseException {
		 cmdParser.parseCommandLineArguments(
					new String[0]);
	}

	 @Test(expected = ParseException.class)
	public void parseCommandLineArguments_missingUserArgs_throwsException() throws ParseException {
		String inputFilePath =  UUID.randomUUID().toString();
		String outputFilePath =  UUID.randomUUID().toString();
		cmdParser.parseCommandLineArguments(
						new String[] {inputFilePath, outputFilePath, "-u"});
	}

	 @Test(expected = ParseException.class)
	public void parseCommandLineArguments_missingKeywordArgs_throwsException() throws ParseException {
		String inputFilePath =  UUID.randomUUID().toString();
		String outputFilePath =  UUID.randomUUID().toString();
		cmdParser.parseCommandLineArguments(
						new String[] {inputFilePath, outputFilePath, "-k"});
	}

	 @Test(expected = ParseException.class)
	public void parseCommandLineArguments_missingWordArgs_throwsException() throws ParseException {
		String inputFilePath =  UUID.randomUUID().toString();
		String outputFilePath =  UUID.randomUUID().toString();
		cmdParser.parseCommandLineArguments(
						new String[] {inputFilePath, outputFilePath, "-b"});
	}
	
	@Test
	public void parseCommandLineArguments_multipleWordsToHide_parsesCorrectly() throws ParseException {
		String inputFilePath =  UUID.randomUUID().toString();
		String outputFilePath =  UUID.randomUUID().toString();
		String hideWordOption = "-b";
		String hideWord = "word";
		String hideWord2 = "word2";
		String hideWord3 = "word3";
		String hideWord4 = "word4";
		String hideWord5 = "word5";
		String hideWord6 = "word6";
		String hideWord7= "word7";
		String hideWord8 = "word8";
		String hideWord9 = "word9";
		String hideWord10 = "word10";
		
		ConversationExporterConfiguration config = cmdParser.parseCommandLineArguments(
								new String[]{inputFilePath, outputFilePath,
										hideWordOption, hideWord, hideWord2, hideWord3,
										hideWord4, hideWord5, hideWord6, hideWord7,
										hideWord8, hideWord9, hideWord10});

		assertEquals(inputFilePath, config.getInputFilePath());
		assertEquals(outputFilePath, config.getOutputFilePath());
		
		Collection<IConversationExportCommand> commands = config.getListOfCommands();
		assertEquals(1, commands.size());
		
		IConversationExportCommand[] commandsArr = new IConversationExportCommand[1];
        commands.toArray(commandsArr);
        
		IConversationExportCommand command = commandsArr[0];
		assertEquals(HideBlacklistWordsCommand.class, command.getClass());
		
		// check all words to hide went through
		HideBlacklistWordsCommand hideCommand = (HideBlacklistWordsCommand) command;
		
		String[] returnedWords = hideCommand.getBlacklistWords();

		assertEquals(10, returnedWords.length);;
		assertEquals(hideWord, returnedWords[0]);;
		assertEquals(hideWord2, returnedWords[1]);;
		assertEquals(hideWord3, returnedWords[2]);;
		assertEquals(hideWord4, returnedWords[3]);;
		assertEquals(hideWord5, returnedWords[4]);;
		assertEquals(hideWord6, returnedWords[5]);;
		assertEquals(hideWord7, returnedWords[6]);;
		assertEquals(hideWord8, returnedWords[7]);;
		assertEquals(hideWord9, returnedWords[8]);;
		assertEquals(hideWord10, returnedWords[9]);;
	}	
	@Test
	public void parseCommandLineArguments_moreThanMaxWordsToHide_parsesOnlyMaxWords() throws ParseException {
		String inputFilePath =  UUID.randomUUID().toString();
		String outputFilePath =  UUID.randomUUID().toString();
		String hideWordOption = "-b";
		String hideWord = "word";
		String hideWord2 = "word2";
		String hideWord3 = "word3";
		String hideWord4 = "word4";
		String hideWord5 = "word5";
		String hideWord6 = "word6";
		String hideWord7= "word7";
		String hideWord8 = "word8";
		String hideWord9 = "word9";
		String hideWord10 = "word10";
		String hideWord11 = "word11";
		
		ConversationExporterConfiguration config = cmdParser.parseCommandLineArguments(
								new String[]{inputFilePath, outputFilePath,
										hideWordOption, hideWord, hideWord2, hideWord3,
										hideWord4, hideWord5, hideWord6, hideWord7,
										hideWord8, hideWord9, hideWord10, hideWord11});

		assertEquals(inputFilePath, config.getInputFilePath());
		assertEquals(outputFilePath, config.getOutputFilePath());
		
		Collection<IConversationExportCommand> commands = config.getListOfCommands();
		assertEquals(1, commands.size());
		
		IConversationExportCommand[] commandsArr = new IConversationExportCommand[1];
        commands.toArray(commandsArr);
        
		IConversationExportCommand command = commandsArr[0];
		assertEquals(HideBlacklistWordsCommand.class, command.getClass());
		
		// check all words to hide went through
		HideBlacklistWordsCommand hideCommand = (HideBlacklistWordsCommand) command;
		
		String[] returnedWords = hideCommand.getBlacklistWords();

		assertEquals(10, returnedWords.length);;
	}
	
	/**
	 * Sets up before each test
	 */
	@Before
    public void setUp() {
		r = new Random();
        cmdParser = new CommandLineArgumentParser();
    }
    

}
