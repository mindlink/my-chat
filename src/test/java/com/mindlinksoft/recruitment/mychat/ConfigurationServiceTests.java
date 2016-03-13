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
    public void testCreatesRightConversationExporterConfiguration() throws Exception {
    	String[] parameters =  {"chat.txt", "chat.json"};
    	ConfigurationService configService = new ConfigurationService();
    	ConversationExporterConfiguration config = configService.parseConfiguration(parameters);
    	
    	assertEquals(config.getInputFilePath(), "chat.txt");
    	assertEquals(config.getOutputFilePath(), "chat.json");
    }
	
	/**
     * Tests that providing insufficient configuration parameters is handled.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testProvidingInsufficientParameters() throws Exception {
    	ConfigurationService configService = new ConfigurationService();
    	configService.parseConfiguration(new String[] {});
    }
}
