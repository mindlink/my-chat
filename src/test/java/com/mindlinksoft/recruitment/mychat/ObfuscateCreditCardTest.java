package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import com.mindlinksoft.recruitment.mychat.filter.ObfuscateCreditCard;

/**
 * This class tests the obfuscation of credit card details.
 * 
 * @author Mohamed Yusuf
 *
 */
public class ObfuscateCreditCardTest {

	@Test
	public void testStandardCreditCard() {
		Set<Message> convo = new HashSet<Message>();	
		String[] filter = {"1234 1234 1234 1234"};
		List<String> filterSet = new ArrayList<String>(Arrays.asList(filter));

		convo.addAll(new ObfuscateCreditCard().filter(new GenerateMockMessages().genMockMessages(), new GenerateMockConfiguration().genMockConfiguration()));
		
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
	public void testMergedCreditCard() {
		Set<Message> convo = new HashSet<Message>();	
		String[] filter = {"1234123412341234"};
		List<String> filterSet = new ArrayList<String>(Arrays.asList(filter));

		convo.addAll(new ObfuscateCreditCard().filter(new GenerateMockMessages().genMockMessages(), new GenerateMockConfiguration().genMockConfiguration()));
		
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
	public void testDashedCreditCard() {
		Set<Message> convo = new HashSet<Message>();	
		String[] filter = {"1234-1234-1234-1234"};
		List<String> filterSet = new ArrayList<String>(Arrays.asList(filter));

		convo.addAll(new ObfuscateCreditCard().filter(new GenerateMockMessages().genMockMessages(), new GenerateMockConfiguration().genMockConfiguration()));
		
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

}
