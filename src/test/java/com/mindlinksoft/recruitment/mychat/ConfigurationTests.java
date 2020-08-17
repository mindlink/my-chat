package com.mindlinksoft.recruitment.mychat;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ConfigurationTests {

    @Test
    public void testConfigurationParsesCorrectlyForRequiredArguments(){
        String[] arguments = {"chat.txt","chat.json"};
        ConversationExporterConfiguration configuration = new ConversationExporterConfiguration(arguments);

        assertEquals(configuration.inputFilePath,"chat.txt");
        assertEquals(configuration.outputFilePath,"chat.json");
        assertNull(configuration.user);
        assertNull(configuration.keyword);
        assertEquals(configuration.blacklist.size(),0);
    }
    @Test
    public void testConfigurationParsesCorrectlyForAllArgumentsGiven(){
        String[] arguments = {"chat.txt","chat.json","user=bob","keyword=pie","blacklist=[society,angus]"};
        ConversationExporterConfiguration configuration = new ConversationExporterConfiguration(arguments);

        assertEquals(configuration.inputFilePath,"chat.txt");
        assertEquals(configuration.outputFilePath,"chat.json");
        assertEquals(configuration.user,"bob");
        assertEquals(configuration.keyword,"pie");
        assertEquals(configuration.blacklist.size(),2);
        assertEquals(configuration.blacklist.get(0),"society");
        assertEquals(configuration.blacklist.get(1),"angus");
    }

    @Test
    public void testConfigurationParsesCorrectlyForSomeArgumentsGiven(){
        String[] arguments = {"chat.txt","chat.json","keyword=pie","blacklist=[society,angus]"};
        ConversationExporterConfiguration configuration = new ConversationExporterConfiguration(arguments);

        assertEquals(configuration.inputFilePath,"chat.txt");
        assertEquals(configuration.outputFilePath,"chat.json");
        assertEquals(configuration.keyword,"pie");
        assertEquals(configuration.blacklist.size(),2);
        assertEquals(configuration.blacklist.get(0),"society");
        assertEquals(configuration.blacklist.get(1),"angus");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConfigurationFailsForInvalidArgument(){
        String[] arguments = {"chat.txt","chat.json","username=bob"};
        ConversationExporterConfiguration configuration = new ConversationExporterConfiguration(arguments);
    }
}
