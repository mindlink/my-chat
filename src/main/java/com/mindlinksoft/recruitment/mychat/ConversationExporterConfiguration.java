package com.mindlinksoft.recruitment.mychat;

import java.awt.image.SinglePixelPackedSampleModel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Represents the configuration for the exporter.
 */
public final class ConversationExporterConfiguration {

    private String inputFilePath;
    private String outputFilePath;
//    private Map<String, String> singlevaluedOptions;
//    private Map<String, String[]> multivaluedOptions;
    private List<ConversationFilter> filters;
    private Set<String> flags;
      
    
    public ConversationExporterConfiguration() {
    	init();
    }
    
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath) {
    	this.inputFilePath = inputFilePath;
    	this.outputFilePath = outputFilePath;
    	init();
    }
    
    private void init() {
//    	this.singlevaluedOptions = new HashMap<String, String>();
//    	this.multivaluedOptions = new HashMap<String, String[]>();
    	this.filters = new LinkedList<ConversationFilter>();
    	this.flags = new TreeSet<String>();
    }
    
    public Set<String> getFlags() {
    	return null;
    }
    
    public void setFlag(String key) {
    	
    }
    
    public void addFilter(ConversationFilter filter) {
    	
    }
    
    public void removeFilter(ConversationFilter filter) {
    	
    }
    
    
    public String getOption(String key) {
    	return null;

    }
    
    public void setOption(String key, String option) {
    	
    }
    

    public String[] getMultivaluedOption(String key) {
    	return null;

    }
   
    public void setMultivaluedOption(String key, String[] option) {
    	
    }
    
}
