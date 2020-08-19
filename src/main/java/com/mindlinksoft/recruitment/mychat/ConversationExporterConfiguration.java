package com.mindlinksoft.recruitment.mychat;

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Represents the configuration for the exporter.
 */
public final class ConversationExporterConfiguration {
	
    /**
     * Gets the input file path.
     */
    public static String inputFilePath = "C:\\Users\\Shehreem\\Documents\\GitHub\\my-chat\\chat.txt"; //TEMP LOCATION

    /**
     * Gets the output file path.
     */
    public static String outputFilePath = "C:\\Users\\Shehreem\\Documents\\GitHub\\my-chat\\output.txt"; //TEMP LOCATION
    
    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     */
    
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath) {
        inputFilePath = this.inputFilePath;
        outputFilePath = this.outputFilePath;
    }
    
    /**
     * Asks the user to enter the file path of their input file
     */
    public static String GetInputPath() {
        try { Scanner in = new Scanner(System.in);
    	System.out.println("Hello, What's the file path of the input file");
        System.out.println("You can find this by going on your file explorer and clicking on the file once and clicking 'copy path' on the top");
        System.out.println("If you do attain the file path in this way, please make sure to remove quotation marks and change the backward slashes '\\' to double backward slashes '\\\\'");
        System.out.println("For example, 'C:\\Users' should be entered as C:\\\\Users"); //console only prints half \ entered
        inputFilePath = in.nextLine();
        Scanner in2 = new Scanner(System.in);
    	System.out.println("You have entered the FilePath " + inputFilePath + " is this correct?");
    	 String decision = in2.nextLine();
 	    if (!(decision.toLowerCase().equals("yes")))
 	    {
 	    	GetInputPath();
 	    }
    } catch (NoSuchElementException e) {
        System.out.println("Sorry, I didn't pick up your input, please try again");
        GetInputPath();
    }
        return inputFilePath;
    }
    
    public static String GetOutputPath() {
        try {Scanner in = new Scanner(System.in);
    	System.out.println("Hello, What's the file path of the output file");
        System.out.println("You can find this by going on your file explorer and clicking on the file once and clicking 'copy path' on the top");
        System.out.println("If you do attain the file path in this way, please make sure to remove quotation marks and change the backward slashes '\\' to double backward slashes '\\\\'");
        System.out.println("For example, 'C:\\Users' should be entered as C:\\\\Users"); //console only prints half \ entered
        System.out.println("Additionally, if you'd like to create a new file, please enter the desired file name and it'll automatically be created");
        outputFilePath = in.nextLine();
        Scanner in2 = new Scanner(System.in);
    	System.out.println("You have entered the FilePath " + outputFilePath + " is this correct?");
    	 String decision = in2.nextLine();
 	    if (!(decision.toLowerCase().equals("yes") || outputFilePath.length() == 0))
 	    {
 	    	GetOutputPath();
 	    }
        } catch (NoSuchElementException e) {
        	System.out.println("Sorry, I didn't pick up your input, please try again");
            GetOutputPath();
        }
        return outputFilePath;
    }
}
