package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import com.mindlinksoft.recruitment.mychat.filter.BlackList;

/**
 * This class tests various aspects of the blacklist filter.
 * 
 * @author Mohamed Yusuf
 *
 */
public class BlackListTest {

	@Test
	public void testBlackListPie() {
		Set<Message> convo = new HashSet<Message>();	
		String[] filter = {"pie"};
		List<String> filterSet = new ArrayList<String>(Arrays.asList(filter));
		ConversationExporterConfiguration config = new GenerateMockConfiguration().genMockConfiguration();
		config.setWordsToBlacklist(filter);

		convo.addAll(new BlackList().filter(new GenerateMockMessages().genMockMessages(), config));
		
		if(convo.isEmpty())
			fail("Expected a conversation");
		
		for(Message mess : convo) {			
			String[] content = mess.getContent().split(" ");
			List<String> messContent = new ArrayList<String>(Arrays.asList(content));
			messContent.retainAll(filterSet);
			//System.out.println("Content after " + messContent);
			assertTrue(messContent.isEmpty());
		}
	}
	
	@Test
	public void testBlackListCard() {
		Set<Message> convo = new HashSet<Message>();	
		String[] filter = {"card"};
		List<String> filterSet = new ArrayList<String>(Arrays.asList(filter));
		ConversationExporterConfiguration config = new GenerateMockConfiguration().genMockConfiguration();
		config.setWordsToBlacklist(filter);

		convo.addAll(new BlackList().filter(new GenerateMockMessages().genMockMessages(), config));
		
		if(convo.isEmpty())
			fail("Expected a conversation");
		
		for(Message mess : convo) {			
			String[] content = mess.getContent().split(" ");
			List<String> messContent = new ArrayList<String>(Arrays.asList(content));
			messContent.retainAll(filterSet);
			//System.out.println("Content after " + messContent);
			assertTrue(messContent.isEmpty());
		}
	}
	
	@Test
	public void testBlackListAll() {
		Set<Message> convo = new HashSet<Message>();	
		String[] filter = {"all"};
		List<String> filterSet = new ArrayList<String>(Arrays.asList(filter));
		ConversationExporterConfiguration config = new GenerateMockConfiguration().genMockConfiguration();
		config.setWordsToBlacklist(filter);

		convo.addAll(new BlackList().filter(new GenerateMockMessages().genMockMessages(), config));
		
		if(convo.isEmpty())
			fail("Expected a conversation");
		
		for(Message mess : convo) {			
			String[] content = mess.getContent().split(" ");
			List<String> messContent = new ArrayList<String>(Arrays.asList(content));
			messContent.retainAll(filterSet);
			//System.out.println("Content after " + messContent);
			assertTrue(messContent.isEmpty());
		}
	}
	
	@Test
	public void testBlackListHow() {
		Set<Message> convo = new HashSet<Message>();	
		String[] filter = {"how"};
		List<String> filterSet = new ArrayList<String>(Arrays.asList(filter));
		ConversationExporterConfiguration config = new GenerateMockConfiguration().genMockConfiguration();
		config.setWordsToBlacklist(filter);

		convo.addAll(new BlackList().filter(new GenerateMockMessages().genMockMessages(), config));
		
		if(convo.isEmpty())
			fail("Expected a conversation");
		
		for(Message mess : convo) {			
			String[] content = mess.getContent().split(" ");
			List<String> messContent = new ArrayList<String>(Arrays.asList(content));
			messContent.retainAll(filterSet);
			//System.out.println("Content after " + messContent);
			assertTrue(messContent.isEmpty());
		}
	}
	
	@Test
	public void testBlackListAsk() {
		Set<Message> convo = new HashSet<Message>();	
		String[] filter = {"ask"};
		List<String> filterSet = new ArrayList<String>(Arrays.asList(filter));
		ConversationExporterConfiguration config = new GenerateMockConfiguration().genMockConfiguration();
		config.setWordsToBlacklist(filter);

		convo.addAll(new BlackList().filter(new GenerateMockMessages().genMockMessages(), config));
		
		if(convo.isEmpty())
			fail("Expected a conversation");
		
		for(Message mess : convo) {			
			String[] content = mess.getContent().split(" ");
			List<String> messContent = new ArrayList<String>(Arrays.asList(content));
			messContent.retainAll(filterSet);
			//System.out.println("Content after " + messContent);
			assertTrue(messContent.isEmpty());
		}
	}
	
	@Test
	public void testBlackList_1() {
		Set<Message> convo = new HashSet<Message>();	
		String[] filter = {"ask"};
		ConversationExporterConfiguration config = new GenerateMockConfiguration().genMockConfiguration();
		config.setWordsToBlacklist(filter);

		convo.addAll(new BlackList().filter(new GenerateMockMessages().genMockMessages(), config));
		
		if(convo.isEmpty())
			fail("Expected a conversation");
		
		for(Message mess : convo) {			
			String[] content = mess.getContent().split(" ");
			List<String> messContent = new ArrayList<String>(Arrays.asList(content));
			assertTrue(messContent.contains("*REDACTED*"));
		}
	}
	
	@Test
	public void testBlackList_2() {
		Set<Message> convo = new HashSet<Message>();	
		String[] filter = {"how"};
		ConversationExporterConfiguration config = new GenerateMockConfiguration().genMockConfiguration();
		config.setWordsToBlacklist(filter);

		convo.addAll(new BlackList().filter(new GenerateMockMessages().genMockMessages(), config));
		
		if(convo.isEmpty())
			fail("Expected a conversation");
		
		for(Message mess : convo) {			
			String[] content = mess.getContent().split(" ");
			List<String> messContent = new ArrayList<String>(Arrays.asList(content));
			assertTrue(messContent.contains("*REDACTED*"));
		}
	}
	
	@Test
	public void testBlackList_3() {
		Set<Message> convo = new HashSet<Message>();	
		String[] filter = {"like"};
		ConversationExporterConfiguration config = new GenerateMockConfiguration().genMockConfiguration();
		config.setWordsToBlacklist(filter);

		convo.addAll(new BlackList().filter(new GenerateMockMessages().genMockMessages(), config));
		
		if(convo.isEmpty())
			fail("Expected a conversation");
		
		for(Message mess : convo) {			
			String[] content = mess.getContent().split(" ");
			List<String> messContent = new ArrayList<String>(Arrays.asList(content));
			assertTrue(messContent.contains("*REDACTED*"));
		}
	}
	
	@Test
	public void testBlackList_4() {
		Set<Message> convo = new HashSet<Message>();	
		String[] filter = {"you"};
		ConversationExporterConfiguration config = new GenerateMockConfiguration().genMockConfiguration();
		config.setWordsToBlacklist(filter);

		convo.addAll(new BlackList().filter(new GenerateMockMessages().genMockMessages(), config));
		
		if(convo.isEmpty())
			fail("Expected a conversation");
		
		for(Message mess : convo) {			
			String[] content = mess.getContent().split(" ");
			List<String> messContent = new ArrayList<String>(Arrays.asList(content));
			assertTrue(messContent.contains("*REDACTED*"));
		}
	}
	
	@Test
	public void testBlackList_5() {
		Set<Message> convo = new HashSet<Message>();	
		String[] filter = {"me"};
		ConversationExporterConfiguration config = new GenerateMockConfiguration().genMockConfiguration();
		config.setWordsToBlacklist(filter);

		convo.addAll(new BlackList().filter(new GenerateMockMessages().genMockMessages(), config));
		
		if(convo.isEmpty())
			fail("Expected a conversation");
		
		for(Message mess : convo) {			
			String[] content = mess.getContent().split(" ");
			List<String> messContent = new ArrayList<String>(Arrays.asList(content));
			assertTrue(messContent.contains("*REDACTED*"));
		}
	}

}
