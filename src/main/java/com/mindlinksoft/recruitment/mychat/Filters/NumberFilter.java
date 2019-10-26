package com.mindlinksoft.recruitment.mychat.Filters;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

/**
 * Filters the contents of each {@link Message} by replacing any phone or card
 * number with "*redacted*".
 */
public class NumberFilter extends Filter {

	/**
	 * Initialises a new instance of the {@link NumberFilter} class.
	 * 
	 * @param option The array containing the filter option.
	 */
	public NumberFilter(String[] option) {
		super(option);
	}

	/**
	 * Filters the messages in a given {@link Conversation} object to remove numbers
	 * bigger than 10000.
	 * 
	 * @param convo {@link Conversation} object to be filtered.
	 * @return New {@link Conversation} object with filtered messages.
	 */
	@Override
	public Conversation filterMessages(Conversation convo) {
		List<Message> filteredMessages = new ArrayList<Message>();
		String conversationName = convo.name;

		Pattern p = Pattern.compile("\\d+");

		for (Message m : convo.messages) {
			String newContent = m.content;
			Matcher match = p.matcher(newContent);

			// If number exists, check if its bigger than 10000 and redact if true
			while (match.find()) {

				BigInteger foundNum = new BigInteger(match.group());
				BigInteger limit = new BigInteger("10000");

				if (foundNum.compareTo(limit) > 0) {
					newContent = newContent.replaceAll(match.group(), "*redacted*");
				}
			}
			filteredMessages.add(new Message(m.timestamp, m.senderId, newContent));
		}

		return new Conversation(conversationName, filteredMessages);
	}
}
