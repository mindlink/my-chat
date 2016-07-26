package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class CommandLineArgumentParserTest {

	@Test
	public void testParseCommandLineArguments() {
		String [] testArgs = new String [12];
		testArgs [0] = "-i";
		testArgs [1] = "D:\\Development\\Git\\my-chat\\chat.txt";
		testArgs [2] = "-o";
		testArgs [3] = "C:\\Users\\tomba\\Desktop\\output.txt";
		testArgs [4] = "-u";
		testArgs [5] = "angus";
		testArgs [6] = "-k";
		testArgs [7] = "Angus";
		testArgs [8] = "-b";
		testArgs [9] = "C:\\Users\\tomba\\Desktop\\blacklist.txt";
		testArgs [10] = "-p";
		testArgs [11] = "-P";
		
		CommandLineArgumentParser c = new CommandLineArgumentParser();
		c.match(testArgs);
		
		Assert.assertEquals(c.hasInput, true);
		Assert.assertEquals(c.hasOutput, true);
		Assert.assertEquals(c.hasUsername, true);
		Assert.assertEquals(c.hasKeyword, true);
		Assert.assertEquals(c.hasBlackList, true);
		Assert.assertEquals(c.rConfidential, true);
		Assert.assertEquals(c.rUsername, true);
		
		Assert.assertEquals(c.inputFilePath, testArgs [1]);
		Assert.assertEquals(c.outputFilePath, testArgs [3]);	
		Assert.assertEquals(c.blackList, testArgs [9]);	

	}

}
