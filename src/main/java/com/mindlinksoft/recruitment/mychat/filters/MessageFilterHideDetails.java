package com.mindlinksoft.recruitment.mychat.filters;

import java.util.List;

import com.mindlinksoft.recruitment.mychat.model.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.model.Message;

public class MessageFilterHideDetails implements MessageFilter {

	/**
	 * Redacts any credit card numbers or UK mobile numbers found in any message
	 * content.
	 */
	@Override
	public List<Message> filterMessages(List<Message> messages, ConversationExporterConfiguration config) {
		boolean hideDetails = config.isHidePersonalDeatilsOn();
		if (hideDetails) {
			final String redacted = "*redacted*";
			final String creditCardRegEx = "([0-9]{4})\\s?([0-9]{4})\\s?([0-9]{4})\\s?([0-9]{4})";
			final String ukMobileRegEx = "(07)\\d{3}(\\s)?\\d{6}";
			for (Message message : messages) {
				message.content = message.content.replaceAll(creditCardRegEx, redacted);
				message.content = message.content.replaceAll(ukMobileRegEx, redacted);
			}
		}
		return messages;
	}

}
