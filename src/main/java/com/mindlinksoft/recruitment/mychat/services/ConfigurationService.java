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
    	if (configuration.length == 2) {
    		return new ConversationExporterConfiguration(configuration[0], configuration[1]);
    	} else if (configuration.length > 2) {
    		return new ConversationExporterConfiguration(configuration[0], configuration[1], configuration[2]);
    	} else {
    		throw new IllegalArgumentException("There needs to be atleast an input path and output path specified");
    	}
        
    }
}
