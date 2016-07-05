package com.mindlinksoft.recruitment.mychat;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents the configuration for the CLI application. Responsible for 
 * encapsulating and modelling the application configuration data.
 * 
 */
public final class CLIConfiguration {

	private final static Logger LOGGER = Logger.getLogger("com.mindlinksoft.recruitment.mychat");
	
    private String inputFilePath;
    private String outputFilePath;
    private List<ConversationFilter> filters;
    
    public CLIConfiguration(String inputFilePath, String outputFilePath) {
    	LOGGER.log(Level.INFO, "Initializing configuration with input file '" + 
    inputFilePath + "' and output file '" + outputFilePath +"'");
    	this.setInputFilePath(inputFilePath);
    	this.setOutputFilePath(outputFilePath);
    	init();
    }
    
    /**
     * Helper method to initialize data structures, reusable across different
     * constructors.
     * */
    private void init() {
    	this.filters = new LinkedList<ConversationFilter>();
    }
    
    /**
     * @return the input file path stored in the configuration
     * */
    public String getInputFilePath() {
    	
    	return inputFilePath;
    }
    
    /**
     * Sets the input file path for the configuration. Verifies file path is 
     * valid against host file system (see {@link http://stackoverflow.com/a/35452697})
     * 
     * @param inputFilePath the new value for the input file path in the configuration
     * */
    public void setInputFilePath(String inputFilePath) {
    	
    	try { Paths.get(inputFilePath); }
    	catch (InvalidPathException |  NullPointerException ex) {
    		throw new InvalidPathException(inputFilePath, "Invalid input"
    				+ "file path provided.");
        }
    	
    	this.inputFilePath = inputFilePath;
    }
    
    /**
     * @return the output file path stored in the configuration.
     * */
    public String getOutputFilePath() {
    	
    	return outputFilePath;
    }
    
    /**
     * Sets the input file path for the configuration. Verifies file path is 
     * valid against host file system (see {@link http://stackoverflow.com/a/35452697})
     * 
     * @param outputFilePath the new value for the output file path stored in the
     * configuration
     * */
    public void setOutputFilePath(String outputFilePath) {
    	
    	try { Paths.get(outputFilePath); }
    	catch (InvalidPathException |  NullPointerException ex) {
    		throw new InvalidPathException(outputFilePath, "Invalid output"
    				+ "file path provided.");
        }
    	
    	this.outputFilePath = outputFilePath;
    }
    
    /**
     * Adds a conversation filter to the configuration.
     * @param filter the filter to add
     * */
    public void addFilter(ConversationFilter filter) {
    	filters.add(filter);
    	
    }
    
    /**
     * @return a copy of the set of filters for read access.
     * */
    public List<ConversationFilter> getFilters() {
    	return new LinkedList<ConversationFilter>(filters);
    	
    }
    
}
