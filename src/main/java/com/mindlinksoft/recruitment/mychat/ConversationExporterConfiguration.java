package com.mindlinksoft.recruitment.mychat;

/**
 * Represents the configuration for the exporter.
 */
public final class ConversationExporterConfiguration {
    //Attributes ---------------------------------------------------------------
    /**
     * Holds input file path
     */
    private String inputFilePath;
    /**
     * Holds output file path
     */
    private String outputFilePath;
    /**
     * Holds usernames to be filter by if any
     */
    private String []userName;
    /**
     * Holds key words to be filtered by if any
     */
    private String []keyWord;
    /**
     * Holds words to be retracted if any
     */
    private String []hiddenWord;
    /**
     * Holds an instance of the command line parser
     */
    protected CommandLineArgumentParser parser;
    
    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param arguments The arguments supplied by the (@link CommandLineArgumentParser) class 
     * @throws Exception from (@link CommandLineArgumentParser) Class
     */
    public ConversationExporterConfiguration(String [] arguments) throws Exception {
        parser = new CommandLineArgumentParser(arguments);     
    }
    /**
     * Represents helper method to set up configuration
     * @throws Exception from parser methods
     */
    public void setConfiguration(String[] arguments) throws Exception
    {
        this.inputFilePath = parser.getInputFilePath();
        this.outputFilePath = parser.createOutputFilePath();
        if(parser.hasIdentifiers)
        {
            this.userName = parser.collectIdentifierValues('u',arguments);//filtered user if any
            this.keyWord = parser.collectIdentifierValues('k',arguments);//filtered keyword if any
            this.hiddenWord = parser.collectIdentifierValues('h',arguments);//retracted word if any          
        }
    }
    
    //Accesors -----------------------------------------------------------------
    /**
     * Access inputFilePath
     * @return outPutFilePath
     */
    public String getInputFilePath(){
        return inputFilePath;
    }
    /**
     * Access user name to filter by if any
     * @return keyWord, null if none specified 
     */
    public String []getUserNames(){
        return userName;
    }
    /**
     * Access key word to filter by if any
     * @return keyWord, null if none specified 
     */
    public String []getKeyWords(){
        return this.keyWord;
    }
    /**
     * Access hidden word to filter by if any
     * @return hiddenWord, null if none specified 
     */
    public String []getHiddenWords(){
        return this.hiddenWord;
    }
    /**
     * Access outPutFilePath
     * @return outPutFilePath
     */
    public String getOutputFilePath(){
        return this.outputFilePath;
    }
}
