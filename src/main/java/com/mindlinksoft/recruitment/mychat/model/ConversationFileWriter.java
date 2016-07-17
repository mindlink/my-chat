package com.mindlinksoft.recruitment.mychat.model;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mindlinksoft.recruitment.mychat.bean.Conversation;
import com.mindlinksoft.recruitment.mychat.util.GSonHelper;

/**
 * Writes a conversation object into a file as specified in
 * ConversationExporterConfiguration
 */
public class ConversationFileWriter {

	private ConversationExporterConfiguration config_;
	private Path path_;
	private Gson gson_;
	private Conversation conversation_;

	public ConversationFileWriter(Conversation conversation, ConversationExporterConfiguration configuration) {
		conversation_ = conversation;
		config_ = configuration;
	}

	public void write() {
		path_ = Paths.get(config_.getOutputFile());
		warnFileExists();
		checkValidConversation();
		createGson();
		writeToFile();
	}

	private void checkValidConversation() {
		if (conversation_ == null)
			throw new RuntimeException("Cannot write null conversation");
		if (StringUtils.isBlank(conversation_.getName()))
			throw new RuntimeException("Please provide a name for the conversation");
	}

	private void writeToFile() {
		try (BufferedWriter writer = Files.newBufferedWriter(path_)) {
			writer.write(gson_.toJson(conversation_));
		} catch (Exception e) {
			throw new RuntimeException("Faled to write conversation to output file: " + e.getMessage());
		}

	}

	private void warnFileExists() {
		if (Files.isRegularFile(path_))
			System.out.println("Warning, output file exists and will be overwritten");
	}

	private void createGson() {
		GsonBuilder gsonBuilder = new GSonHelper().createGsonBuilder();
		gson_ = gsonBuilder.create();
	}

}
