package com.mindlinksoft.recruitment.mychat;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the configuration for the exporter.
 * Effectively a wrapper for the Java Collections Map interface.
 */
public final class ConversationExporterConfiguration {
    private Map<Character, String> m;
      
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath) {
    	initHashMap();
    	
    	m.put('i', inputFilePath);
    	m.put('o', outputFilePath);
    	
    }
   
    private void initHashMap() {
    	m = new HashMap<Character, String>();
    	
    }
    
    public String get(Character key) {
    	return m.get(key);
    }
    
    public String put(Character key, String value) {
    	return m.put(key, value);
    }
}
