package com.mindlinksoft.recruitment.mychat.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.mindlinksoft.recruitment.mychat.bean.Conversation;
import com.mindlinksoft.recruitment.mychat.bean.Message;

/**
 * Reads the input file specified in the ConversationExporterConfiguration
 * object and uses its contents to create a Conversation object, applying
 * filters as per the configuration object
 */
public class ConversationFileReader {

	private ConversationExporterConfiguration config_;
	private List<String> allLines_;
	private String title_;
	private List<String> messageLines_;
	private MessageParser parser_;
	private List<Message> messages_;
	private List<Message> filteredMessages_;

	public ConversationFileReader(ConversationExporterConfiguration config) {
		config_ = config;
		parser_ = new MessageParser();
	}

	public Conversation read() {
		readInputFile();
		checkNotEmpty();
		splitTitleAndMessageLines();
		createMessages();
		filterMessages();
		return new Conversation(title_, filteredMessages_);
	}

	private void createMessages() {
		messages_ = new ArrayList<Message>();
		for (int i = 0; i < messageLines_.size(); i++)
			messages_.add(parser_.parse(messageLines_.get(i)));
	}

	private void filterMessages() {
		filteredMessages_ = new MessageFilterBuilder(config_, messages_).build();
	}

	private void splitTitleAndMessageLines() {
		title_ = allLines_.get(0);
		messageLines_ = allLines_.subList(1, allLines_.size());
	}

	private void checkNotEmpty() {
		if (allLines_ == null || allLines_.isEmpty())
			throw new RuntimeException("Failed to read input file.");
	}

	private void readInputFile() {
		if (StringUtils.isBlank(config_.getInputFile()))
			throw new RuntimeException("No input file provided.");
		try {
			allLines_ = Files.lines(Paths.get(config_.getInputFile())).collect(Collectors.toList());
		} catch (IOException e) {
			throw new RuntimeException("Failed to read input file.");
		}
	}
}
