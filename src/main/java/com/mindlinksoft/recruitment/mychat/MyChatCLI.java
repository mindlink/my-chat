package com.mindlinksoft.recruitment.mychat;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.mindlinksoft.recruitment.mychat.model.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.utils.CommandLineArgumentParser;
import com.mindlinksoft.recruitment.mychat.utils.ConversationExporter;

/**
 * Command line interface for the my-chat app
 *
 */
public class MyChatCLI 
{
    /**
     * The application entry point.
     * @param args The command line arguments, empty if want to enter numerous configurations
     * @throws IOException When file can not be read from
     * @throws FileNotFoundException  When either input or output file can't be found
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws FileNotFoundException, IOException
    {
    	if(args.length == 0)
    	{
    		ArrayList<ConversationExporterConfiguration> configs = new ArrayList<ConversationExporterConfiguration>();
        	
        	Scanner in = new Scanner(System.in);
        	int inputNo = 1;
        	int configNo = 1;
        	String input;
        	String config;
        	
        	//Ask for input file continuously until empty input
        	System.out.print("Enter input file path " + inputNo + ": ");
        	input = in.nextLine();
        	while(!input.equals(""))
        	{
        		//Ask for configuration and output file for every input file continuously till empty input
        		System.out.print("Enter configuration " + configNo + ": ");
        		config = in.nextLine();
        		while(!config.equals(""))
        		{
        			System.out.print("Enter output file path " + configNo + ": ");
            		String output = in.nextLine();
            		String[] arguments = {input, output, config};
            		configs.add(CommandLineArgumentParser.parseCommandLineArguments(arguments));
            		configNo++;
            		System.out.print("Enter configuration " + configNo + ": ");
            		config = in.nextLine();
        		}
        		configNo = 1;
        		inputNo++;
        		System.out.print("Enter input file path " + inputNo + ": ");
            	input = in.nextLine();
        	}
        	
        	in.close();
        	
        	// Do exports using stored lists of configurations
        	for(ConversationExporterConfiguration configuration : configs)
        	{
        		ConversationExporter.exportConversation(configuration);
        	}
        	
    	}
    	else
    	{
    		//Use arguments given at invocation to set up configuration and run export
    		ConversationExporterConfiguration configuration = CommandLineArgumentParser.parseCommandLineArguments(args);

    		ConversationExporter.exportConversation(configuration);
    	}
    }
}
