package test.java.com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import main.java.com.mindlinksoft.recruitment.mychat.ConversationExporter;
import main.java.com.mindlinksoft.recruitment.mychat.configuration.ConversationExporterConfiguration;
import main.java.com.mindlinksoft.recruitment.mychat.conversation.Conversation;
import main.java.com.mindlinksoft.recruitment.mychat.exceptions.FileAlreadyExistsExc;
import main.java.com.mindlinksoft.recruitment.mychat.message.Message;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests {
	private static final String inputFilePath = "chat.txt";
	private static final String outputFilePath = "chat.json";
	
	private static void clearOutput() throws IOException{
		File f = new File(ConversationExporterTests.outputFilePath);
		if (f.exists()){
	        Path p = f.toPath();
	        Files.delete(p);
		}
	}
	
	private static Conversation exportAndLoadResult(ConversationExporterConfiguration config) throws FileAlreadyExistsExc, FileNotFoundException, IOException{    	
    	ConversationExporter exporter = new ConversationExporter(config);

        exporter.export();

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c;
        try(InputStreamReader reader = new InputStreamReader(
        		new FileInputStream(ConversationExporterTests.outputFilePath))){
        	c = g.fromJson(reader, Conversation.class);
        }
        
        return c;
	}
	
    /**
     * Tests that exporting a conversation will export the conversation correctly.
     * @throws FileAlreadyExistsExc when output file already exists
     * @throws FileNotFoundException when the input file is not found
     * @throws IOException When reading the input file or deleting the generated file fails
     */
    @Test
    public void testExportingConversationExportsConversation() throws FileAlreadyExistsExc, FileNotFoundException, IOException {
    	ConversationExporterTests.clearOutput();
    	
		ConversationExporterConfiguration config = 
    			new ConversationExporterConfiguration(
    					ConversationExporterTests.inputFilePath, 
    					ConversationExporterTests.outputFilePath);
    	
    	Conversation c = ConversationExporterTests.exportAndLoadResult(config);

        assertEquals("My Conversation", c.getName());

        assertEquals(7, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(ms[0].getTimestamp(), Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].getSenderId(), "bob");
        assertEquals(ms[0].getContent(), "Hello there!");

        assertEquals(ms[1].getTimestamp(), Instant.ofEpochSecond(1448470905));
        assertEquals(ms[1].getSenderId(), "mike");
        assertEquals(ms[1].getContent(), "how are you?");

        assertEquals(ms[2].getTimestamp(), Instant.ofEpochSecond(1448470906));
        assertEquals(ms[2].getSenderId(), "bob");
        assertEquals(ms[2].getContent(), "I'm good thanks, do you like pie?");

        assertEquals(ms[3].getTimestamp(), Instant.ofEpochSecond(1448470910));
        assertEquals(ms[3].getSenderId(), "mike");
        assertEquals(ms[3].getContent(), "no, let me ask Angus, btw I think his number is 99-111222");

        assertEquals(ms[4].getTimestamp(), Instant.ofEpochSecond(1448470912));
        assertEquals(ms[4].getSenderId(), "angus");
        assertEquals(ms[4].getContent(), "Hell yes! Are we buying some pie? Here's my credit card number 111-22222-3333-444, feel free to use it");

        assertEquals(ms[5].getTimestamp(), Instant.ofEpochSecond(1448470914));
        assertEquals(ms[5].getSenderId(), "bob");
        assertEquals(ms[5].getContent(), "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[6].getTimestamp(), Instant.ofEpochSecond(1448470915));
        assertEquals(ms[6].getSenderId(), "angus");
        assertEquals(ms[6].getContent(), "YES! I'm the head pie eater there...");
    }
        
    /**
     * Tests that filtering by sender exports all messages sent by the specified user and by noone else.
     * @throws FileAlreadyExistsExc when output file already exists
     * @throws FileNotFoundException when the input file is not found
     * @throws IOException When reading the input file or deleting the generated file fails
     */
    @Test
    public void testUserfiltering() throws FileAlreadyExistsExc, FileNotFoundException, IOException {
    	ConversationExporterTests.clearOutput();
    	
		ConversationExporterConfiguration config = 
    			new ConversationExporterConfiguration(
    					ConversationExporterTests.inputFilePath, 
    					ConversationExporterTests.outputFilePath);
    	
		String testSender = "bob";
		int totalExpected = 3;
		
		config.setUserFilter(testSender);

		Conversation c = ConversationExporterTests.exportAndLoadResult(config);

        assertEquals("My Conversation", c.getName());

        assertEquals(totalExpected, c.getMessages().size());
        
        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);
        
        for (Message m : ms){
        	assertEquals(m.getSenderId(), testSender);
        }
    }
        
    /**
     * Tests that filtering by keyword exports all messages containing the specified keyword and no others.
     * @throws FileAlreadyExistsExc when output file already exists
     * @throws FileNotFoundException when the input file is not found
     * @throws IOException When reading the input file or deleting the generated file fails
     */
    @Test
    public void testKeywordfiltering() throws FileAlreadyExistsExc, FileNotFoundException, IOException {
    	ConversationExporterTests.clearOutput();
        
		ConversationExporterConfiguration config = 
    			new ConversationExporterConfiguration(
    					ConversationExporterTests.inputFilePath, 
    					ConversationExporterTests.outputFilePath);
    	
		String testKeyword = "the";
		int totalExpected = 2;
		
		config.setKeywordFilter(testKeyword);

		Conversation c = ConversationExporterTests.exportAndLoadResult(config);

        assertEquals("My Conversation", c.getName());
        
        assertEquals(totalExpected, c.getMessages().size());
        
        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);
        
        assertEquals(ms[0].getTimestamp(), Instant.ofEpochSecond(1448470914));
        assertEquals(ms[0].getSenderId(), "bob");
        assertEquals(ms[0].getContent(), "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[1].getTimestamp(), Instant.ofEpochSecond(1448470915));
        assertEquals(ms[1].getSenderId(), "angus");
        assertEquals(ms[1].getContent(), "YES! I'm the head pie eater there...");
    }
    
    /**
     * Tests that hiding specific words exports all messages replacing the specified words with *redacted*.
     * @throws FileAlreadyExistsExc when output file already exists
     * @throws FileNotFoundException when the input file is not found
     * @throws IOException When reading the input file or deleting the generated file fails
     */
    @Test
    public void testHidingWords() throws FileAlreadyExistsExc, FileNotFoundException, IOException {
    	ConversationExporterTests.clearOutput();

		ConversationExporterConfiguration config = 
    			new ConversationExporterConfiguration(
    					ConversationExporterTests.inputFilePath, 
    					ConversationExporterTests.outputFilePath);
    	
		String[] blacklistedWords = {"pie", "there"}; 
		int totalExpected = 7;
		
		List<String> blackList = new ArrayList<String>(blacklistedWords.length);
		for(String w : blacklistedWords) blackList.add(w);
		
		config.setBlacklist(blackList);

		Conversation c = ConversationExporterTests.exportAndLoadResult(config);

        assertEquals("My Conversation", c.getName());
        
        assertEquals(totalExpected, c.getMessages().size());
        
        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);
        
        assertEquals(ms[0].getTimestamp(), Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].getSenderId(), "bob");
        assertEquals(ms[0].getContent(), "Hello *redacted*!");

        assertEquals(ms[1].getTimestamp(), Instant.ofEpochSecond(1448470905));
        assertEquals(ms[1].getSenderId(), "mike");
        assertEquals(ms[1].getContent(), "how are you?");

        assertEquals(ms[2].getTimestamp(), Instant.ofEpochSecond(1448470906));
        assertEquals(ms[2].getSenderId(), "bob");
        assertEquals(ms[2].getContent(), "I'm good thanks, do you like *redacted*?");

        assertEquals(ms[3].getTimestamp(), Instant.ofEpochSecond(1448470910));
        assertEquals(ms[3].getSenderId(), "mike");
        assertEquals(ms[3].getContent(), "no, let me ask Angus, btw I think his number is 99-111222");

        assertEquals(ms[4].getTimestamp(), Instant.ofEpochSecond(1448470912));
        assertEquals(ms[4].getSenderId(), "angus");
        assertEquals(ms[4].getContent(), "Hell yes! Are we buying some *redacted*? Here's my credit card number 111-22222-3333-444, feel free to use it");

        assertEquals(ms[5].getTimestamp(), Instant.ofEpochSecond(1448470914));
        assertEquals(ms[5].getSenderId(), "bob");
        assertEquals(ms[5].getContent(), "No, just want to know if *redacted*'s anybody else in the *redacted* society...");

        assertEquals(ms[6].getTimestamp(), Instant.ofEpochSecond(1448470915));
        assertEquals(ms[6].getSenderId(), "angus");
        assertEquals(ms[6].getContent(), "YES! I'm the head *redacted* eater *redacted*...");
    }
    
    /**
     * Tests that hiding sensitive numbers exports all messages replacing phone and credit card numbers with *redacted*.
     * @throws FileAlreadyExistsExc when output file already exists
     * @throws FileNotFoundException when the input file is not found
     * @throws IOException When reading the input file or deleting the generated file fails
     */
    @Test
    public void testHidingSensitiveNumbers() throws FileAlreadyExistsExc, FileNotFoundException, IOException {
    	ConversationExporterTests.clearOutput();

		ConversationExporterConfiguration config = 
    			new ConversationExporterConfiguration(
    					ConversationExporterTests.inputFilePath, 
    					ConversationExporterTests.outputFilePath);
    	
		int totalExpected = 7;
		
		config.setHideSensitiveInfo(true);

		Conversation c = ConversationExporterTests.exportAndLoadResult(config);

        assertEquals("My Conversation", c.getName());
        
        assertEquals(totalExpected, c.getMessages().size());
        
        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);
        
        assertEquals(ms[0].getTimestamp(), Instant.ofEpochSecond(1448470901));
        assertEquals(ms[0].getSenderId(), "bob");
        assertEquals(ms[0].getContent(), "Hello there!");

        assertEquals(ms[1].getTimestamp(), Instant.ofEpochSecond(1448470905));
        assertEquals(ms[1].getSenderId(), "mike");
        assertEquals(ms[1].getContent(), "how are you?");

        assertEquals(ms[2].getTimestamp(), Instant.ofEpochSecond(1448470906));
        assertEquals(ms[2].getSenderId(), "bob");
        assertEquals(ms[2].getContent(), "I'm good thanks, do you like pie?");

        assertEquals(ms[3].getTimestamp(), Instant.ofEpochSecond(1448470910));
        assertEquals(ms[3].getSenderId(), "mike");
        assertEquals(ms[3].getContent(), "no, let me ask Angus, btw I think his number is *redacted*");

        assertEquals(ms[4].getTimestamp(), Instant.ofEpochSecond(1448470912));
        assertEquals(ms[4].getSenderId(), "angus");
        assertEquals(ms[4].getContent(), "Hell yes! Are we buying some pie? Here's my credit card number *redacted*, feel free to use it");

        assertEquals(ms[5].getTimestamp(), Instant.ofEpochSecond(1448470914));
        assertEquals(ms[5].getSenderId(), "bob");
        assertEquals(ms[5].getContent(), "No, just want to know if there's anybody else in the pie society...");

        assertEquals(ms[6].getTimestamp(), Instant.ofEpochSecond(1448470915));
        assertEquals(ms[6].getSenderId(), "angus");
        assertEquals(ms[6].getContent(), "YES! I'm the head pie eater there...");
    }
    
    /**
     * Tests that obfuscation users works consistently when enabled
     * @throws FileAlreadyExistsExc when output file already exists
     * @throws FileNotFoundException when the input file is not found
     * @throws IOException When reading the input file or deleting the generated file fails
     */
    @Test
    public void testSenderObfuscation() throws FileAlreadyExistsExc, FileNotFoundException, IOException {
    	ConversationExporterTests.clearOutput();

		ConversationExporterConfiguration config = 
    			new ConversationExporterConfiguration(
    					ConversationExporterTests.inputFilePath, 
    					ConversationExporterTests.outputFilePath);
    	
		config.setObfuscateUsers(true);

		Conversation c = ConversationExporterTests.exportAndLoadResult(config);

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(ms[0].getSenderId(), ms[2].getSenderId());
        assertEquals(ms[1].getSenderId(), ms[3].getSenderId());

        assertFalse(ms[1].getSenderId().equals(ms[2].getSenderId()));
        assertFalse(ms[3].getSenderId().equals(ms[4].getSenderId()));
        
        assertFalse(ms[0].getSenderId().contains("bob"));
        assertFalse(ms[1].getSenderId().contains("mike"));
        assertFalse(ms[2].getSenderId().contains("bob"));
        assertFalse(ms[3].getSenderId().contains("angus"));
    }
    
    /**
     * Tests that when report user stats is enabled, the correct statistics are accumulated and included in the output
     * @throws FileAlreadyExistsExc when output file already exists
     * @throws FileNotFoundException when the input file is not found
     * @throws IOException When reading the input file or deleting the generated file fails
     */
    @Test
    public void testStatsExport() throws FileAlreadyExistsExc, FileNotFoundException, IOException {
    	ConversationExporterTests.clearOutput();

		ConversationExporterConfiguration config = 
    			new ConversationExporterConfiguration(
    					ConversationExporterTests.inputFilePath, 
    					ConversationExporterTests.outputFilePath);
    	
		config.setReportUserStats(true);

		Conversation c = ConversationExporterTests.exportAndLoadResult(config);

		//extraLines is in our case a LinkedTreeMap hence the order is preserved
		Map<String, Map<String, Integer>> extraData = c.getExtraLines();
		Map<String, Integer> stats = extraData.get(ConversationExporterConfiguration.USER_STATS_TITLE);
		
		List<String> keys = new ArrayList<String>(stats.keySet());
		assertEquals(keys.get(0), "bob");
		assertEquals(keys.size(), 3);
		
		int bobTotal = stats.get("bob");
		assertEquals(bobTotal, 3);
		int mikeTotal = stats.get("mike");
		assertEquals(mikeTotal, 2);
		int angusTotal = stats.get("angus");
		assertEquals(angusTotal, 2);
    }
}
