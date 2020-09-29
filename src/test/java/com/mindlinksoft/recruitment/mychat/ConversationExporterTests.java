package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import com.mindlinksoft.recruitment.mychat.commands.FilterByUserCommand;
import com.mindlinksoft.recruitment.mychat.commands.IConversationExportCommand;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests {
	
	Gson g;
	Random r;
	ConversationExporter exporter;
	
    /**
     * Tests that exporting a conversation will export the conversation correctly.
     * @throws IOException 
     * @throws IllegalArgumentException 
     */
    @Test
    public void exportingConversation_ExportsConversation() throws IllegalArgumentException, IOException {


        exporter.exportConversation("chat.txt", "chat.json", new ArrayList<IConversationExportCommand>());

       
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);   
       

        assertEquals("My Conversation", c.getName());

        assertEquals(7, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

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

    @Test
    public void writeConversation_WritesCorrectJsonFile() throws IllegalArgumentException, IOException {
    	// setup conversation object
    	String name = "Test Conversation";
    	String outputFilePath = "testWriteConversationWritesCorrectJsonFile.json";
    	String testSender1 = UUID.randomUUID().toString();
    	String testSender2 = UUID.randomUUID().toString();
    	String testSender3 = UUID.randomUUID().toString();

    	String testMessage1 = UUID.randomUUID().toString();
    	String testMessage2 = UUID.randomUUID().toString();
    	String testMessage3 = UUID.randomUUID().toString();

    	Instant testTimestamp1 = Instant.now().minusMillis(r.nextLong()).truncatedTo(ChronoUnit.SECONDS);
    	Instant testTimestamp2 = testTimestamp1.plusMillis(r.nextLong()).truncatedTo(ChronoUnit.SECONDS);
    	Instant testTimestamp3 = testTimestamp2.plusMillis(r.nextLong()).truncatedTo(ChronoUnit.SECONDS);
    	
    	
    	List<Message> messages = new ArrayList<Message>();
    	messages.add(new Message(testTimestamp1, testSender1, testMessage1));
    	messages.add(new Message(testTimestamp2, testSender2, testMessage2));
    	messages.add(new Message(testTimestamp3, testSender3, testMessage3)); 	
    	
    	Conversation testConversation = new Conversation(name, messages);
    	
    	// write to json
    	exporter.writeConversation(testConversation, outputFilePath);
    	
    	//check json file is correctly written
    	Conversation c = g.fromJson((new InputStreamReader(new FileInputStream(outputFilePath))), Conversation.class);


    	assertEquals(name, c.getName());
        assertEquals(3, c.getMessages().size());
    	
    	Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(testTimestamp1, ms[0].getTimestamp());
        assertEquals(testSender1, ms[0].getSenderId());
        assertEquals(testMessage1, ms[0].getContent());
        
        assertEquals(testTimestamp2, ms[1].getTimestamp());
        assertEquals(testSender2, ms[1].getSenderId());
        assertEquals(testMessage2, ms[1].getContent());
        
        assertEquals(testTimestamp3, ms[2].getTimestamp());
        assertEquals(testSender3, ms[2].getSenderId());
        assertEquals(testMessage3, ms[2].getContent());
        
    }
    
    @Test
    public void readConversation_ReturnsCorrectConversationObject() throws IOException {
    	// setup conversation
    	String name = "Test Conversation";
    	String inputFilePath = "testReadConversationReturnsCorrectConversationObject.txt";
    	String testSender1 = UUID.randomUUID().toString();
    	String testSender2 = UUID.randomUUID().toString();
    	String testSender3 = UUID.randomUUID().toString();

    	String testMessage1 = UUID.randomUUID().toString();
    	String testMessage2 = UUID.randomUUID().toString();
    	String testMessage3 = UUID.randomUUID().toString();

    	Instant testTimestamp1 = Instant.EPOCH.plusMillis(Math.abs(r.nextLong())).truncatedTo(ChronoUnit.SECONDS);
    	Instant testTimestamp2 = testTimestamp1.plusMillis(Math.abs(r.nextLong())).truncatedTo(ChronoUnit.SECONDS);
    	Instant testTimestamp3 = testTimestamp2.plusMillis(Math.abs(r.nextLong())).truncatedTo(ChronoUnit.SECONDS);
    	
    	//write a test text file
    	OutputStream os = new FileOutputStream(inputFilePath, false); 
    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
		StringBuffer sb = new StringBuffer();
		sb.append(name).append("\n");
		sb.append(testTimestamp1.getEpochSecond()).append(" ")
								.append(testSender1).append(" ")
								.append(testMessage1).append("\n");
		sb.append(testTimestamp2.getEpochSecond())
								.append(" ").append(testSender2)
								.append(" ").append(testMessage2)
								.append("\n");	
		sb.append(testTimestamp3.getEpochSecond()).append(" ")
								.append(testSender3).append(" ")
								.append(testMessage3);


        bw.write(sb.toString());
        bw.close();
        os.close();
        
        // test read conversation
        Conversation c = exporter.readConversation(inputFilePath);
        
           
    	//check conversation object is correct 
  
    	assertEquals(name, c.getName());
        assertEquals(3, c.getMessages().size());
    	
    	Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(testTimestamp1, ms[0].getTimestamp());
        assertEquals(testSender1, ms[0].getSenderId());
        assertEquals(testMessage1, ms[0].getContent());
        
        assertEquals(testTimestamp2, ms[1].getTimestamp());
        assertEquals(testSender2, ms[1].getSenderId());
        assertEquals(testMessage2, ms[1].getContent());
        
        assertEquals(testTimestamp3, ms[2].getTimestamp());
        assertEquals(testSender3, ms[2].getSenderId());
        assertEquals(testMessage3, ms[2].getContent());
    }

    @Test(expected = IllegalArgumentException.class)
    public void writeConversation_BadFilePath_ThrowsException() throws IllegalArgumentException, IOException {
    	// setup bad file path
    	String inputFilePath = "/test/Write/Conversation/IncorrectFilePath/ThrowsException.txt";
    	Conversation testConversation = new Conversation("name", new ArrayList<Message>());
    	
    	// write to json
    	exporter.writeConversation(testConversation, inputFilePath);
    }

    @Test(expected = IllegalArgumentException.class)
    public void readConversation_IncorrectFilePath_ThrowsException() throws IllegalArgumentException, IOException {
    	// setup bad file path
    	String inputFilePath = "testReadConversationIncorrectFilePathThrowsIOException.txt";
      
        // test read conversation
    	// this should throw an IllegalArgumentException due to FileNotFound
        exporter.readConversation(inputFilePath);
    }
    
    @Test
    public void doOptionalCommands_returnsFilteredConversation(){
    	String name = "Test Conversation";
    	String testSender1 = UUID.randomUUID().toString();
    	String testSender2 = UUID.randomUUID().toString();
    	String testSender3 = UUID.randomUUID().toString();

    	String testMessage1 = UUID.randomUUID().toString();
    	String testMessage2 = UUID.randomUUID().toString();
    	String testMessage3 = UUID.randomUUID().toString();

    	Instant testTimestamp1 = Instant.now().minusMillis(r.nextLong()).truncatedTo(ChronoUnit.SECONDS);
    	Instant testTimestamp2 = testTimestamp1.plusMillis(r.nextLong()).truncatedTo(ChronoUnit.SECONDS);
    	Instant testTimestamp3 = testTimestamp2.plusMillis(r.nextLong()).truncatedTo(ChronoUnit.SECONDS);
    	
    	
    	List<Message> messages = new ArrayList<Message>();
    	messages.add(new Message(testTimestamp1, testSender1, testMessage1));
    	messages.add(new Message(testTimestamp2, testSender2, testMessage2));
    	messages.add(new Message(testTimestamp3, testSender3, testMessage3)); 
    	
    	String userFilter = testSender1;
    	
    	Conversation testConversation = new Conversation(name, messages);
    	
    	Collection<IConversationExportCommand> cmds = new ArrayList<IConversationExportCommand>();
    	cmds.add(new FilterByUserCommand(userFilter));
    	Conversation c = exporter.doOptionalCommands(testConversation, cmds);
    
    	assertEquals(1, c.getMessages().size());
    	

    	Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);
        
    	assertEquals(userFilter, ms[0].getSenderId());
    	
    }


	/**
	 * Sets up before each test
	 */
	@Before
    public void setUp() {
		GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        g = builder.create();
        r = new Random();
        exporter = new ConversationExporter();
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
