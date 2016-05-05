package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.Collection;


/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTest {
	
	public ConversationExporter exporter = new ConversationExporter();
	
	@Rule
	public TemporaryFolder folder= new TemporaryFolder();
	
    /**
     * Tests Gson correctly instantiates {@link Gson} object which is able to serialize our custom type, {@link Instant}.
     */
	@Test
	public void testSetupGson() {
		
		Gson g = exporter.setupGson();
		
		long time = 1448470901;
		Instant timestamp = Instant.ofEpochSecond(time);

		assertEquals(Long.toString(time), g.toJson(timestamp));
	}
	
	/**
	 * Tests checkWrite for establishing whether {@code testFile} should be overwritten.
	 */
	@Test
	public void testCheckWriteUserPrompt() throws IOException {
		
		String testFile = "test_overwrite.txt";
		assertTrue(exporter.checkWrite(testFile, false));  // Returns true if file doesn't exist
		
		String outputFilePath = folder.newFile(testFile).getAbsolutePath();
	    
		assertTrue(exporter.checkWrite(testFile, true));  // Returns true if file exists and 'forceOverwrite' is enabled
		
	    String userInput = "n";
	    InputStream in = new ByteArrayInputStream(userInput.getBytes());
	    System.setIn(in);
	    
	    assertFalse(exporter.checkWrite(outputFilePath, false));	// Returns false is user enters 'n'
	    
	    userInput = "y";
	    in = new ByteArrayInputStream(userInput.getBytes());
	    System.setIn(in);
	    
	    assertTrue(exporter.checkWrite(outputFilePath, false));		// Returns true if user enters 'y'
	    
	    System.setIn(System.in);
	}
	
    /**
     * Tests that exporting a conversation will export the conversation correctly.
     * @throws Exception When something bad happens.
     */
    @Test
    public void testExportingConversationExportsConversation() throws Exception { //Integration Test
    	String testIn = folder.newFile("testIn.txt").getAbsolutePath();
    	String testOut = folder.newFile("testOut.json").getAbsolutePath();
    	
		TestUtilities.createSampleReadFile(testIn);
		
		String[] args = {testIn, testOut}; 	
    	ConversationExporterConfiguration configuration = CommandLineArgumentParser.parseCommandLineArguments(args);
    	configuration.forceOverwrite(true);
    	
    	exporter.exportConversation(configuration); 

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();
        
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream(testOut)), Conversation.class);
        Collection<Message> messages = c.getMessages();
        
        assertEquals("My Conversation", c.getName());

        assertEquals(7, messages.size());

        Message[] ms = new Message[messages.size()];
        messages.toArray(ms);

	    assertEquals(Instant.ofEpochSecond(1448470901), ms[0].getTimestamp());
	    assertEquals("bob", ms[0].getSenderId());
	    assertEquals("Hello there!", ms[0].getContent());
	    
	    assertEquals(Instant.ofEpochSecond(1448470905), ms[1].getTimestamp());
	    assertEquals("mike", ms[1].getSenderId());
	    assertEquals("how are you?", ms[1].getContent());

	    assertEquals(Instant.ofEpochSecond(1448470906), ms[2].getTimestamp());
	    assertEquals("bob", ms[2].getSenderId());
	    assertEquals("I'm good thanks, do you like pie?", ms[2].getContent());

	    assertEquals(Instant.ofEpochSecond(1448470910), ms[3].getTimestamp());
	    assertEquals("mike", ms[3].getSenderId());
	    assertEquals("no, let me ask Angus...", ms[3].getContent());

	    assertEquals(Instant.ofEpochSecond(1448470912), ms[4].getTimestamp());
	    assertEquals("angus", ms[4].getSenderId());
	    assertEquals("Hell yes! Are we buying some pie?", ms[4].getContent());

	    assertEquals(Instant.ofEpochSecond(1448470914), ms[5].getTimestamp());
	    assertEquals("bob", ms[5].getSenderId());
	    assertEquals("No, just want to know if there's anybody else in the pie society...", ms[5].getContent());

	    assertEquals(Instant.ofEpochSecond(1448470915), ms[6].getTimestamp());
	    assertEquals("angus", ms[6].getSenderId());
	    assertEquals("YES! I'm the head pie eater there...", ms[6].getContent());
    }

    class InstantDeserializer implements JsonDeserializer<Instant> {

        @Override
        public Instant deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (!jsonElement.isJsonPrimitive()) {
                throw new JsonParseException("Expected instant represented as JSON number, but no primitive found.");
            }

            JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();

            if (!jsonPrimitive.isNumber()) {
                throw new JsonParseException("Expected instant represented as JSON number, but different primitive found.");
            }

            return Instant.ofEpochSecond(jsonPrimitive.getAsLong());
        }
    }
}
