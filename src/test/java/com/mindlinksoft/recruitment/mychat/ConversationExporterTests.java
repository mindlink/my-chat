package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import com.mindlinksoft.recruitment.mychat.Model.Conversation;
import com.mindlinksoft.recruitment.mychat.Model.Message;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests {
    
    @Test
    public void userFilterUnitTest() throws Exception {

        Message msg1 = new Message(Instant.ofEpochSecond(10), "Rodger", "How you doing today?");
        Message msg2 = new Message(Instant.ofEpochSecond(20), "Tim", "Not great.");
        Message msg3 = new Message(Instant.ofEpochSecond(30), "Rodger", "Why's that?");
        Message msg4 = new Message(Instant.ofEpochSecond(40), "Tim", "Had some mac n' cheese for dinner.");
        Message msg5 = new Message(Instant.ofEpochSecond(50), "Rodger", "Isn't that your favorite?");
        Message msg6 = new Message(Instant.ofEpochSecond(60), "Tim", "yeah.");
        Message msg7 = new Message(Instant.ofEpochSecond(70), "Rodger", "So what's wrong?");
        Message msg8 = new Message(Instant.ofEpochSecond(80), "Tim", "Mark cooked for dinner.");
        Message msg9 = new Message(Instant.ofEpochSecond(90), "Rodger", "OH.");


        List<Message> msgs = new ArrayList<Message>();
        msgs.add(msg1);
        msgs.add(msg2);
        msgs.add(msg3);
        msgs.add(msg4);
        msgs.add(msg5);
        msgs.add(msg6);
        msgs.add(msg7);
        msgs.add(msg8);
        msgs.add(msg9);

        List<Message> exptMsgs = new ArrayList<Message>();
        exptMsgs.add(msg2);
        exptMsgs.add(msg4);
        exptMsgs.add(msg6);
        exptMsgs.add(msg8);
        
        Conversation baseConvo = new Conversation("Cooking", msgs);
        Conversation exptConvo = new Conversation("Cooking", exptMsgs);

        Conversation actualConvo = new ConversationTransformer(baseConvo).filterConvoByUser("Tim");

        System.out.println(exptConvo.equals(actualConvo));

        assertEquals(exptConvo, actualConvo);

    }
    
    @Test
    public void contentFilterUnitTest() throws Exception {
        Message msg1 = new Message(Instant.ofEpochSecond(10), "Rodger", "How you doing today?");
        Message msg2 = new Message(Instant.ofEpochSecond(20), "Tim", "Not great.");
        Message msg3 = new Message(Instant.ofEpochSecond(30), "Rodger", "Why's that?");
        Message msg4 = new Message(Instant.ofEpochSecond(40), "Tim", "Had some mac n' cheese for dinner.");
        Message msg5 = new Message(Instant.ofEpochSecond(50), "Rodger", "Isn't that your favorite?");
        Message msg6 = new Message(Instant.ofEpochSecond(60), "Tim", "yeah.");
        Message msg7 = new Message(Instant.ofEpochSecond(70), "Rodger", "So what's wrong?");
        Message msg8 = new Message(Instant.ofEpochSecond(80), "Tim", "Mark cooked for dinner.");
        Message msg9 = new Message(Instant.ofEpochSecond(90), "Rodger", "OH.");


        List<Message> msgs = new ArrayList<Message>();
        msgs.add(msg1);
        msgs.add(msg2);
        msgs.add(msg3);
        msgs.add(msg4);
        msgs.add(msg5);
        msgs.add(msg6);
        msgs.add(msg7);
        msgs.add(msg8);
        msgs.add(msg9);

        List<Message> exptMsgs = new ArrayList<Message>();
        exptMsgs.add(msg4);
        exptMsgs.add(msg8);
        
        Conversation baseConvo = new Conversation("Cooking", msgs);
        Conversation exptConvo = new Conversation("Cooking", exptMsgs);

        Conversation actualConvo = new ConversationTransformer(baseConvo).filterConvoByKeyword("dinner");

        System.out.println(exptConvo.equals(actualConvo));

        assertEquals(exptConvo, actualConvo);
    }
    
    @Test
    public void censorConversationUnitTest() throws Exception {
        
        List<Message> msgs = new ArrayList<Message>();
        msgs.add(new Message(Instant.ofEpochSecond(10), "Rodger", "How you doing today?"));
        msgs.add(new Message(Instant.ofEpochSecond(20), "Tim", "Not great."));
        msgs.add(new Message(Instant.ofEpochSecond(30), "Rodger", "Why's that?"));
        msgs.add(new Message(Instant.ofEpochSecond(40), "Tim", "Had some mac n' cheese for dinner."));
        msgs.add(new Message(Instant.ofEpochSecond(50), "Rodger", "Isn't that your favorite?"));
        msgs.add(new Message(Instant.ofEpochSecond(60), "Tim", "yeah."));
        msgs.add(new Message(Instant.ofEpochSecond(70), "Rodger", "So what's wrong?"));
        msgs.add(new Message(Instant.ofEpochSecond(80), "Tim", "Mark cooked for dinner."));
        msgs.add(new Message(Instant.ofEpochSecond(90), "Rodger", "OH."));
        
        Conversation baseConvo = new Conversation("Cooking", msgs);


        List<Message> exptMsgs = new ArrayList<Message>();
        exptMsgs.add(new Message(Instant.ofEpochSecond(10), "Rodger", "How you doing today?"));
        exptMsgs.add(new Message(Instant.ofEpochSecond(20), "Tim", "Not great."));
        exptMsgs.add(new Message(Instant.ofEpochSecond(30), "Rodger", "Why's that?"));
        exptMsgs.add(new Message(Instant.ofEpochSecond(40), "Tim", "Had some mac n' cheese for *redacted*."));
        exptMsgs.add(new Message(Instant.ofEpochSecond(50), "Rodger", "Isn't that your favorite?"));
        exptMsgs.add(new Message(Instant.ofEpochSecond(60), "Tim", "yeah."));
        exptMsgs.add(new Message(Instant.ofEpochSecond(70), "Rodger", "So what's wrong?"));
        exptMsgs.add(new Message(Instant.ofEpochSecond(80), "Tim", "Mark *redacted*ed for *redacted*."));
        exptMsgs.add(new Message(Instant.ofEpochSecond(90), "Rodger", "OH."));
        
        Conversation exptConvo = new Conversation("Cooking", exptMsgs);


        List<String> blacklist = new ArrayList<String>();
        blacklist.add("dinner");
        blacklist.add("cook");


        Conversation actualConvo = new ConversationTransformer(baseConvo).censorConvo(blacklist);

        for (int i = 0; i < actualConvo.getMessages().size(); i++) {
            Message msg1 = ((ArrayList<Message>)actualConvo.getMessages()).get(i);
            Message msg2 = ((ArrayList<Message>)exptConvo.getMessages()).get(i);
            if(!msg1.equals(msg2)){
                System.out.println("content: " + msg1.content.equals(msg2.content));
                System.out.println("Timestamp: " + msg1.timestamp.equals(msg2.timestamp));
                System.out.println("ID: " + msg1.senderId.equals(msg2.senderId));
            }
        }

        assertEquals(exptConvo, actualConvo);
    }

    @Test
    public void readingUnitTest() throws Exception {

        List<Message> msgs = new ArrayList<Message>();
        
        msgs.add(new Message( Instant.ofEpochSecond(1448470901), "bob", "Hello there!"));
        msgs.add(new Message( Instant.ofEpochSecond(1448470905), "mike", "how are you?"));
        msgs.add(new Message( Instant.ofEpochSecond(1448470906), "bob", "I'm good thanks, do you like pie?"));
        msgs.add(new Message( Instant.ofEpochSecond(1448470910), "mike", "no, let me ask Angus..."));
        msgs.add(new Message( Instant.ofEpochSecond(1448470912), "angus", "Hell yes! Are we buying some pie?"));
        msgs.add(new Message( Instant.ofEpochSecond(1448470914), "bob", "No, just want to know if there's anybody else in the pie society..."));
        msgs.add(new Message( Instant.ofEpochSecond(1448470915), "angus", "YES! I'm the head pie eater there..."));

        Conversation expected = new Conversation("My Conversation", msgs);

        ConversationExporter ce = new ConversationExporter();
        assertEquals(expected, ce.readConversation("chat.txt"));
    }
    

    //End to End testing.

    /**
     * Tests that exporting a conversation will export the conversation correctly.
     * @throws Exception When something bad happens.
     */
    @Test
    public void testExportingConversationExportsConversation() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        exporter.exportConversation("chat.txt", "chat.json");

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        assertEquals("My Conversation", c.name);

        assertEquals(7, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(Instant.ofEpochSecond(1448470901), ms[0].timestamp);
        assertEquals("bob", ms[0].senderId);
        assertEquals("Hello there!", ms[0].content);

        assertEquals(Instant.ofEpochSecond(1448470905), ms[1].timestamp);
        assertEquals("mike", ms[1].senderId);
        assertEquals("how are you?", ms[1].content);

        assertEquals(Instant.ofEpochSecond(1448470906), ms[2].timestamp);
        assertEquals("bob", ms[2].senderId);
        assertEquals("I'm good thanks, do you like pie?", ms[2].content);

        assertEquals(Instant.ofEpochSecond(1448470910), ms[3].timestamp);
        assertEquals("mike", ms[3].senderId);
        assertEquals("no, let me ask Angus...", ms[3].content);

        assertEquals(Instant.ofEpochSecond(1448470912), ms[4].timestamp);
        assertEquals("angus", ms[4].senderId);
        assertEquals("Hell yes! Are we buying some pie?", ms[4].content);

        assertEquals(Instant.ofEpochSecond(1448470914), ms[5].timestamp);
        assertEquals("bob", ms[5].senderId);
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[5].content);

        assertEquals(Instant.ofEpochSecond(1448470915), ms[6].timestamp);
        assertEquals("angus", ms[6].senderId);
        assertEquals("YES! I'm the head pie eater there...", ms[6].content);
    }

    /**
     * Tests for the functions in {@link Conversation} designed to filter/censor
     * said conversation.
     * @throws Exception
     */
    @Test
    public void optionFunctionTests() throws Exception {

        ConversationExporter exporter = new ConversationExporter();

        Conversation convo = exporter.readConversation("chat.txt");
        ConversationTransformer c = new ConversationTransformer(convo);

        //User filtered convosation.
        Conversation userf_c = c.filterConvoByUser("bob");
        Message[] ms = new Message[userf_c.getMessages().size()];
        userf_c.getMessages().toArray(ms);

        assertEquals(Instant.ofEpochSecond(1448470901), ms[0].timestamp);
        assertEquals("bob", ms[0].senderId);
        assertEquals("Hello there!", ms[0].content);

        assertEquals(Instant.ofEpochSecond(1448470906), ms[1].timestamp);
        assertEquals("bob", ms[1].senderId);
        assertEquals("I'm good thanks, do you like pie?", ms[1].content);

        assertEquals(Instant.ofEpochSecond(1448470914), ms[2].timestamp);
        assertEquals("bob", ms[2].senderId);
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[2].content);



        //Keyword filtered convosation.
        Conversation keyf_c = c.filterConvoByKeyword("pie");
        ms = new Message[keyf_c.getMessages().size()];
        keyf_c.getMessages().toArray(ms);

        assertEquals(Instant.ofEpochSecond(1448470906), ms[0].timestamp);
        assertEquals("bob", ms[0].senderId);
        assertEquals("I'm good thanks, do you like pie?", ms[0].content);

        assertEquals(Instant.ofEpochSecond(1448470912), ms[1].timestamp);
        assertEquals("angus", ms[1].senderId);
        assertEquals("Hell yes! Are we buying some pie?", ms[1].content);

        assertEquals(Instant.ofEpochSecond(1448470914), ms[2].timestamp);
        assertEquals("bob", ms[2].senderId);
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[2].content);

        assertEquals(Instant.ofEpochSecond(1448470915), ms[3].timestamp);
        assertEquals("angus", ms[3].senderId);
        assertEquals("YES! I'm the head pie eater there...", ms[3].content);



        //Censored convosation.
        ArrayList<String> blacklist = new ArrayList<String>();
        blacklist.add("pie");
        blacklist.add("society");
        Conversation censored_c = c.censorConvo(blacklist);
        ms = new Message[censored_c.getMessages().size()];
        censored_c.getMessages().toArray(ms);

        assertEquals(Instant.ofEpochSecond(1448470901), ms[0].timestamp);
        assertEquals("bob", ms[0].senderId);
        assertEquals("Hello there!", ms[0].content);

        assertEquals(Instant.ofEpochSecond(1448470905), ms[1].timestamp);
        assertEquals("mike", ms[1].senderId);
        assertEquals("how are you?", ms[1].content);

        assertEquals(Instant.ofEpochSecond(1448470906), ms[2].timestamp);
        assertEquals("bob", ms[2].senderId);
        assertEquals("I'm good thanks, do you like *redacted*?", ms[2].content);

        assertEquals(Instant.ofEpochSecond(1448470910), ms[3].timestamp);
        assertEquals("mike", ms[3].senderId);
        assertEquals("no, let me ask Angus...", ms[3].content);

        assertEquals(Instant.ofEpochSecond(1448470912), ms[4].timestamp);
        assertEquals("angus", ms[4].senderId);
        assertEquals("Hell yes! Are we buying some *redacted*?", ms[4].content);

        assertEquals(Instant.ofEpochSecond(1448470914), ms[5].timestamp);
        assertEquals("bob", ms[5].senderId);
        assertEquals("No, just want to know if there's anybody else in the *redacted* *redacted*...", ms[5].content);

        assertEquals(Instant.ofEpochSecond(1448470915), ms[6].timestamp);
        assertEquals("angus", ms[6].senderId);
        assertEquals("YES! I'm the head *redacted* eater there...", ms[6].content);


    }

    /**
     * Will call the ConversationExporter's main method, testing that the program
     * parses the args correctly.
     * 
     * Note - Order of options in command matter.
     * 
     * @throws Exception
     */
    @Test
    public void TestingParsing() throws Exception {

        ConversationExporter.main(new String[]{"-i", "chat.txt", "-o", "chat.json", "-u=bob", "-k=pie", "-b=pie", "-b=society"});

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();

        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(Instant.ofEpochSecond(1448470906), ms[0].timestamp);
        assertEquals("bob", ms[0].senderId);
        assertEquals("I'm good thanks, do you like *redacted*?", ms[0].content);

        assertEquals(Instant.ofEpochSecond(1448470914), ms[1].timestamp);
        assertEquals("bob", ms[1].senderId);
        assertEquals("No, just want to know if there's anybody else in the *redacted* *redacted*...", ms[1].content);
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
