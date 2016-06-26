package com.mindlinksoft.recruitment.mychat;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CommandLineArgumentParserTests.class, ConversationExporterTests.class, ConversationTests.class,
		MainCLITests.class })
public class AllTests {

}
