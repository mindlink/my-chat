package com.mindlinksoft.recruitment.mychat.services;

import com.mindlinksoft.recruitment.mychat.models.ConversationExporterConfiguration;

/**
 * Service to understand the configuration the user supplied.
 */
public final class ConfigurationService {
	
    /**
     * Parses the given {@code configuration} into the exporter configuration.
     * @param arguments The configuration options.
     * @return The exporter configuration representing the options supplied.
     */
    public ConversationExporterConfiguration parseConfiguration(String[] configuration) {
    	
    	// TODO: Test for correct input
    	
        return new ConversationExporterConfiguration(configuration[0], configuration[1]);
    }
}
