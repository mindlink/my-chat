package com.mindlinksoft.recruitment.mychat;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        ConversationExporterTests.class,
        UserFilterTests.class,
        KeywordFilterTests.class,
        BlacklistFilterTests.class })

public class AllTests {

}
