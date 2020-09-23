package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.binary.Base64;

public class MessageFilters {

	private List<Message> messages;
	private ConversationExporterConfiguration configuration;
	private final String redacted = "*redacted*";

	/**
	 * Groups all the words of a message's content into one string.
	 * 
	 * @param messages - A List of messages
	 * @param config   - The exporter configuration - tells us which filters have
	 *                 been enabled.
	 * @return this.messages - The list of messages after all enabled filters haven
	 *         been applied.
	 */
	public List<Message> filterMessages(List<Message> messages, ConversationExporterConfiguration config) {
		this.messages = messages;
		this.configuration = config;
		filterMessagesByUser();
		filterMessagesByKeyword();
		blacklistMessages();
		hidePersonalDetails();
		obfuscateUsers();
		return this.messages;
	}

	/**
	 * Filters messages by a specific user set in the configuration. Only messages
	 * sent by the specific user will be stored in the messages field.
	 */
	private void filterMessagesByUser() {
		List<Message> postFilterMessages = new ArrayList<Message>();
		String userFilter = configuration.getUserFilter();
		if (userFilter.equals(ConversationExporterConfiguration.NO_FILTER)) {
			return; // This function does nothing if the user has not requested this filter.
		}

		for (Message message : messages) {
			if (message.senderId.equals(userFilter)) {
				postFilterMessages.add(message);
			}
		}
		messages = postFilterMessages;
	}

	/**
	 * Filters messages by a specific keyword set in the configuration. Only
	 * messages containing the specific keyword will be stored in the messages
	 * field.
	 */
	private void filterMessagesByKeyword() {
		List<Message> postFilterMessages = new ArrayList<Message>();
		String keywordFilter = configuration.getKeywordFilter();
		if (keywordFilter.equals(ConversationExporterConfiguration.NO_FILTER)) {
			return; // This function does nothing if the user has not requested this filter.
		}

		for (Message message : messages) {
			if (message.content.contains(keywordFilter)) {
				postFilterMessages.add(message);
			}
		}
		messages = postFilterMessages;
	}

	/**
	 * Redacts a specific word (set in the configuration), if it appears in any
	 * message content.
	 */
	private void blacklistMessages() {
		String blacklist = configuration.getBlacklist();
		if (blacklist.equals(ConversationExporterConfiguration.NO_FILTER)) {
			return; // This function does nothing if the user has not requested this filter.
		}

		String regexBlacklist = "\\b" + blacklist + "\\b";
		for (Message message : messages) {
			message.content = message.content.replaceAll(regexBlacklist, redacted);
		}
	}

	/**
	 * Redacts any credit card numbers or UK mobile numbers found in any message
	 * content.
	 */
	private void hidePersonalDetails() {
		boolean hideDetails = configuration.isHidePersonalDeatilsOn();
		if (hideDetails) {
			final String creditCardRegEx = "([0-9]{4})\\s?([0-9]{4})\\s?([0-9]{4})\\s?([0-9]{4})";
			final String ukMobileRegEx = "(07)\\d{3}(\\s)?\\d{6}";
			for (Message message : messages) {
				message.content = message.content.replaceAll(creditCardRegEx, redacted);
				message.content = message.content.replaceAll(ukMobileRegEx, redacted);
			}
		}
	}

	/**
	 * All user/sender Id's are Base64 encoded. This gives all users privacy, while
	 * keeping their Id's unique.
	 */
	private void obfuscateUsers() {
		boolean obfuscateUsers = configuration.isObfuscateUsersOn();
		if (obfuscateUsers) {
			for (Message message : messages) {
				String userId = message.senderId;
				byte[] bytesEncoded = Base64.encodeBase64(userId.getBytes());
				message.senderId = new String(bytesEncoded);
			}
		}
	}
}