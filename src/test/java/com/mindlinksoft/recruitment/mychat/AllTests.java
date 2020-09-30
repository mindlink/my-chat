package com.mindlinksoft.recruitment.mychat;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ConversationExporterTests.class, FilterByKeywordCommandTests.class, FilterByUserCommandTests.class,
		HideBlacklistWordsCommandTests.class, CommandLineArgumentParserTests.class, HideNumbersCommandTests.class })
public class AllTests {

}
