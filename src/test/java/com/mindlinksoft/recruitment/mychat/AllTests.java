package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.FilterTests.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Runs all the JUnit tests that have been included in the list.
 */
@RunWith(Suite.class)
@SuiteClasses({
        ConversationExporterTests.class,
        UserFilterTests.class,
        KeywordFilterTests.class,
        BlacklistFilterTests.class,
        NumberFilterTests.class,
        ObfuscateIDFilterTests.class,
        ActivityReportTests.class })

public class AllTests {

}
