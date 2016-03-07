/**
 * 
 */
package com.mindlinksoft.recruitment.mychat.filters;

import static org.junit.Assert.*;

import com.mindlinksoft.recruitment.mychat.Message;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for all the types of ConversationFilter.
 */

public class ConversationFiltersTest {
	
	ArrayList<Message> messages = new ArrayList<Message>();
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		messages.add(new Message(Instant.EPOCH, "alice", "Hello bob!"));
        messages.add(new Message(Instant.EPOCH, "bob", "Hello Alice!"));
        messages.add(new Message(Instant.EPOCH, "alice", "Hum"));
        messages.add(new Message(Instant.EPOCH, "alice", "How are you?"));
        messages.add(new Message(Instant.EPOCH, "cristina", "This is a phone number: +441613532462"));
        messages.add(new Message(Instant.EPOCH, "cristina", "And this is a visa credit card number: 4111111111111111"));
        messages.add(new Message(Instant.EPOCH, "cristina", "And this is a master card credit card number: 5105105105105100"));
        messages.add(new Message(Instant.EPOCH, "cristina", "This is a shorter number: 5105"));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

    @Test
    public void testUserFilterAlice() {
        Collection<Message> expResult = new ArrayList<>();
        expResult.add(messages.get(0));
        expResult.add(messages.get(2));
        expResult.add(messages.get(3));
        
        UserFilter instance = new UserFilter("alice");
        Collection<Message> resultexpResult = instance.useFilter(messages);
        assertEquals(expResult, resultexpResult);
    }
    
    @Test
    public void testUserFilterBob() {
        Collection<Message> expResult = new ArrayList<>();
        expResult.add(messages.get(1));
        
        UserFilter instance = new UserFilter("bob");
        Collection<Message> resultexpResult = instance.useFilter(messages);
        assertEquals(expResult, resultexpResult);
    }
    
    @Test
    public void testKeywordFilterHello() throws Exception {
        Collection<Message> expResult = new ArrayList<>();
        expResult.add(messages.get(0));
        expResult.add(messages.get(1));
        
        KeywordFilter instance = new KeywordFilter("Hello");
        Collection<Message> resultexpResult = instance.useFilter(messages);
        assertEquals(expResult, resultexpResult);
    }
    
    @Test
    public void testBlacklistFilterHello() throws Exception {
        String word = "Hello";
        String redacted = "\\*redacted\\*";
        
        List<Message> expResult = new ArrayList<>(messages);
        expResult.get(0).setContent(messages.get(0).getContent().replace(word, redacted));
        expResult.get(1).setContent(messages.get(1).getContent().replace(word, redacted));
        
        String [] blacklist = word.split(",");
        BlacklistFilter instance = new BlacklistFilter(Arrays.asList(blacklist));
        Collection<Message> resultexpResult = instance.useFilter(messages);
        assertEquals(expResult, resultexpResult);
    }
    
    @Test
    public void testCreditCardPhoneFilter() throws Exception {
        String redacted = "\\*redacted\\*";
        String phone = "+441613532462";
        String visaCreditCard = "4111111111111111";
        String mcCreditCard = "5105105105105100";
        
        List<Message> expResult = new ArrayList<>(messages);
        expResult.get(4).setContent(messages.get(4).getContent().replace(phone, redacted));
        expResult.get(5).setContent(messages.get(5).getContent().replace(visaCreditCard, redacted));
        expResult.get(6).setContent(messages.get(6).getContent().replace(mcCreditCard, redacted));
        expResult.get(7).setContent(messages.get(7).getContent());
        CreditCardPhoneFilter instance = new CreditCardPhoneFilter();
        Collection<Message> resultexpResult = instance.useFilter(messages);
        assertEquals(expResult, resultexpResult);
    }
    
    @Test
    public void testObfuscateFilter() throws Exception {
        String user1 = "User #1";
        String user2 = "User #2";
        
        List<Message> expResult = new ArrayList<>(messages);
        expResult.get(0).setSenderId(user1);
        expResult.get(1).setSenderId(user2);
        expResult.get(2).setSenderId(user1);
        expResult.get(3).setSenderId(user1);
        ObfuscateFilter instance = new ObfuscateFilter();
        Collection<Message> resultexpResult = instance.useFilter(messages);
        assertEquals(expResult, resultexpResult);
    }

}
