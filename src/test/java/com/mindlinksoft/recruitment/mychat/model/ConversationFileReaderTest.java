package com.mindlinksoft.recruitment.mychat.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mindlinksoft.recruitment.mychat.bean.Conversation;
import com.mindlinksoft.recruitment.mychat.model.ConversationFileReader;

public class ConversationFileReaderTest {

	@Mock
	private ConversationExporterConfiguration config_;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test(expected = RuntimeException.class)
	public void testFileNotFound() {
		Mockito.when(config_.getInputFile()).thenReturn("aaa");
		ConversationFileReader reader = new ConversationFileReader(config_);
		reader.read();
	}

	@Test(expected = RuntimeException.class)
	public void testDirectory() {
		Mockito.when(config_.getInputFile()).thenReturn("src");
		ConversationFileReader reader = new ConversationFileReader(config_);
		reader.read();
	}

	@Test(expected = RuntimeException.class)
	public void testNullFile() {
		Mockito.when(config_.getInputFile()).thenReturn(null);
		ConversationFileReader reader = new ConversationFileReader(config_);
		reader.read();
	}

	@Test(expected = RuntimeException.class)
	public void testBlankFileName() {
		Mockito.when(config_.getInputFile()).thenReturn("");
		ConversationFileReader reader = new ConversationFileReader(config_);
		reader.read();
	}

	@Test(expected = RuntimeException.class)
	public void testEmptyFile() {
		Mockito.when(config_.getInputFile()).thenReturn("empty-file");
		ConversationFileReader reader = new ConversationFileReader(config_);
		reader.read();
	}

	@Test
	public void testOnlyTitle() {
		Mockito.when(config_.getInputFile()).thenReturn("single-line");
		ConversationFileReader reader = new ConversationFileReader(config_);
		Conversation conversation = reader.read();
		Assert.assertEquals("My Conversation", conversation.getName());
		Assert.assertTrue(conversation.getMessages().isEmpty());
	}

	@Test
	public void testValidFile() {
		Mockito.when(config_.getInputFile()).thenReturn("chat.txt");
		ConversationFileReader reader = new ConversationFileReader(config_);
		Conversation conversation = reader.read();
		Assert.assertEquals("My Conversation", conversation.getName());
		Assert.assertEquals(7, conversation.getMessages().size());
	}

}
