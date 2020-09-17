package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import com.mindlinksoft.recruitment.mychat.filter.FilterWord;

/**
 * This class tests various aspects of the word filter.
 * 
 * @author Mohamed Yusuf
 *
 */
public class FilterWordTest {

	@Test
	public void testFilterWordGood() {
		Set<Message> convo = new HashSet<Message>();	
		String[] filter = {"good"};
		List<String> filterSet = new ArrayList<String>(Arrays.asList(filter));

		convo.addAll(new FilterWord().filter(new GenerateMockMessages().genMockMessages(), filter));
		
		if(convo.isEmpty())
			fail("Expected a conversation");
		
		for(Message mess : convo) {			
			String[] content = mess.getContent().split(" ");
			List<String> messContent = new ArrayList<String>(Arrays.asList(content));
			messContent.retainAll(filterSet);
			//System.out.println("Content after " + messContent);
			assertFalse(messContent.isEmpty());
		}
	}
	
	@Test
	public void testFilterWordHello() {
		Set<Message> convo = new HashSet<Message>();	
		String[] filter = {"Hello"};
		List<String> filterSet = new ArrayList<String>(Arrays.asList(filter));

		convo.addAll(new FilterWord().filter(new GenerateMockMessages().genMockMessages(), filter));
		
		if(convo.isEmpty())
			fail("Expected a conversation");
		
		for(Message mess : convo) {			
			String[] content = mess.getContent().split(" ");
			List<String> messContent = new ArrayList<String>(Arrays.asList(content));
			messContent.retainAll(filterSet);
			//System.out.println("Content after " + messContent);
			assertFalse(messContent.isEmpty());
		}
	}
	
	@Test
	public void testFilterWordAll() {
		Set<Message> convo = new HashSet<Message>();	
		String[] filter = {"all"};
		List<String> filterSet = new ArrayList<String>(Arrays.asList(filter));

		convo.addAll(new FilterWord().filter(new GenerateMockMessages().genMockMessages(), filter));
		
		if(convo.isEmpty())
			fail("Expected a conversation");
		
		for(Message mess : convo) {			
			String[] content = mess.getContent().split(" ");
			List<String> messContent = new ArrayList<String>(Arrays.asList(content));
			messContent.retainAll(filterSet);
			//System.out.println("Content after " + messContent);
			assertFalse(messContent.isEmpty());
		}
	}
	
	@Test
	public void testFilterWordMultiple_1() {
		Set<Message> convo = new HashSet<Message>();	
		String[] filter = {"all", "Hello"};
		List<String> filterSet = new ArrayList<String>(Arrays.asList(filter));

		convo.addAll(new FilterWord().filter(new GenerateMockMessages().genMockMessages(), filter));
		
		if(convo.isEmpty())
			fail("Expected a conversation");
		
		for(Message mess : convo) {			
			String[] content = mess.getContent().split(" ");
			List<String> messContent = new ArrayList<String>(Arrays.asList(content));
			messContent.retainAll(filterSet);
			//System.out.println("Content after " + messContent);
			assertFalse(messContent.isEmpty());
		}
	}
	
	@Test
	public void testFilterWordMultiple_2() {
		Set<Message> convo = new HashSet<Message>();	
		String[] filter = {"Hell", "one", "seemed"};
		List<String> filterSet = new ArrayList<String>(Arrays.asList(filter));

		convo.addAll(new FilterWord().filter(new GenerateMockMessages().genMockMessages(), filter));
		
		if(convo.isEmpty())
			fail("Expected a conversation");
		
		for(Message mess : convo) {			
			String[] content = mess.getContent().split(" ");
			List<String> messContent = new ArrayList<String>(Arrays.asList(content));
			messContent.retainAll(filterSet);
			//System.out.println("Content after " + messContent);
			assertFalse(messContent.isEmpty());
		}
	}
	
	@Test
	public void testFilterWordMultiple_3() {
		Set<Message> convo = new HashSet<Message>();	
		String[] filter = {"card", "1234", "pie"};
		List<String> filterSet = new ArrayList<String>(Arrays.asList(filter));

		convo.addAll(new FilterWord().filter(new GenerateMockMessages().genMockMessages(), filter));
		
		if(convo.isEmpty())
			fail("Expected a conversation");
		
		for(Message mess : convo) {			
			String[] content = mess.getContent().split(" ");
			List<String> messContent = new ArrayList<String>(Arrays.asList(content));
			messContent.retainAll(filterSet);
			//System.out.println("Content after " + messContent);
			assertFalse(messContent.isEmpty());
		}
	}
	
	@Test
	public void testFilterWordMultiple_4() {
		Set<Message> convo = new HashSet<Message>();	
		String[] filter = {"buying", "card", "good"};
		List<String> filterSet = new ArrayList<String>(Arrays.asList(filter));

		convo.addAll(new FilterWord().filter(new GenerateMockMessages().genMockMessages(), filter));
		
		if(convo.isEmpty())
			fail("Expected a conversation");
		
		for(Message mess : convo) {			
			String[] content = mess.getContent().split(" ");
			List<String> messContent = new ArrayList<String>(Arrays.asList(content));
			messContent.retainAll(filterSet);
			//System.out.println("Content after " + messContent);
			assertFalse(messContent.isEmpty());
		}
	}
	
	@Test
	public void testFilterWordMultipleIncorrect_1() {
		Set<Message> convo = new HashSet<Message>();	
		String[] filter = {"feeder", "smith", "dance"};

		convo.addAll(new FilterWord().filter(new GenerateMockMessages().genMockMessages(), filter));
		assertTrue(convo.isEmpty());		
	}
	
	@Test
	public void testFilterWordMultipleIncorrect_2() {
		Set<Message> convo = new HashSet<Message>();	
		String[] filter = {"time", "dam", "alex"};

		convo.addAll(new FilterWord().filter(new GenerateMockMessages().genMockMessages(), filter));
		assertTrue(convo.isEmpty());		
	}
	
	@Test
	public void testFilterWordMultipleIncorrect_3() {
		Set<Message> convo = new HashSet<Message>();	
		String[] filter = {"remote", "control", "vertibrate"};

		convo.addAll(new FilterWord().filter(new GenerateMockMessages().genMockMessages(), filter));
		assertTrue(convo.isEmpty());		
	}
	
	@Test
	public void testFilterWordMultipleIncorrect_4() {
		Set<Message> convo = new HashSet<Message>();	
		String[] filter = {"film", "steam", "fallout"};

		convo.addAll(new FilterWord().filter(new GenerateMockMessages().genMockMessages(), filter));
		assertTrue(convo.isEmpty());		
	}
}
