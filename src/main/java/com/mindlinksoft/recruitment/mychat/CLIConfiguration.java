package com.mindlinksoft.recruitment.mychat;

import java.awt.image.SinglePixelPackedSampleModel;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Represents the configuration for the exporter.
 */
public final class CLIConfiguration {

    private String inputFilePath;
    private String outputFilePath;
//    private Map<String, String> singlevaluedOptions;
//    private Map<String, String[]> multivaluedOptions;
    private List<ConversationFilter> filters;
    private Set<String> flags;
      
    
    public CLIConfiguration() {
    	init();
    }
    
    public CLIConfiguration(String inputFilePath, String outputFilePath) {
    	this.setInputFilePath(inputFilePath);
    	this.setOutputFilePath(outputFilePath);
    	init();
    }
    
    private void init() {
//    	this.singlevaluedOptions = new HashMap<String, String>();
//    	this.multivaluedOptions = new HashMap<String, String[]>();
    	this.filters = new LinkedList<ConversationFilter>();
    	this.flags = new TreeSet<String>();
    }
    
    public String getInputFilePath() {
    	
    	return inputFilePath;
    }
    
    /**
     * {@link http://stackoverflow.com/a/35452697}
     * */
    public void setInputFilePath(String inputFilePath) {
    	
    	try { Paths.get(inputFilePath); }
    	catch (InvalidPathException |  NullPointerException ex) {
    		throw new InvalidPathException(inputFilePath, "Invalid input"
    				+ "file path provided.");
        }
    	
    	this.inputFilePath = inputFilePath;
    }
    
    public String getOutputFilePath() {
    	
    	return outputFilePath;
    }
    
    /**
     * {@link http://stackoverflow.com/a/35452697}
     * */
    public void setOutputFilePath(String outputFilePath) {
    	
    	try { Paths.get(outputFilePath); }
    	catch (InvalidPathException |  NullPointerException ex) {
    		throw new InvalidPathException(outputFilePath, "Invalid output"
    				+ "file path provided.");
        }
    	
    	this.outputFilePath = outputFilePath;
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
    
//    
//    public String getOption(String key) {
//    	return null;
//
//    }
//    
//    public void setOption(String key, String option) {
//    	
//    }
//    
//
//    public String[] getMultivaluedOption(String key) {
//    	return null;
//
//    }
//   
//    public void setMultivaluedOption(String key, String[] option) {
//    	
//    }
//    
}
