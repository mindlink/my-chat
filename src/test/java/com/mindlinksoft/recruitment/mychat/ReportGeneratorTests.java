package com.mindlinksoft.recruitment.mychat;

import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class ReportGeneratorTests {

	/**
     * Tests the report generation functionality
     * @throws Exception When something bad happens.
     */
    @Test
    public void testReportGeneration() throws Exception {
    	
    	String[] users = {"bill", "ben", "jack", "jill", "bill", "ben", "jack", "bill", "ben", "bill"};
    	
    	ReportGenerator generator = new ReportGenerator();
    	
    	for (String user : users) {
    		generator.addUser(user);
    	}
    	
    	List<UserCount> sortedUsers = generator.sortValues();
    	
    	assertEquals(sortedUsers.get(0).name, "bill");
    	assertEquals(sortedUsers.get(0).occurrence, 4);
    	
    	assertEquals(sortedUsers.get(1).name, "ben");
    	assertEquals(sortedUsers.get(1).occurrence, 3);
    	
    	assertEquals(sortedUsers.get(2).name, "jack");
    	assertEquals(sortedUsers.get(2).occurrence, 2);
    	
    	assertEquals(sortedUsers.get(3).name, "jill");
    	assertEquals(sortedUsers.get(3).occurrence, 1);
    	
    }
    
    /**
     * Tests the report generation functionality but with oddly capitalized usernames
     * @throws Exception When something bad happens.
     */
    @Test
    public void testReportGenerationVariedCases() throws Exception {
    	
    	String[] users = {"bIll", "beN", "jAcK", "Jill", "bilL", "bEn", "JaCk", "BIll", "Ben", "BilL"};
    	
    	ReportGenerator generator = new ReportGenerator();
    	
    	for (String user : users) {
    		generator.addUser(user);
    	}
    	
    	List<UserCount> sortedUsers = generator.sortValues();
    	
    	assertEquals(sortedUsers.get(0).name, "bill");
    	assertEquals(sortedUsers.get(0).occurrence, 4);
    	
    	assertEquals(sortedUsers.get(1).name, "ben");
    	assertEquals(sortedUsers.get(1).occurrence, 3);
    	
    	assertEquals(sortedUsers.get(2).name, "jack");
    	assertEquals(sortedUsers.get(2).occurrence, 2);
    	
    	assertEquals(sortedUsers.get(3).name, "jill");
    	assertEquals(sortedUsers.get(3).occurrence, 1);
    	
    }
}
