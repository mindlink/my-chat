package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.Services.FilterServices;
import com.mindlinksoft.recruitment.mychat.Services.IOFileServices;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Orry Edwards
 Represents a conversation filter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {

    /**
     * The application entry point.
     * @param args The command line arguments.
     * @throws Exception Thrown when an unexpected error happens
     */
    public static void main(String[] args) throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        
        //Get input file
        String inputFile = exporter.getUserInput("inputPath");
        
        //Validate the entered input file
        inputFile = exporter.validateFilePath(inputFile);
        
        //Get output file
        String outputFile = exporter.getUserInput("outputPath");
        
        //Add Json file extension
        outputFile = exporter.getJsonExtension(outputFile);
        
        //Set the entered input and output file
        ConversationExporterConfiguration config = 
                new ConversationExporterConfiguration(inputFile,outputFile);
        
        exporter.menu(config);
    }
    
     /**
     * Presents the main menu
     * @param config The configuration settings
     * @throws Exception Thrown when an unexpected error happens
     */
    private void menu(ConversationExporterConfiguration config)throws Exception
    {
        String choice = "";
        Scanner sc = new Scanner(System.in);
        FilterServices filter = new FilterServices(config);
        IOFileServices fileServices = new IOFileServices();
        
        try
        {
            //Get the whole conversation
            Conversation conversation = fileServices.readConversation(config.getInputFilePath());

            //Create a new conversation with the filtered messages and name
            Conversation filteredConversation;

            //Loop the menu until they want to exit
            while(!choice.equalsIgnoreCase("x"))
            {
                //Print menu options
                System.out.println(Resources.PLEASEENTER_MENUOPTION + getMenu());
                choice = sc.next().toLowerCase();

                switch(choice)
                {
                    //Export the conversation read file normally
                    case "e":
                        this.exportConversation(config);
                        break;
                    //Export a conversation filtered by a key word
                    case "k":
                        config.setfilterKeyWord(this.getUserInput("keyword"));
                        filteredConversation = filter.FilterByKeyWord(config, conversation);
                        fileServices.writeConversation(filteredConversation, config);
                        break;
                    //Export a conversation filtered by a specified user
                    case "u":
                        config.setFilterUserName(this.getUserInput("username"));
                        filteredConversation = filter.FilterByUsername(config, conversation);
                        fileServices.writeConversation(filteredConversation, config);
                        break;
                    //Export a conversation censored by a blacklist
                    case "b":
                        ArrayList<String> blackList = getBlackArrayList();
                        filteredConversation = filter.FilterByBlackList(config, blackList, conversation);
                        fileServices.writeConversation(filteredConversation, config);
                        break;
                    //Exit system
                    case "x":
                        System.out.println(Resources.CLOSING);
                        System.exit(0);
                        break;
                    default:
                        System.out.println(Resources.INVALIDMENUOPTION);
                        break;
                }
            }
        }
                catch (FileNotFoundException e){
            throw new IllegalArgumentException(Resources.FILEWASNOTFOUND + "\n" + e.getMessage());
        } 
    }
    
    /**
     * Exports the conversation at {@code config.inputFilePath} as JSON to {@code config.outputFilePath}.
     * @param config The configuration settings
     * @throws Exception Thrown when an unexpected error happens
     */
    public void exportConversation(ConversationExporterConfiguration config) throws Exception {
        
        IOFileServices fileServices = new IOFileServices();
        
        //Read the file
        Conversation conversation = fileServices.readConversation(config.getInputFilePath());

        //Write to file
        fileServices.writeConversation(conversation, config); 
    }
    
     /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @return the black listed words
     * @throws Exception Thrown when something bad happens.
     */
    private ArrayList<String> getBlackArrayList()
    {
        ArrayList<String> blackList = new ArrayList();
        boolean addAnother = true;
        
        while(addAnother)
        {
            //Enter a word to add to the black list
            System.out.println(Resources.PLEASEENTER_BLACKLISTWORD);
            Scanner sc = new Scanner(System.in);
            blackList.add(sc.nextLine());
            
            //Prompt to add another
            System.out.println(Resources.DOYOUWISHTOADDANOTHER);
            String choice = sc.nextLine().toLowerCase();
            
            //End loop when "n" is pressed
            if(choice.equals("n"))
                addAnother = false;
        }
        
        return blackList;
    }
    
    
     /**
     * Returns a string which displays the menu options
     * @return string of menu options
     */
    private String getMenu()
    {
        return  "\n" + 
                    Resources.OPTIONTEXT_EXPORTCONVERSATIONOPTION + "\n" +
                    Resources.OPTIONTEXT_FILTERCONVERSATIONBYKEY + "\n" +
                    Resources.OPTIONTEXT_FILTERCONVERSATIONBYUSERNAME + "\n" +
                    Resources.OPTIONTEXT_CENSORWORDSFROMBLACKLIST + "\n" +
                    Resources.OPTIONTEXT_EXIT;
    }
    
    
     /**
     * Prompts user for input depending on the where it was called
     * @return string representing the user input
     */
    private String getUserInput(String inputType)
    {
        String userInput;
        
        switch(inputType.toLowerCase())
        {
            case "keyword":
                System.out.println(Resources.PLEASEENTER_KEYWORD);
                break;
            case "username":
                System.out.println(Resources.PLEASEENTER_USERNAME);
                break;
            case "inputpath":
                System.out.println(Resources.PLEASEENTER_INPUTPATH);
                break;
            case "outputpath":
                System.out.println(Resources.PLEASEENTER_OUTPUTPATH);
                break;
        }
        Scanner sc = new Scanner(System.in);
        
        userInput = sc.nextLine();
        
        return userInput;
    }
    
    /**
     * Validates the inputPath specified
     * @return valid string representing the user input
     */
    private String validateFilePath(String inputPath)
    {
        IOFileServices fileServices = new IOFileServices();
        
        while(!fileServices.isFilePathValid(inputPath))
        {
            System.out.println("Invalid file path, please try again");
            inputPath = this.getUserInput(inputPath);
        }
        return inputPath;    
    }
    
    private String getJsonExtension(String outputPath)
    {
        //Strips the output path of any extensions it may have
        String path = outputPath.split("\\.", 2)[0];
        
        //Add the json extension
        path = path + ".json";
        
        return path;
    }
}
