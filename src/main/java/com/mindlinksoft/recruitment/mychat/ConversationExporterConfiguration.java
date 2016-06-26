package com.mindlinksoft.recruitment.mychat;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the configuration for the exporter.
 * Effectively a wrapper for the Java Collections Map interface.
 */
public final class ConversationExporterConfiguration {
    private Map<String, String> m;
      
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath) {
    	initHashMap();
    	
    	m.put("inputFilePath", inputFilePath);
    	m.put("outputFilePath", outputFilePath);
    	
    }
   
    private void initHashMap() {
    	m = new HashMap<String, String>();
    	
    }
    
    public String get(String key) {
    	return m.get(key);
    }
    
    public String put(String key, String value) {
    	return m.put(key, value);
    }
}
