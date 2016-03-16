package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.mindlinksoft.recruitment.mychat.models.ConfigurationOptions;
import com.mindlinksoft.recruitment.mychat.models.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.services.ConfigurationService;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConfigurationServiceTests {
    
    /**
     * Tests that the {@link ConfigurationService} creates the proper
     * {@link ConversationExporterConfiguration} object.
     */
    @Test
    public void testCreatesRightConversationExporterConfiguration() {
    	String[] parameters =  {
    			"-" + ConfigurationOptions.INPUT.getValue(), "chat.txt",
    			"-" + ConfigurationOptions.OUTPUT.getValue(), "chat.json",
    			"-" + ConfigurationOptions.USER.getValue(), "bob",
    			"-" + ConfigurationOptions.KEYWORD.getValue(), "pie",
    			"-" + ConfigurationOptions.BLACKLIST.getValue(), "hell yes, society, pie"};
    	
    	ConfigurationService configService = new ConfigurationService();
    	ConversationExporterConfiguration config = configService.parseConfiguration(parameters);
    	
    	assertEquals(config.getInputFilePath(), "chat.txt");
    	assertEquals(config.getOutputFilePath(), "chat.json");
    	assertEquals(config.getUser(), "bob");
    	assertEquals(config.getKeyword(), "pie");
	
    	List<String> blacklist = new ArrayList<String>();
    	blacklist.add("hell yes");
    	blacklist.add("society");
    	blacklist.add("pie");
    	
    	assertEquals(config.getBlacklist(), blacklist);
    }
	
	/**
     * Tests that when you do not supply an input or an output it will return null.
     */
    @Test
    public void testProvidingInsufficientParameters() {
    	ConfigurationService configService = new ConfigurationService();
    	ConversationExporterConfiguration config = configService.parseConfiguration(new String[] {});
  	
    	assertEquals(config, null);
    	
    	config = configService.parseConfiguration(new String[] {"-" + ConfigurationOptions.INPUT.getValue(), "chat.txt"});
    	assertEquals(config, null);
    	
    	config = configService.parseConfiguration(new String[] {"-" + ConfigurationOptions.OUTPUT.getValue(), "chat.json"});
    	assertEquals(config, null);
    }
}
