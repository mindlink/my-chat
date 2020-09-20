package com.mindlinksoft.recruitment.mychat;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	FilterUserTest.class,
	FilterWordTest.class,
	BlackListTest.class,
	ObfuscateUserTest.class,
	ObfuscateCreditCardTest.class,
	CommandLineArgumentParserTest.class
})

/**
 * Use this test suite to run all unit test, rather than
 * running them induvidually.
 * 
 * @author Mohamed Yusuf
 *
 */
public class TestRunner {

}
