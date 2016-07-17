package com.mindlinksoft.recruitment.mychat.model;

import static org.junit.Assert.assertEquals;

import java.time.Instant;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mindlinksoft.recruitment.mychat.bean.Message;
import com.mindlinksoft.recruitment.mychat.enums.CommandLineArgument;
import com.mindlinksoft.recruitment.mychat.model.InvalidMessageException;
import com.mindlinksoft.recruitment.mychat.model.MessageParser;

public class MessageParserTest extends ConversationTestBase {

	private ConversationExporterConfiguration config_;
	private MessageParser parser_;
	private Message m_;

	@Before
	public void init() {
		config_ = new ConversationExporterConfiguration();
		parser_ = new MessageParser();
		m_ = createMessage(1, "john", "hi! how are you!");
	}

	@Test
	public void testParseValid() {
		Message m = parser_.parse("1448470901 bob Hello there!");
		assertEquals(m.getTimestamp(), Instant.ofEpochSecond(1448470901));
		assertEquals(m.getSenderId(), "bob");
		assertEquals(m.getContent(), "Hello there!");
	}

	@Test
	public void testParseValidNumberUsername() {
		Message m = parser_.parse("1448470 901 bob Hello there!");
		assertEquals(m.getTimestamp(), Instant.ofEpochSecond(1448470));
		assertEquals(m.getSenderId(), "901");
		assertEquals(m.getContent(), "bob Hello there!");
	}

	@Test(expected = InvalidMessageException.class)
	public void testParseInvalidTs() {
		parser_.parse("-1 bob Hello there!");
	}

	@Test(expected = InvalidMessageException.class)
	public void testParseInvalidNoTs() {
		parser_.parse("bob Hello there!");
	}

	@Test(expected = InvalidMessageException.class)
	public void testParseNull() {
		parser_.parse(null);
	}

	@Test
	public void testFilterNoOption() {
		Assert.assertTrue(parser_.filterByUser(config_, m_));
	}

	@Test
	public void testFilterUserNoMatch() {
		config_.set(CommandLineArgument.FILTER_USER, "boris");
		Assert.assertFalse(parser_.filterByUser(config_, m_));
	}

	@Test
	public void testFilterUserNoMatchCaseSensitive() {
		config_.set(CommandLineArgument.FILTER_USER, "JOHN");
		Assert.assertFalse(parser_.filterByUser(config_, m_));
	}

	@Test
	public void testFilterUserMatch() {
		config_.set(CommandLineArgument.FILTER_USER, "john");
		Assert.assertTrue(parser_.filterByUser(config_, m_));
	}

	@Test
	public void testFilterKeywordNoOption() {
		Assert.assertTrue(parser_.filterByKeyword(config_, m_));
	}

	@Test
	public void testFilterKeywordNoMatch() {
		config_.set(CommandLineArgument.FILTER_KEYWORD, "boris");
		Assert.assertFalse(parser_.filterByKeyword(config_, m_));
		config_.set(CommandLineArgument.FILTER_KEYWORD, "ou");
		Assert.assertFalse(parser_.filterByKeyword(config_, m_));
		config_.set(CommandLineArgument.FILTER_KEYWORD, "re");
		Assert.assertFalse(parser_.filterByKeyword(config_, m_));
		config_.set(CommandLineArgument.FILTER_KEYWORD, "e");
		Assert.assertFalse(parser_.filterByKeyword(config_, m_));
		config_.set(CommandLineArgument.FILTER_KEYWORD, "!");
		Assert.assertFalse(parser_.filterByKeyword(config_, m_));
		config_.set(CommandLineArgument.FILTER_KEYWORD, ".*");
		Assert.assertFalse(parser_.filterByKeyword(config_, m_));
		config_.set(CommandLineArgument.FILTER_KEYWORD, ".+");
		Assert.assertFalse(parser_.filterByKeyword(config_, m_));
	}

	@Test
	public void testFilterKeywordMatch() {
		config_.set(CommandLineArgument.FILTER_KEYWORD, "HI");
		Assert.assertTrue(parser_.filterByKeyword(config_, m_));
		config_.set(CommandLineArgument.FILTER_KEYWORD, "how");
		Assert.assertTrue(parser_.filterByKeyword(config_, m_));
		config_.set(CommandLineArgument.FILTER_KEYWORD, "are");
		Assert.assertTrue(parser_.filterByKeyword(config_, m_));
		config_.set(CommandLineArgument.FILTER_KEYWORD, "you");
		Assert.assertTrue(parser_.filterByKeyword(config_, m_));
	}

	@Test
	public void testContainsKeywordNullContent() {
		config_.set(CommandLineArgument.FILTER_KEYWORD, "HI");
		Message m = createMessage(1, "john", null);
		Assert.assertFalse(parser_.filterByKeyword(config_, m));
	}

	@Test
	public void testContainsKeywordEmptyContent() {
		config_.set(CommandLineArgument.FILTER_KEYWORD, "HI");
		Message m = createMessage(1, "john", "");
		Assert.assertFalse(parser_.filterByKeyword(config_, m));
	}

	@Test
	public void testContainsKeywordEmptyKeyword() {
		config_.set(CommandLineArgument.FILTER_KEYWORD, "");
		Assert.assertTrue(parser_.filterByKeyword(config_, m_));
		config_.set(CommandLineArgument.FILTER_KEYWORD, "   ");
		Assert.assertTrue(parser_.filterByKeyword(config_, m_));
	}

	@Test
	public void testContainsKeywordRegex() {
		Message m = createMessage(1, "john", "hi");
		Assert.assertFalse(parser_.containsKeyword(m, ".*"));
		Assert.assertFalse(parser_.containsKeyword(m, ".+"));
	}

	@Test
	public void testContainsKeywordMatch() {
		Assert.assertTrue(parser_.containsKeyword(createMessage(1, "john", "hi"), "hi"));
		Assert.assertTrue(parser_.containsKeyword(createMessage(1, "john", "hi."), "hi"));
		Assert.assertTrue(parser_.containsKeyword(createMessage(1, "john", "hi!"), "hi"));
		Assert.assertTrue(parser_.containsKeyword(createMessage(1, "john", ".hi!"), "hi"));
		Assert.assertTrue(parser_.containsKeyword(createMessage(1, "john", ".hi"), "hi"));
		Assert.assertTrue(parser_.containsKeyword(createMessage(1, "john", "hey .hi! hey"), "hi"));
		Assert.assertTrue(parser_.containsKeyword(createMessage(1, "john", "..hi.."), "hi"));
		Assert.assertTrue(parser_.containsKeyword(createMessage(1, "john", "hi,"), "hi"));
		Assert.assertTrue(parser_.containsKeyword(createMessage(1, "john", "Hi,"), "hi"));
		Assert.assertTrue(parser_.containsKeyword(createMessage(1, "john", "HI,"), "hi"));
		Assert.assertTrue(parser_.containsKeyword(createMessage(1, "john", "hI,"), "hi"));
	}

	@Test
	public void testgetKeywordsAsRegexOr() {
		Assert.assertEquals("\\Qword\\E", parser_.getKeywordsAsRegexOr("word"));
		Assert.assertEquals("\\Qword\\E|\\Q2\\E", parser_.getKeywordsAsRegexOr("word 2"));
		Assert.assertEquals("\\Qword\\E|\\Q2\\E|\\Qend\\E", parser_.getKeywordsAsRegexOr("word 2 end"));
	}

	@Test
	public void testHideKeywordsNoMatch() {
		Message m = createMessage(1, "john", "hi, my name is john! What is your name?");
		assertMessageEquals(m, parser_.hideKeywords(m, null));
		assertMessageEquals(m, parser_.hideKeywords(m, ""));
		assertMessageEquals(m, parser_.hideKeywords(m, "ame"));
		assertMessageEquals(m, parser_.hideKeywords(m, "boris"));
		assertMessageEquals(m, parser_.hideKeywords(m, "black list"));
	}

	@Test
	public void testHideKeywordsMatch() {
		Message m = createMessage(1, "john", "hi, my name is john! What is your NAME?");
		assertEquals("*redacted*, my name is john! What is your NAME?", parser_.hideKeywords(m, "HI").getContent());
		assertEquals("hi, my *redacted* is john! What is your *redacted*?",
				parser_.hideKeywords(m, "name").getContent());
		assertEquals("*redacted*, my *redacted* is *redacted*! What is your *redacted*?",
				parser_.hideKeywords(m, "hi NAME jOhn").getContent());
	}

	@Test
	public void testFilterBlacklistNoOption() {
		assertEquals(m_, parser_.filterBlacklistedWords(config_, m_));
	}

	@Test
	public void testFilterCreditCardPhoneNumberNoOption() {
		Message m = createMessage(1, "john", "my master card is 5555555555554444");
		assertEquals(m, parser_.filterCreditCards(config_, m));
		m = createMessage(1, "john", "my phone is 99777888");
		assertEquals(m, parser_.filterCreditCards(config_, m));
	}

	@Test
	public void testFilterCreditCard() {
		config_.set(CommandLineArgument.HIDE_CREDIT_CARD_AND_PHONE, "");

		testCreditCard("my master card is 5555555555554444", "my master card is *redacted*");
		testCreditCard("my visa is 4012888888881881!", "my visa is *redacted*!");
		testCreditCard("my american express is (371449635398431)", "my american express is (*redacted*)");
		testCreditCard("3566002020360505 is my jcb", "*redacted* is my jcb");

		testCreditCard("invalid visa is 3012888888881881", null);
		testCreditCard("3666002020360505 is invalid", null);
		testCreditCard("my phone is 99237623", null);
		testCreditCard("my phone is 18447595433", null);
		testCreditCard("my dob is 22 12 1954", null);
		testCreditCard("my ssn is 721-07-4426", null);
	}

	private void testCreditCard(String message, String expected) {
		Message m1 = createMessage(1, "john", message);
		Message m2 = parser_.filterCreditCards(config_, m1);
		assertEquals(expected == null ? message : expected, m2.getContent());
	}

	@Test
	public void testFilterPhone() {
		config_.set(CommandLineArgument.HIDE_CREDIT_CARD_AND_PHONE, "");
		testPhone("my phone is 99777888", "my phone is *redacted*");
		testPhone("is +44 1632 960185", "is *redacted*");
		testPhone("call me at 99 777 888", "call me at *redacted*");
		testPhone("99 people call me at 1-800-555-6443 tonight", "99 people call me at *redacted* tonight");
		testPhone("my master card is 5555555555554444", null);
	}

	private void testPhone(String message, String expected) {
		Message m1 = createMessage(1, "john", message);
		Message m2 = parser_.filterPhoneNumbers(config_, m1);
		assertEquals(expected == null ? message : expected, m2.getContent());
	}

	@Test
	public void testFilterBlacklist() {
		config_.set(CommandLineArgument.BLACKLIST, "hi");
		testBlacklist("hi", "*redacted*");
		testBlacklist("hi!", "*redacted*!");
		testBlacklist("hi guys, hi, i said hi!", "*redacted* guys, *redacted*, i said *redacted*!");

		testBlacklist("hit", null);
		testBlacklist("I'm hit!", null);
	}

	private void testBlacklist(String message, String expected) {
		Message m1 = createMessage(1, "john", message);
		Message m2 = parser_.filterBlacklistedWords(config_, m1);
		assertEquals(expected == null ? message : expected, m2.getContent());
	}

}
