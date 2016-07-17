package com.mindlinksoft.recruitment.mychat.model;

import java.util.List;
import java.util.stream.Collectors;

import com.mindlinksoft.recruitment.mychat.bean.Message;

/**
 * Filters a list of messages as per the ConversationExporterConfiguration
 * object
 */
public class MessageFilterBuilder {

	private List<Message> messages_;
	private ConversationExporterConfiguration config_;
	private MessageParser parser_;
	private MessageSenderIdObfuscator obfuscator_;

	public MessageFilterBuilder(ConversationExporterConfiguration config, List<Message> messages) {
		config_ = config;
		messages_ = messages;
		parser_ = new MessageParser();
		obfuscator_ = new MessageSenderIdObfuscator();
	}

	public List<Message> build() {
		return messages_.stream() //
				.filter(this::filterByUser) //
				.filter(this::filterByKeyword) //
				.map(this::filterBlacklistedWords) //
				.map(this::filterPhoneNumbers) //
				.map(this::filterCreditCards) //
				.map(this::obfucateSender) //
				.collect(Collectors.toList());
	}

	private Message filterCreditCards(Message m) {
		return parser_.filterCreditCards(config_, m);
	}

	private Message filterPhoneNumbers(Message m) {
		return parser_.filterPhoneNumbers(config_, m);
	}

	private Message filterBlacklistedWords(Message m) {
		return parser_.filterBlacklistedWords(config_, m);
	}

	private boolean filterByKeyword(Message m) {
		return parser_.filterByKeyword(config_, m);
	}

	private boolean filterByUser(Message m) {
		return parser_.filterByUser(config_, m);
	}

	private Message obfucateSender(Message m) {
		if (!config_.isObfuscateSender())
			return m;
		return obfuscator_.obfuscate(m);
	}

}
