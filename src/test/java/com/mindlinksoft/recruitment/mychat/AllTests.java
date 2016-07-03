package com.mindlinksoft.recruitment.mychat;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CLIConfigurationTests.class, CommandLineArgumentParserTests.class, ConversationFilterApplierTests.class,
		ConversationFilterFactoryTests.class, ConversationReaderTests.class, ConversationWriterTests.class,
		FilterBlacklistTests.class, FilterKeywordTests.class, FilterObfuscateUsernamesTests.class, 
		FilterReportTests.class, FilterUsernameTests.class, MainCLITests.class })
public class AllTests {

}
