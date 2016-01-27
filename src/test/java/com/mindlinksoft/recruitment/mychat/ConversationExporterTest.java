
package com.mindlinksoft.recruitment.mychat;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Represents a metho to test Conversation exporter
 * @author Carl
 */
public class ConversationExporterTest {
    
    public ConversationExporterTest() {
    }

    /**
     * Test of main method, of class ConversationExporter.
     * @throws java.lang.Exception
     */
    @Test
    public void testMain() throws Exception {
        
        //test 1
        System.out.println("main");
        System.out.println("test1");
        String[] args = {"chat.txt"};
        ConversationExporter.main(args);
        
        //test 2 no username argument
        System.out.println("test2");
        String[] args2 = {"1u","chat.txt"};
        ConversationExporter.main(args2);
        
        //test 3 
        System.out.println("test3");
        String [] args3 = {"1u","chat.txt","mike"};
        ConversationExporter.main(args3);
    }
    
}//end class
