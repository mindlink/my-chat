package com.mindlinksoft.recruitment.mychat;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Class to test the (@link Writers) class.
 * @author Carl
 */
public class WritersTest {
    
    public WritersTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    public Writers instance = new Writers();
    public TesterData testerData = new TesterData();

    /**
     * Test of writeConversation method, of class Writers.
     */
    @Test
    public void testWriteConversation() throws Exception {
        System.out.println("writeConversation");
        Conversation conversation = testerData.createTestConversation();
        String outputFilePath = "chat.json";
        int result = instance.writeConversation(conversation, outputFilePath);
        int expResult = 1;
        System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of readConversation method, of class Writers.
     */
    @Test
    public void testReadConversation() throws Exception {
        System.out.println("readConversation");
        String inputFilePath = "chat.txt";        
        Conversation expResult = testerData.createTestConversation();
        Conversation result = instance.readConversation(inputFilePath);
        System.out.println(expResult.toString());
        System.out.println(result.toString());
        System.out.println("Message Counts: "+result.getMessages().size()+" expect: "+expResult.getMessages().size());
        assertEquals(expResult.toString(), result.toString());
        
    }
    
}
