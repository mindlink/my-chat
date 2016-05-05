package com.mindlinksoft.recruitment.mychat;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)

@SuiteClasses({ 
	ConversationExporterTest.class, 
	CommandLineArgumentParserTest.class, 
	MessageTest.class, 
	UserFilterTest.class, 
	WordFilterTest.class, 
	RedactFilterTest.class, 
	ReportTest.class, 
	ObfuscateUserFilterTest.class 
})

public class AllTests {}
