package com.mindlinksoft.recruitment.mychat.Filters;

import java.util.ArrayList;
import java.util.List;
import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

/**
 * Filters the words in the {@link Message} messages by replacing those from the
 * {@code blacklist}. Extends abstract class {@link Filter}.
 */
public class BlacklistFilter extends Filter {

	/**
	 * The keyword included in the {@link Message} messages that will not be
	 * filtered.
	 */
	private String blacklist;

	/**
	 * Initialises a new instance of the {@link BlacklistFilter} class.
	 * 
	 * @param option The array containing the filter option and the
	 *               {@code blacklist}.
	 */
	public BlacklistFilter(String[] option) {
		super(option);
		this.blacklist = option[1];
	}

	/**
	 * Filters the messages in a given {@link Conversation} object to remove words
	 * in the {@code blacklist} from messages.
	 * 
	 * @param convo {@link Conversation} object to be filtered.
	 * @return New {@link Conversation} object with filtered messages.
	 */
	@Override
	public Conversation filterMessages(Conversation convo) {
		List<Message> filteredMessages = new ArrayList<Message>();
		String conversationName = convo.name;
		String[] blacklistArray = blacklist.split("\\s+");

		for (Message m : convo.messages) {
			String newContent = m.content;
			for (String word : blacklistArray) {
				if (newContent.toLowerCase().contains(word.toLowerCase())) {
					newContent = newContent.replaceAll("(?i)" + word, "*redacted*");
				}
			}
			filteredMessages.add(new Message(m.timestamp, m.senderId, newContent));
		}
		return new Conversation(conversationName, filteredMessages);
	}

	/**
	 * Get function for {@code blacklist}.
	 * 
	 * @return {@code blacklist}.
	 */
	public String getBlacklist() {
		return this.blacklist;
	}
}
