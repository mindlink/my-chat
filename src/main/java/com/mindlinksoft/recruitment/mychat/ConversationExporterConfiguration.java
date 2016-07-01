package com.mindlinksoft.recruitment.mychat;

import java.util.List;

/**
 * Represents the configuration for the exporter.
 */
public final class ConversationExporterConfiguration {

	// public String export;
	public String inputFilePath;
	public String outputFilePath;
	// public String filter;
	public String user;
	public String keyword; 
	public List<String> blacklist;

	/**
	 * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
	 * @param inputFilePath The input file path.
	 * @param outputFilePath The output file path.
	 *
	 */

	public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, String user, String keyword, List<String> blacklist) {

		//this.export = export;
		this.inputFilePath = inputFilePath;
		this.outputFilePath = outputFilePath;
		// this.filter = filter;
		this.user = user;
		this.keyword = keyword;
		this.blacklist = blacklist;
	}

	/** getters and setters
	 */
	public String getInputFilePath(){
		return inputFilePath;
	}

	public String getOutputFilePath(){
		return outputFilePath;
	}

	//    public String getExport(){
	//    	return export;
	//    }

	//	public String getFilter(){
	//		return filter;
	//	}

	public String getUser(){
		return user;
	}

	public String getKeyWord(){
		return keyword;
	}

	public List<String> getBl(){
		return blacklist;
	}

}
