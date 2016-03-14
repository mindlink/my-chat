package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

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
    	String[] parameters =  {"-i", "chat.txt", "-o", "chat.json", "-u", "bob"};
    	ConfigurationService configService = new ConfigurationService();
    	ConversationExporterConfiguration config = configService.parseConfiguration(parameters);
    	
    	assertEquals(config.getInputFilePath(), "chat.txt");
    	assertEquals(config.getOutputFilePath(), "chat.json");
    	assertEquals(config.getUser(), "bob");
    }
	
	/**
     * Tests that when you do not supply an input or an output it will return null.
     */
    @Test
    public void testProvidingInsufficientParameters() {
    	ConfigurationService configService = new ConfigurationService();
    	ConversationExporterConfiguration config = configService.parseConfiguration(new String[] {});
  	
    	assertEquals(config, null);
    	
    	config = configService.parseConfiguration(new String[] {"-i", "chat.txt"});
    	assertEquals(config, null);
    	
    	config = configService.parseConfiguration(new String[] {"-o", "chat.json"});
    	assertEquals(config, null);
    }
}
