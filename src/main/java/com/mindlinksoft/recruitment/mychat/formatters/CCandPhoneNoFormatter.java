package com.mindlinksoft.recruitment.mychat.formatters;

import com.mindlinksoft.recruitment.mychat.messages.Message;


public class CCandPhoneNoFormatter implements IFormatter {
	/**
	 * A {@link Formatter} that occurrences of Credit Card number 
	 * matching the Regex "\b4[0-9]{3}[ -]*[0-9]{4}[ -]*[0-9]{4}[ -]*[0-9](?:[0-9]{3})\b"
	 * or phone number matching the Regex "\b(((0{2})|(\\+{1}))(357))?(9|2)[0-9]{7}\b"
	 * in the message content by the string "*redacted*"
	 * @author Omar
	 *
	 */
	private final String REPLACEMENT = "*redacted*";
	private final String CREDIT_CARD_NUMBER_REGEX="\\b4[0-9]{3}[ -]*[0-9]{4}[ -]*[0-9]{4}[ -]*[0-9](?:[0-9]{3})\\b";
	private final String CYPRUS_PHONE_REGEX="\\b(((0{2})|(\\+{1}))(357))?(9|2)[0-9]{7}\\b";
	
	/**
	 * Replaces the occurrences of Credit Card number 
	 * matching the Regex "\b4[0-9]{3}[ -]*[0-9]{4}[ -]*[0-9]{4}[ -]*[0-9](?:[0-9]{3})\b"
	 * or phone number matching the Regex "\b(((0{2})|(\\+{1}))(357))?(9|2)[0-9]{7}\b"
	 * in the message content by the string "*redacted*"
     * @param message
	 */
	@Override
	public Message apply(Message message) {

		String content = message.getContent();
		content = content.replaceAll(CREDIT_CARD_NUMBER_REGEX , REPLACEMENT);
		content = content.replaceAll(CYPRUS_PHONE_REGEX , REPLACEMENT);
		message.setContent(content);

		return message;
	}
}
