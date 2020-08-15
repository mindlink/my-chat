package com.mindlinksoft.recruitment.mychat;

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
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception 
    {
    	if(args.length == 0)
    	{
    		ArrayList<ConversationExporterConfiguration> configs = new ArrayList<ConversationExporterConfiguration>();
        	
        	Scanner in = new Scanner(System.in);
        	int inputNo = 1;
        	int configNo = 1;
        	String input;
        	String config;
        	
        	System.out.print("Enter input file path " + inputNo + ": ");
        	input = in.nextLine();
        	while(!input.equals(""))
        	{
        		System.out.print("Enter configuration " + configNo + ": ");
        		config = in.nextLine();
        		while(!config.equals(""))
        		{
        			System.out.print("Enter output file path " + configNo + ": ");
            		String output = in.nextLine();
            		String[] arguments = {input, output, config};
            		configs.add(CommandLineArgumentParser.parseCommandLineArguments(arguments));
            		System.out.print("Enter configuration " + configNo + ": ");
            		config = in.nextLine();
        		}
        		configNo = 1;
        		System.out.print("Enter input file path " + inputNo + ": ");
            	input = in.nextLine();
        	}
        	
        	in.close();
        	
    	}
    	else
    	{
    		ConversationExporterConfiguration configuration = CommandLineArgumentParser.parseCommandLineArguments(args);

    		ConversationExporter.exportConversation(configuration);
    	}
    }
}
