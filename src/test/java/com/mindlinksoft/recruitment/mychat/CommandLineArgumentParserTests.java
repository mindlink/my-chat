package com.mindlinksoft.recruitment.mychat;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

public class CommandLineArgumentParserTests {
	
	/**
     * Tests that the /fuser argument is read correctly
     * @throws Exception When something bad happens.
     */
    @Test
    public void testUserFilter() throws Exception {
    	
        String[] arguments = {"chat.txt", "chat.json", "/fuser", "bob"};
    	
    	ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(arguments);
    	
    	ArrayList<Boolean> filterBooleans = configuration.getFilterSettings();
    	ArrayList<ArrayList<String>> filterValues = configuration.getFilterValues();

    	//User
        assertEquals(filterBooleans.get(0), true);
        assertEquals(filterValues.get(0).size(), 1);
        assertEquals(filterValues.get(0).get(0), "bob");

        //Keyword
        assertEquals(filterBooleans.get(1), false);
        assertEquals(filterValues.get(1).size(), 0);
        
        //Blacklist
        assertEquals(filterBooleans.get(2), false);
        assertEquals(filterValues.get(2).size(), 0);
        
        System.out.println("----------");
    }  

	/**
     * Tests that the only the first iteration of an argument is read
     * @throws Exception When something bad happens.
     */
    @Test
    public void testDuplicateArgument() throws Exception {
        String[] arguments = {"chat.txt", "chat.json", "/fuser", "bob", "/fuser", "donald"};
    	
    	ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(arguments);
    	
    	ArrayList<Boolean> filterBooleans = configuration.getFilterSettings();
    	ArrayList<ArrayList<String>> filterValues = configuration.getFilterValues();

    	//User
        assertEquals(filterBooleans.get(0), true);
        assertEquals(filterValues.get(0).size(), 1);
        assertEquals(filterValues.get(0).get(0), "bob");

        //Keyword
        assertEquals(filterBooleans.get(1), false);
        assertEquals(filterValues.get(1).size(), 0);
        
        //Blacklist
        assertEquals(filterBooleans.get(2), false);
        assertEquals(filterValues.get(2).size(), 0);
        
        System.out.println("----------");
    }  
    
    /**
     * Tests that the /fword argument is read correctly
     * @throws Exception When something bad happens.
     */
    @Test
    public void testKeywordFilter() throws Exception {
    	
        String[] arguments = {"chat.txt", "chat.json", "/fword", "pizza"};
    	
    	ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(arguments);
    	
    	ArrayList<Boolean> filterBooleans = configuration.getFilterSettings();
    	ArrayList<ArrayList<String>> filterValues = configuration.getFilterValues();

    	//User
        assertEquals(filterBooleans.get(0), false);
        assertEquals(filterValues.get(0).size(), 0);

        //Keyword
        assertEquals(filterBooleans.get(1), true);
        assertEquals(filterValues.get(1).size(), 1);
        assertEquals(filterValues.get(1).get(0), "pizza");
        
        //Blacklist
        assertEquals(filterBooleans.get(2), false);
        assertEquals(filterValues.get(2).size(), 0);
        
        System.out.println("----------");
    }   
    
    /**
     * Tests that the /blist argument is read correctly with a single value
     * @throws Exception When something bad happens.
     */
    @Test
    public void testBlacklistSingleValue() throws Exception {
    	
        String[] arguments = {"chat.txt", "chat.json", "/blist", "celery"};
    	
    	ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(arguments);
    	
    	ArrayList<Boolean> filterBooleans = configuration.getFilterSettings();
    	ArrayList<ArrayList<String>> filterValues = configuration.getFilterValues();

    	//User
        assertEquals(filterBooleans.get(0), false);
        assertEquals(filterValues.get(0).size(), 0);

        //Keyword
        assertEquals(filterBooleans.get(1), false);
        assertEquals(filterValues.get(1).size(), 0);
        
        //Blacklist
        assertEquals(filterBooleans.get(2), true);
        assertEquals(filterValues.get(2).size(), 1);
        assertEquals(filterValues.get(2).get(0), "celery");
        
        System.out.println("----------");
    } 
    
    /**
     * Tests that the /blist argument is read correctly with a few values
     * @throws Exception When something bad happens.
     */
    @Test
    public void testBlacklistMultipleValue() throws Exception {
    	
        String[] arguments = {"chat.txt", "chat.json", "/blist", "celery", "cabbage", "mushrooms"};
    	
    	ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(arguments);
    	
    	ArrayList<Boolean> filterBooleans = configuration.getFilterSettings();
    	ArrayList<ArrayList<String>> filterValues = configuration.getFilterValues();

    	//User
        assertEquals(filterBooleans.get(0), false);
        assertEquals(filterValues.get(0).size(), 0);

        //Keyword
        assertEquals(filterBooleans.get(1), false);
        assertEquals(filterValues.get(1).size(), 0);
        
        //Blacklist
        assertEquals(filterBooleans.get(2), true);
        assertEquals(filterValues.get(2).size(), 3);
        assertEquals(filterValues.get(2).get(0), "celery");
        assertEquals(filterValues.get(2).get(1), "cabbage");
        assertEquals(filterValues.get(2).get(2), "mushrooms");
        
        System.out.println("----------");
    }

    
    /**
     * Tests that all three arguments are read correctly 
     * @throws Exception When something bad happens.
     */
    @Test
    public void testAllArguments() throws Exception {
    	
        String[] arguments = {"chat.txt", "chat.json", "/fuser", "bob", "/blist", "celery", "cabbage", "mushrooms", "/fword", "curry"};
    	
    	ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(arguments);
    	
    	ArrayList<Boolean> filterBooleans = configuration.getFilterSettings();
    	ArrayList<ArrayList<String>> filterValues = configuration.getFilterValues();

    	//User
        assertEquals(filterBooleans.get(0), true);
        assertEquals(filterValues.get(0).size(), 1);
        assertEquals(filterValues.get(0).get(0), "bob");

        //Keyword
        assertEquals(filterBooleans.get(1), true);
        assertEquals(filterValues.get(1).size(), 1);
        assertEquals(filterValues.get(1).get(0), "curry");
        
        //Blacklist
        assertEquals(filterBooleans.get(2), true);
        assertEquals(filterValues.get(2).size(), 3);
        assertEquals(filterValues.get(2).get(0), "celery");
        assertEquals(filterValues.get(2).get(1), "cabbage");
        assertEquals(filterValues.get(2).get(2), "mushrooms");
        
        System.out.println("----------");
    }
    
    
    /**
     * Tests that invalid arguments are ignored
     * @throws Exception When something bad happens.
     */
    @Test
    public void testInvalid() throws Exception {
    	
        String[] arguments = {"chat.txt", "chat.json", "/fuser", "bob", "/invalid", "/blist", "celery", "cabbage", "mushrooms", "/fword", "curry"};
    	
    	ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(arguments);
    	
    	ArrayList<Boolean> filterBooleans = configuration.getFilterSettings();
    	ArrayList<ArrayList<String>> filterValues = configuration.getFilterValues();

    	//User
        assertEquals(filterBooleans.get(0), true);
        assertEquals(filterValues.get(0).size(), 1);
        assertEquals(filterValues.get(0).get(0), "bob");

        //Keyword
        assertEquals(filterBooleans.get(1), true);
        assertEquals(filterValues.get(1).size(), 1);
        assertEquals(filterValues.get(1).get(0), "curry");
        
        //Blacklist
        assertEquals(filterBooleans.get(2), true);
        assertEquals(filterValues.get(2).size(), 3);
        assertEquals(filterValues.get(2).get(0), "celery");
        assertEquals(filterValues.get(2).get(1), "cabbage");
        assertEquals(filterValues.get(2).get(2), "mushrooms");
        
        System.out.println("----------");
    }
    
    /**
     * Tests that empty arguments are ignored
     * @throws Exception When something bad happens.
     */
    @Test
    public void testEmptyArgument() throws Exception {
    	
        String[] arguments = {"chat.txt", "chat.json", "/fuser", "/blist", "celery", "cabbage", "mushrooms", "/fword", "curry"};
    	
    	ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(arguments);
    	
    	ArrayList<Boolean> filterBooleans = configuration.getFilterSettings();
    	ArrayList<ArrayList<String>> filterValues = configuration.getFilterValues();

    	//User
        assertEquals(filterBooleans.get(0), false);
        assertEquals(filterValues.get(0).size(), 0);

        //Keyword
        assertEquals(filterBooleans.get(1), true);
        assertEquals(filterValues.get(1).size(), 1);
        assertEquals(filterValues.get(1).get(0), "curry");
        
        //Blacklist
        assertEquals(filterBooleans.get(2), true);
        assertEquals(filterValues.get(2).size(), 3);
        assertEquals(filterValues.get(2).get(0), "celery");
        assertEquals(filterValues.get(2).get(1), "cabbage");
        assertEquals(filterValues.get(2).get(2), "mushrooms");
        
        System.out.println("----------");
    }
}
