package com.mindlinksoft.recruitment.mychat.conversation.serialization;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mindlinksoft.recruitment.mychat.conversation.Conversation;
import com.mindlinksoft.recruitment.mychat.message.IMessage;
import com.mindlinksoft.recruitment.mychat.message.Message;

public class JSONSerializerTest {

	private static ISerializer serializer;
	private static Gson g;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		serializer = new JSONSerializer();
		
		GsonBuilder builder = new GsonBuilder();
        
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        builder.registerTypeAdapter(Conversation.class, new ConversationDeserializer());
        
        g = builder.create();
        
	}

	@Test
	public void testInstantSerialization() {
		Instant instant = Instant.ofEpochSecond(1448470905);
		
		String jsonInstant = serializer.serialize(instant);
		Instant deserializedInstant = g.fromJson(jsonInstant, Instant.class);
		
		assertEquals(instant, deserializedInstant);
	}

	@Test
	public void testConversationSerialization() {
		Instant instant = Instant.ofEpochSecond(1448470905);
		Collection<IMessage> msgs = new ArrayList<IMessage>();
		IMessage msg1 = new Message(instant, "alex", "blah blah");
		msgs.add(msg1);
		
		Conversation conversation = new Conversation("my conversation", msgs);
		
		String serializedConversation = serializer.serialize(conversation);
		Conversation deserializedConversation = g.fromJson(serializedConversation, Conversation.class);
		Message[] desiarilizedMessages = new Message[deserializedConversation.getMessages().size()];
		deserializedConversation.getMessages().toArray(desiarilizedMessages);
		
		assertEquals(conversation.getName(), deserializedConversation.getName());
		assertEquals(desiarilizedMessages[0].getTimestamp(), msg1.getTimestamp());
		assertEquals(desiarilizedMessages[0].getSenderId(), msg1.getSenderId());
		assertEquals(desiarilizedMessages[0].getContent(), msg1.getContent());
		
	}
}
