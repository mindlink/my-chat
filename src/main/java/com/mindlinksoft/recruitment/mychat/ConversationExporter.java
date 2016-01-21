package com.mindlinksoft.recruitment.mychat;
/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public final class ConversationExporter {
    
    private final static String helpInstructions = 
            "General argument format:\n"
            +"-----------------------------\n"
            +"[file.txt]\n"
            +"[identifiers] [file.txt] *[filtervalues]\n"
            +"Identifier Options: [u|k|h]\n"
            +"-----------------------------\n"
            +"u - Filter conversation as per user/s\n"
            +"k - Filter conversation as per keywords\n"
            +"h -  Retract or hide words in the conversations\n"
            +"Identifier Format: [di|di|di] \n"
            +"------------------------------\n"
            +"d - placeholder for a digit from 1-9\n"
            +"i - placeholder for Identifier option\n"
            +"*The digit before the identifier indicates how many options are intended\n"
            +"*The ordering of the identifiers and coresponding digits indicates the order\n"
            +"of the arguments\n"
            +"Example\n"
            +"------------------------------\n"
            +"1k2h3u chat.txt keyword1 hiddenword1 hiddenword2 username1 username2 username3";
    private final ConversationExporterConfiguration configuration;
    
    /**
     * The application entry point.
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args)throws Exception{
        
        ConversationExporterConfiguration config = new ConversationExporterConfiguration(args);
        String check = config.parser.checkInput(args);
        if(check.equals("correct"))
        {
            config.setConfiguration(args);
            ConversationExporter exporter = new ConversationExporter(config);  
            exporter.exportConversation();
        }
        else
        {
            System.out.println("Input error: ");
            System.out.println(check);
            System.out.println();
            System.out.println("Help Instructions: "+ConversationExporter.helpInstructions);
        }
    }
    
    /**
     * Initializes a new instance of the {@link ConversationExporter} class.
     * @param configuration 
     * @throws Exception thrown from one of the helper class
     */
    public ConversationExporter(ConversationExporterConfiguration configuration) throws Exception{
        this.configuration=configuration;
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation() throws Exception {
        Writers writer = new Writers();
        Conversation conversation = writer.readConversation(configuration.getInputFilePath());
        Filter filter = new Filter();
        if(configuration.parser.hasIdentifiers)
        {
            if(!configuration.getUserNames()[0].equals("none-ike45jsi[';/.khcswsi"))
                conversation = filter.filterByUsername(configuration.getUserNames(), conversation);
            if(!configuration.getKeyWords()[0].equals("none-ike45jsi[';/.khcswsi"))
                conversation = filter.filterByKeyWords(configuration.getKeyWords(), conversation);
            if(!configuration.getHiddenWords()[0].equals("none-ike45jsi[';/.khcswsi"))
                conversation = filter.retractWords(configuration.getHiddenWords(), conversation);
        }      
        
        if(writer.writeConversation(conversation, configuration.getOutputFilePath())==1);   
            System.out.println("Conversation exported from '" + configuration.getInputFilePath() + "' to '" + configuration.getOutputFilePath());
            // TODO: Add more logging...
    }
}//end class