package com.mindlinksoft.recruitment.mychat;

import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Represents a class which tests the CommandLineArgumentParser 
 * @author Carl
 */
public class CommandLineArgumentParserTest {
    
    public CommandLineArgumentParserTest() {
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

    /**
     * Test of collectIdentifierValues method, of class CommandLineArgumentParser.
     */
    @Test
    public void testCollectIdentifierValues() throws Exception{
        System.out.println("collectIdentifierValues");
        
        char u = 'u';
        char h ='h';
        char k ='k';
        
        System.out.println("test1");    
        String [] instanceInput = {"1u","chat.txt","user1"};
        CommandLineArgumentParser instance = new CommandLineArgumentParser(instanceInput);
        String [] expResult = {"user1"};
        String []result = instance.collectIdentifierValues(u,instanceInput);
        System.out.println(Arrays.toString(result)+" expected: "+Arrays.toString(expResult));
        
        System.out.println("test2");
        String [] instanceInput2 = {"2u","chat.txt","user1","user2"};
        CommandLineArgumentParser instance2 = new CommandLineArgumentParser(instanceInput2);
        String [] expResult2 = {"user1","user2"};
        String []result2 = instance2.collectIdentifierValues(u,instanceInput2);
        System.out.println(Arrays.toString(result2)+" expected: "+Arrays.toString(expResult2));
        
        System.out.println("test3");
        String [] instanceInput3 = {"1k3u","chat.txt","keyWord","user1","user2","user3"};
        CommandLineArgumentParser instance3 = new CommandLineArgumentParser(instanceInput3);
        String [] expResult3 = {"user1","user2","user3"};
        String []result3 = instance3.collectIdentifierValues(u,instanceInput3);
        System.out.println(Arrays.toString(result3)+" expected: "+Arrays.toString(expResult3));
        
        System.out.println("test4");
        String [] instanceInput4 = {"2k1u1h","chat.txt","keyWord1","keyWord1","user1","hiddenWord"};
        CommandLineArgumentParser instance4 = new CommandLineArgumentParser(instanceInput4);
        String [] expResult4 = {"user1"};
        String [] result4 = instance4.collectIdentifierValues(u,instanceInput4);
        System.out.println(Arrays.toString(result4)+" expected: "+Arrays.toString(expResult4));
        
        System.out.println("test5");
        String [] instanceInput5 = {"2h3k","chat.txt","hiddenWord1","hiddenWord2","keyWord","keyWord","keyword3"};
        CommandLineArgumentParser instance5 = new CommandLineArgumentParser(instanceInput5);
        String [] expResult5 = {"hiddenWord1","hiddenWord2"};
        String [] result5 = instance5.collectIdentifierValues(h,instanceInput5);
        System.out.println(Arrays.toString(result5)+" expected: "+Arrays.toString(expResult5));
        
        System.out.println("test6");
        String [] instanceInput6 = {"3k2h","chat.txt","keyWord1","keyWord2","keyWord3","hiddenWord1","hiddenWord2"};
        CommandLineArgumentParser instance6 = new CommandLineArgumentParser(instanceInput6);
        String [] expResult6 = {"keyWord1","keyWord2","keyWord3"};
        String [] result6 = instance6.collectIdentifierValues(k,instanceInput6);
        System.out.println(Arrays.toString(result6)+" expected: "+Arrays.toString(expResult6));
        
        System.out.println("test7");
        String [] instanceInput7 = {"1u1k1h","chat.txt","username1","keyWord1","hiddenWord1"};
        CommandLineArgumentParser instance7 = new CommandLineArgumentParser(instanceInput7);
        String [] expResult7 = {"keyWord1"};
        String [] result7 = instance7.collectIdentifierValues(k,instanceInput7);
        System.out.println(Arrays.toString(result7)+" expected: "+Arrays.toString(expResult7));
        
        System.out.println("test8");
        String [] instanceInput8 = {"1u","chat.txt","mike"};
        CommandLineArgumentParser instance8 = new CommandLineArgumentParser(instanceInput8);
        String [] expResult8 = {"mike"};
        String [] result8 = instance8.collectIdentifierValues(u,instanceInput8);
        System.out.println(Arrays.toString(result8)+" expected: "+Arrays.toString(expResult8));
        
        assertEquals(Arrays.toString(expResult), Arrays.toString(result));
        assertEquals(Arrays.toString(expResult2), Arrays.toString(result2));
        assertEquals(Arrays.toString(expResult3), Arrays.toString(result3));
        assertEquals(Arrays.toString(expResult4), Arrays.toString(result4));
        assertEquals(Arrays.toString(expResult5), Arrays.toString(result5));
        assertEquals(Arrays.toString(expResult6), Arrays.toString(result6));
        assertEquals(Arrays.toString(expResult7), Arrays.toString(result7));
        assertEquals(Arrays.toString(expResult8), Arrays.toString(result8));
        
    }

    /**
     * Test of createOutputFilePath method, of class CommandLineArgumentParser.
     */
    @Test
    public void testCreateOutputFilePath() throws Exception {
        System.out.println("createOutputFilePath");
        String [] instanceInput = {"ukh","chat.txt","mike","keyWord","hiddenWord"};
        CommandLineArgumentParser instance = new CommandLineArgumentParser(instanceInput);
        String expResult = "chat.json";
        String result = instance.createOutputFilePath();
        System.out.println(expResult+" "+result);
        assertEquals(expResult, result);
    }

    /**
     * Test of checkInput method, of class CommandLineArgumentParser.
     */
    @Test
    public void testCheckInput() throws Exception {
        System.out.println("checkInput");
        System.out.println("test1");
        String[] arguments = {"1k2h3u","test.txt","1","2","3","4","5","6"};
        CommandLineArgumentParser instance =new CommandLineArgumentParser(arguments);
        String expResult = "correct";
        String result = instance.checkInput(arguments);
        assertEquals(expResult, result);
        
        System.out.println("test 2");
        String[] arguments2 = {"1k","test.txt","1"};
        String expResult2 = "correct";
        String result2 = instance.checkInput(arguments2);
        assertEquals(expResult2, result2);
        
        System.out.println("test 3");
        String[] arguments3 = {"chat.txt"};
        String expResult3 = "correct";
        String result3 = instance.checkInput(arguments3);
        assertEquals(expResult3, result3);
        
        System.out.println("test 4");
        String[] arguments4 = {"1k2h3u","test.txt","1","2","3","4","5"};
        String expResult4 = "Incorrect number of arguments";
        String result4 = instance.checkInput(arguments4);
        assertEquals(expResult4, result4);
        
        System.out.println("test 5");
        String[] arguments5 = {};
        String expResult5 = "Need at least one argument with input file path";
        String result5 = instance.checkInput(arguments5);
        assertEquals(expResult5, result5);
    }

    /**
     * Test of tallyIdentifiers method, of class CommandLineArgumentParser.
     */
    @Test
    public void testTallyIdentifiers() throws Exception {
        System.out.println("tallyIdentifiers");
        String identifiers = "1k2h3u";
        String [] args= {"1k2h3u","test"};
        CommandLineArgumentParser instance = new CommandLineArgumentParser(args);
        int expResult = 6;
        int result = instance.tallyIdentifiers(identifiers);
        assertEquals(expResult, result);
    }

    
}
