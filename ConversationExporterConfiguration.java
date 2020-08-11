package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the configuration for the exporter.
 */
public final class ConversationExporterConfiguration {
	
	  //Gets the input file path.
	  public String inputFilePath;
	  //Gets the output file path.
	  public String outputFilePath;
	  // Gets all the commands needed to be executed
	  public List<Functionality> functionality;
	    
	   
	  /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     */

	  public ConversationExporterConfiguration(String inputFilePath, String outputFilePath) {
		  this.inputFilePath = inputFilePath;
		  this.outputFilePath = outputFilePath;
	  }
	  
	  // Add command to be executed in a list of type Functionality (Abstract class)
	  public void addFunctionality(Functionality functionality) {
		  if(this.functionality == null || this.functionality.isEmpty()) {
			  this.functionality = new ArrayList<Functionality>();
		  }
		  this.functionality.add(functionality);
		  return;
	  }
	  
	  // Returns false if there are commands to be executed
	  public Boolean isFunctionalityEmpty() {
		  if(this.functionality == null) {
			  return true;
		  }
		  return false;
	  }
	  

}