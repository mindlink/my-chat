package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import org.junit.Test;

import com.mindlinksoft.recruitment.mychat.features.BlacklistFeature;
import com.mindlinksoft.recruitment.mychat.features.ChatFeature;
import com.mindlinksoft.recruitment.mychat.features.HideNumbersFeature;
import com.mindlinksoft.recruitment.mychat.features.KeywordFilterFeature;
import com.mindlinksoft.recruitment.mychat.features.ObfuscateUserFeature;
import com.mindlinksoft.recruitment.mychat.features.UserFilterFeature;
import com.mindlinksoft.recruitment.mychat.model.Conversation;
import com.mindlinksoft.recruitment.mychat.model.Message;
import com.mindlinksoft.recruitment.mychat.utils.FeatureParser;

/**
 * Test class to unit test each feature works when applied to messages/conversation
 *
 */
public class ChatFeatureUnitTests 
{
	/**
	 * Tests the FeatureParser.parse() function returns the correct Feature class if given correct flag
	 */
	@Test
	public void featureParserTest()
	{
		String args = "-b=Hello,pie,yes";
		ChatFeature feature = FeatureParser.parse(args);
		assertTrue(feature.getClass() == BlacklistFeature.class);
		
		args = "-k=pie";
		feature = FeatureParser.parse(args);
		assertTrue(feature.getClass() == KeywordFilterFeature.class);
		
		args = "-u=bob";
		feature = FeatureParser.parse(args);
		assertTrue(feature.getClass() == UserFilterFeature.class);
		
		args = "-h";
		feature = FeatureParser.parse(args);
		assertTrue(feature.getClass() == HideNumbersFeature.class);
		
		args = "-o";
		feature = FeatureParser.parse(args);
		assertTrue(feature.getClass() == ObfuscateUserFeature.class);
	}
	
	/**
	 * Tests the BlacklistFeature
	 */
	@Test
	public void blacklistTest()
	{
		String args = "-b=Hello,pie,yes";
		BlacklistFeature blf = (BlacklistFeature) FeatureParser.parse(args);
		
		Message m1 = new Message(Instant.ofEpochSecond(1448470901), "username1", "Hello, this message contains the word, pie");
		Message m2 = new Message(Instant.ofEpochSecond(1448470905), "username2", "yes! the other message contained the word Hello too!");
		
		Message m1_redacted = blf.applyMessageFeature(m1);
		Message m2_redacted = blf.applyMessageFeature(m2);
		
		assertEquals(m1_redacted.content, "*redacted*, this message contains the word, *redacted*");
		assertEquals(m2_redacted.content, "*redacted*! the other message contained the word *redacted* too!");
	}
	
	/**
	 * Tests the KeywordFilterFeature
	 */
	@Test
	public void keywordFilterTest()
	{
		String args = "-k=sentence";
		KeywordFilterFeature kf = (KeywordFilterFeature) FeatureParser.parse(args);
		
		Message m1 = new Message(Instant.ofEpochSecond(1448470901), "username1", "gonna test this sentence");
		Message m2 = new Message(Instant.ofEpochSecond(1448470905), "username2", "random words");
		Message m3 = new Message(Instant.ofEpochSecond(1448470905), "username2", "going to test this sentence too");
		Message m4 = new Message(Instant.ofEpochSecond(1448470905), "username3", "Just another random sentence");
		
		ArrayList<Message> messages = new ArrayList<Message>();
		messages.add(m1);
		messages.add(m2);
		messages.add(m3);
		messages.add(m4);
		
		Conversation convo = new Conversation("Convo1", messages);
		
		Conversation updated_convo = kf.applyConversationFeature(convo);
		
		for(Message m : updated_convo.messages)
		{
			assertTrue(m.content.contains("sentence"));
		}
	}
	
	/**
	 * Tests the UserFilterFeature
	 */
	@Test
	public void userFilterTest()
	{
		String args = "-u=username2";
		UserFilterFeature uf = (UserFilterFeature) FeatureParser.parse(args);
		
		Message m1 = new Message(Instant.ofEpochSecond(1448470901), "username1", "Here's my phone number 01234567890");
		Message m2 = new Message(Instant.ofEpochSecond(1448470905), "username2", "You want my card number? 1234567891234567");
		Message m3 = new Message(Instant.ofEpochSecond(1448470905), "username2", "My phone number is 077-305-3756");
		Message m4 = new Message(Instant.ofEpochSecond(1448470905), "username3", "My card number is 1111-1111-1111-1111");
		
		ArrayList<Message> messages = new ArrayList<Message>();
		messages.add(m1);
		messages.add(m2);
		messages.add(m3);
		messages.add(m4);
		
		Conversation convo = new Conversation("Convo1", messages);
		
		Conversation updated_convo = uf.applyConversationFeature(convo);
		
		for(Message m : updated_convo.messages)
		{
			assertEquals(m.senderId, "username2");
		}
	}
	
	/**
	 * Tests the HideNumbersFeature
	 */
	@Test
	public void hideNumbersTest()
	{
		String args = "-h";
		HideNumbersFeature hf = (HideNumbersFeature) FeatureParser.parse(args);
		
		Message m1 = new Message(Instant.ofEpochSecond(1448470901), "username1", "Here's my phone number 01234567890");
		Message m2 = new Message(Instant.ofEpochSecond(1448470905), "username2", "You want my card number? 1234567891234567");
		Message m3 = new Message(Instant.ofEpochSecond(1448470905), "username2", "My phone number is 077-305-3756");
		Message m4 = new Message(Instant.ofEpochSecond(1448470905), "username1", "My card number is 1111-1111-1111-1111");
		
		Message m1_redacted = hf.applyMessageFeature(m1);
		Message m2_redacted = hf.applyMessageFeature(m2);
		Message m3_redacted = hf.applyMessageFeature(m3);
		Message m4_redacted = hf.applyMessageFeature(m4);
		
		assertEquals(m1_redacted.content, "Here's my phone number *redacted*");
		assertEquals(m2_redacted.content, "You want my card number? *redacted*");
		assertEquals(m3_redacted.content, "My phone number is *redacted*");
		assertEquals(m4_redacted.content, "My card number is *redacted*");
	}
	
	/**
	 * Tests the ObfuscateUserFeature
	 */
	@Test
	public void obfuscateUserTest()
	{
		String args = "-o";
		ObfuscateUserFeature of = (ObfuscateUserFeature) FeatureParser.parse(args);
		
		Message m1 = new Message(Instant.ofEpochSecond(1448470901), "username1", "Here's my phone number 01234567890");
		Message m2 = new Message(Instant.ofEpochSecond(1448470905), "username2", "You want my card number? 1234567891234567");
		Message m3 = new Message(Instant.ofEpochSecond(1448470905), "username2", "My phone number is 077-305-3756");
		Message m4 = new Message(Instant.ofEpochSecond(1448470905), "username1", "My card number is 1111-1111-1111-1111");
		
		ArrayList<Message> messages = new ArrayList<Message>();
		messages.add(m1);
		messages.add(m2);
		messages.add(m3);
		messages.add(m4);
		
		Conversation convo = new Conversation("Convo1", messages);
		
		Conversation updated_convo = of.applyConversationFeature(convo);
		
		assertEquals(updated_convo.messages.get(0).senderId, "User 0");
		assertEquals(updated_convo.messages.get(1).senderId, "User 1");
		assertEquals(updated_convo.messages.get(2).senderId, "User 1");
		assertEquals(updated_convo.messages.get(3).senderId, "User 0");
	}

}
