package com.mindlinksoft.recruitment.mychat;

/**
 * Represents the configuration for the exporter.
 */
public final class ConversationExporterConfiguration {
    /**
     * Gets the input file path.
     */
    public String inputFilePath;

    /**
     * Gets the output file path.
     */  
    public String outputFilePath;

    
    /**
     * Boolean flags for the functions to be called in ConversationExporter's main function. #
     * False by default, as the functions should only execute when needed.
     */
	public boolean user = false;
	public boolean keyword = false;
	public boolean redact = false;
	
	public boolean redactPhoneCard = false;
	public boolean obfuscate = false;
	public boolean activity = false;
    
    /**
     * Gets the argument used by user, keyword or blacklist redaction functions
     */
    public String userArg = null;
    public String kwArg = null;
    public String redactArg = null;



    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, 
    		boolean user, String userArg, boolean keyword, String kwArg, boolean redact, String redactArg,
    		boolean redactPhoneCard, boolean obfuscate, boolean activity) {
    	
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        
        this.user = user;
        this.userArg = userArg;
        
        this.keyword = keyword;
        this.kwArg = kwArg;
        
        this.redact = redact;
        this.redactArg = redactArg;
        
        this.redactPhoneCard = redactPhoneCard;
        this.obfuscate = obfuscate;
        this.activity = activity;
    }
}
