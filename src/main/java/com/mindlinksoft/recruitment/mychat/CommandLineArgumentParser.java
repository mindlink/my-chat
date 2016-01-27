package com.mindlinksoft.recruitment.mychat;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
    
    //Attributes ---------------------------------------------------------------
    private final String inputFilePath;
    public boolean hasIdentifiers;
    
    //Constructors -------------------------------------------------------------
    /**
     * Initializes a new instance of the {@link ConversationExporter} class.
     * @param arguments
     * @throws Exception does something bad
     */
    public CommandLineArgumentParser(String [] arguments) throws Exception{
        if(arguments.length==1)
        {
            this.inputFilePath = arguments[0];
            hasIdentifiers = false;
        }
        else
        {
            this.inputFilePath = arguments[1];
            hasIdentifiers = true;
        }
    }
    
    /**
     * Method to check if identifier is found in identifier list and extract an array of 
     * the arguments it represents 
     * @param c The character identifier to search for.
     * @param arguments The arguments entered from command line NB the first argument must be the identifier list with format [di|di|di*]
     * @return the Corresponding arguments linked to the identifier. null if identifier is not present.
     * @throws Exception if input is incorrect
     */
    public String [] collectIdentifierValues(char c, String [] arguments) throws Exception{
        
        String identifiers = arguments[0];

        int i = identifiers.indexOf(c);
        int size = 0;
        String [] values; 
        int offset = 2;

        if(i!=-1)//identifieier found
        {    
            size = Character.getNumericValue(identifiers.charAt(i-1));
            values = new String[size];
            if(i!=1)//if not the only identifier
            {
                String temp =identifiers.substring(0,i-1);
                char [] offsetCounter = temp.toCharArray();

                for(int j=0;j<i-1;j+=2)
                    offset+=Character.getNumericValue(offsetCounter[j]);
            }
            for (int j=0;j<size;j++)
                values[j]=arguments[offset+j];
        }
        else//if not found
        {
            values = new String[1];
            values[0]= "none-ike45jsi[';/.khcswsi";
        }
        return values;
    }
    /**
     * Represents helper method to exchange file extension with .json
     * @return The desired output file path.
     */
    public String createOutputFilePath(){
        final int lastPeriodPos = inputFilePath.lastIndexOf('.');
        return inputFilePath.substring(0,lastPeriodPos)+".json";
    }
    /**
     * Represents helper method to verify input arguments
     * @param arguments arguments entered from command line
     * @return A String containing the input error message or correct if error checks pass
     */
    public String checkInput(String [] arguments)
    {
        String check = "correct";
        
        if(arguments.length==0)
            check="Need at least one argument with input file path";
        else if(arguments.length==1 && !arguments[0].contains(".txt"))
            check = "Input file is not a text file";
        //minus 2 to account for Identifiers and file path
        else if(arguments.length!=1 && this.tallyIdentifiers(arguments[0])!=arguments.length-2)
            check = "Incorrect number of arguments";
        
        return check;     
    }
    
    /**
     * Represents Helper method for the input checker to count identifier argument number
     * @param identifiers Identifiers entered as first argument from command line
     * @return Number of intended arguments
     */
    public int tallyIdentifiers(String identifiers){
        char [] charIdentifiers = identifiers.toCharArray();
        int tally=0;
        for(int i=0;i<charIdentifiers.length;i+=2)
        {
            tally+=Character.getNumericValue(charIdentifiers[i]);
        }
        return tally;
    }
    
    //Accesors -----------------------------------------------------------------
    /**
     * Method to get input file path
     * @return input file path
     */
    public String getInputFilePath(){
        return this.inputFilePath;
    }
     
}//end class
