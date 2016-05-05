package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the configuration for the exporter.
 */
public final class ConversationExporterConfiguration {
    /**
     * Stores the input file path.
     */
    private String inputFilePath;

    /**
     * Stores the output file path.
     */
    private String outputFilePath;
    
    /**
     * Stores the list of conversation filters.
     */
    private List<Filter> filters;
    
    /**
     * Marks whether overwrites are forced.
     */
    private boolean forceOverwrite;
    
    /**
     * Marks whether {@link Conversation} should include report.
     */
    private boolean addReport;

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param filters The list of conversation filters.
     * @param addReport This flag determines whether a conversation should include a report.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, List<Filter> filters, boolean addReport) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.filters = filters;
        this.addReport = addReport;
    }
    
    /**
     * Getter for report flag.
     * @return addReport.
     */
    public boolean useReport() {
    	return addReport;
    }
    
    /**
     * Setter for {@link forceOverwrite} flag.
     */
    public void forceOverwrite(boolean overwrite) {
    	forceOverwrite = overwrite;
    }
    
    /**
     * Getter for {@link forceOverwrite} flag.
     * @return forceOverwrite.
     */
    public boolean forceOverwrite() {  //Used to allow integration test in ConversationExporterTest to run un-interrupted.
    	return forceOverwrite;
    }
    
    /**
     * Getter for {@link inputFilePath} flag.
     * @return inputFilePath.
     */
    public String getInputFilePath() {
    	return inputFilePath;
    }
    
    /**
     * Getter for {@link outputFilePath} flag.
     * @return outputFilePath.
     */
    public String getOutputFilePath() {
    	return outputFilePath;
    }
    
    /**
     * Determines is filters are set.
     * @return Boolean True if filters are set.
     */
    public boolean hasFilters() {
    	return filters.size()>0;
    }
    
    /**
     * Getter for filters list.
     * @return The {@link filters} list.
     */
    public List<Filter> getFilters() {
    	return new ArrayList<Filter>(filters);
    }
}
