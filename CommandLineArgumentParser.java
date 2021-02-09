package mindlink;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
    /**
     * Parses the given {@code arguments} into the exporter configuration.
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line arguments.
     */
    public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments)  throws IllegalArgumentException{
        // these are now sent as an array. enforce the idea that 0 and 1 should be input and output, the rest are to be used as FLAGS for FILTERS
        
        if(arguments[0] == null)
    	{
    		throw new IllegalArgumentException("The first argument should be an input file path.");
    	}
    	else if(arguments[1] == null)
    	{
    		throw new IllegalArgumentException("The second argument should be an output file path");
    	}
        return new ConversationExporterConfiguration(arguments);
    }
}
