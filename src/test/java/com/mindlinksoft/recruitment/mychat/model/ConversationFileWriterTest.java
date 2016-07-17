package com.mindlinksoft.recruitment.mychat.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mindlinksoft.recruitment.mychat.bean.Conversation;
import com.mindlinksoft.recruitment.mychat.enums.CommandLineArgument;
import com.mindlinksoft.recruitment.mychat.model.ConversationFileWriter;
import com.mindlinksoft.recruitment.mychat.util.GSonHelper;

public class ConversationFileWriterTest extends ConversationTestBase {

	private static final String OUT = "chat.json";

	private ConversationExporterConfiguration configuration_;

	private Gson gson_;

	@Before
	public void init() {
		GsonBuilder gsonBuilder = new GSonHelper().createGsonBuilder();
		gson_ = gsonBuilder.create();
		configuration_ = new ConversationExporterConfiguration();
		configuration_.set(CommandLineArgument.OUTPUT_FILE, OUT);
	}

	@Test(expected = RuntimeException.class)
	public void testNullConversation() {
		ConversationFileWriter w = createWriter(null);
		w.write();
	}

	@Test(expected = RuntimeException.class)
	public void testWriteToDir() {
		configuration_.set(CommandLineArgument.OUTPUT_FILE, "src");
		Conversation c = createConversation("title", createMessage(1, "bob", "hi"));
		ConversationFileWriter w = createWriter(c);
		w.write();
	}

	@Test(expected = RuntimeException.class)
	public void testNullTitle() {
		Conversation c = createConversation(null, createMessage(1, "bob", "hi"));
		ConversationFileWriter w = createWriter(c);
		w.write();
	}

	@Test(expected = RuntimeException.class)
	public void testEmptyTitle() {
		Conversation c = createConversation("", createMessage(1, "bob", "hi"));
		ConversationFileWriter w = createWriter(c);
		w.write();
	}

	@Test(expected = RuntimeException.class)
	public void testEmptyTitle2() {
		Conversation c = createConversation("  ", createMessage(1, "bob", "hi"));
		ConversationFileWriter w = createWriter(c);
		w.write();
	}

	@Test
	public void testNullMessages() {
		Conversation c = new Conversation("title", null);
		ConversationFileWriter w = createWriter(c);
		w.write();
		assertConversationEquals(c, getOutput());
	}

	@Test
	public void testSingleMessage() {
		Conversation c = createConversation("title", createMessage(1, "bob", "hi"));
		ConversationFileWriter w = createWriter(c);
		w.write();
		assertConversationEquals(c, getOutput());
	}

	@Test
	public void testMultiMessage() {
		Conversation c = createConversation("title", createMessage(1, "bob", "hi"), createMessage(2, "jack", "what?"),
				createMessage(3, "jane", "test"));
		ConversationFileWriter w = createWriter(c);
		w.write();
		assertConversationEquals(c, getOutput());
	}

	@Test
	public void testNewLineInTitle() {
		Conversation c = createConversation("title\ntitle!", createMessage(1, "bob", "hi"));
		ConversationFileWriter w = createWriter(c);
		w.write();
		assertConversationEquals(c, getOutput());
	}

	private ConversationFileWriter createWriter(Conversation conversation) {
		return new ConversationFileWriter(conversation, configuration_);
	}

	private Conversation getOutput() {
		try {
			String json = new String(Files.readAllBytes(Paths.get(OUT)));
			return gson_.fromJson(json, Conversation.class);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not read output file");
		}
	}

}
