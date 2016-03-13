package com.mindlinksoft.recruitment.mychat.services;

import com.mindlinksoft.recruitment.mychat.models.ConversationExporterConfiguration;

/**
 * Service to understand the configuration the user supplied.
 */
public final class ConfigurationService {
	
    /**
     * Parses the given {@code configuration} into the exporter configuration.
     * 
     * @param configuration The configuration options.
     * @return The exporter configuration representing the options supplied.
     * @throws IllegalArgumentException When there is a problem with the configuration passed in.
     */
    public ConversationExporterConfiguration parseConfiguration(String[] configuration) throws IllegalArgumentException {
    	
    	// Check to make sure configuration passed in is valid.
    	if (configuration.length < 2) {
    		throw new IllegalArgumentException("There needs to be atleast an input path and output path specified");
    	}
    	
    	// If it's all valid then return a ConversationExporterConfiguration object that
    	// represents the options the user specified.
        return new ConversationExporterConfiguration(configuration[0], configuration[1]);
    }
}
