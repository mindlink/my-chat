package com.mindlinksoft.recruitment.mychat;

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
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        
        //Get input and output file
        String inputFile = exporter.getInputPath();
        String outputFile = exporter.getOutputPath();
        
        //Set the entered input and output file
        ConversationExporterConfiguration config = 
                new ConversationExporterConfiguration(inputFile,outputFile);
        
        exporter.menu(config);
    }
    
     /**
     * Presents the main menu
     * @param config The configuration settings
     * @throws Exception Thrown when something bad happens.
     */
    private void menu(ConversationExporterConfiguration config)throws Exception
    {
        String choice = "";
        Scanner sc = new Scanner(System.in);
        FilterServices filter = new FilterServices(config);
        IOFileServices fileServices = new IOFileServices();
        
        //Get the whole conversation
        Conversation conversation = fileServices.readConversation(config.getInputFilePath());
       
        //Create a new conversation with the filtered messages and name
        Conversation filteredConversation;
        
        //Loop the menu until they want to exit
        while(!choice.equalsIgnoreCase("x"))
        {
            System.out.println(Resources.PLEASEENTERMENUOPTION);
            choice = sc.next().toLowerCase();

            switch(choice)
            {
                //Export the conversation read file normally
                case "e":
                    this.exportConversation(config);
                    break;
                //Export a conversation filtered by a key word
                case "k":
                    config.setfilterKeyWord(this.getKeyWord());
                    filteredConversation = filter.FilterByKeyWord(config, conversation);
                    fileServices.writeConversation(filteredConversation, config);
                    break;
                //Export a conversation filtered by a specified user
                case "u":
                    config.setFilterUserName(this.getUsername());
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
    
    /**
     * Exports the conversation at {@code config.inputFilePath} as JSON to {@code config.outputFilePath}.
     * @param config The configuration settings
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(ConversationExporterConfiguration config) throws Exception {
        
        IOFileServices fileServices = new IOFileServices();
        
        //Read the file
        Conversation conversation = fileServices.readConversation(config.getInputFilePath());

        //Write to file
        fileServices.writeConversation(conversation, config); 
    }
    
    private String getKeyWord()
    {
        String keyWord;
        
        System.out.println(Resources.PLEASEENTERKEYWORD);
        Scanner sc = new Scanner(System.in);
        
        keyWord = sc.nextLine();
        
        return keyWord;
    }
    
    private String getUsername()
    {
        String filteredUserName;
        
        System.out.println(Resources.PLEASEENTERUSERNAME);
        Scanner sc = new Scanner(System.in);
        
        filteredUserName = sc.nextLine();
        
        return filteredUserName;
    }
    
    private String getInputPath()
    {
        String path;
        
        System.out.println(Resources.PLEASEENTERINPUTPATH);
        Scanner sc = new Scanner(System.in);
        
        path = sc.nextLine();
        
        return path;
    }
    
    private String getOutputPath()
    {
        String path;
        
        System.out.println(Resources.PLEASEENTEROUTPUTPATH);
        Scanner sc = new Scanner(System.in);
        
        path = sc.nextLine();
        
        return path;
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
            System.out.println(Resources.PLEASEENTERBLACKLISTWORD);
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
    
    private void printMenu()
    {
        
    }
}
