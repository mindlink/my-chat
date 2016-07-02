package com.mindlinksoft.recruitment.mychat;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CLIConfigurationTests.class, CommandLineArgumentParserTests.class, ConversationFilterFactoryTests.class,
		ConversationReaderTests.class, ConversationWriterTests.class, FilterBlacklistTests.class,
		FilterKeywordTests.class, FilterUsernameTests.class})
public class AllTests {

}
