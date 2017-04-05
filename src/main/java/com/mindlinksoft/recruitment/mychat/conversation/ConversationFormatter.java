package com.mindlinksoft.recruitment.mychat.conversation;

import java.util.ArrayList;
import java.util.List;

import com.mindlinksoft.recruitment.mychat.message.IMessage;
import com.mindlinksoft.recruitment.mychat.message.IMessageFilter;
import com.mindlinksoft.recruitment.mychat.message.IMessageFormatter;


/**
 * Conversation formatter contains a set of {@link IMessageFilter} and 
 * {@link IMessageFormtter} that can be applied on a given converstion.
 *
 */
public class ConversationFormatter implements IConversationFormatter {

	private List<IMessageFilter> messageFilters = new ArrayList<IMessageFilter>();
	private List<IMessageFormatter> messsageFormatters = new ArrayList<IMessageFormatter>();
	
	@Override
	public IConversation format(IConversation conversation) {
		List<IMessage> filteredMsgs = new ArrayList<IMessage>();
		// Iterate the messages and apply message filters and formatters.
		for (IMessage message : conversation.getMessages()) {
			// If the message is not filtered apply formatting.
			if (filterMessage(message)) {
				filteredMsgs.add(message);
			} else {
				applyFormatters(message);
			}
		}
		conversation.getMessages().removeAll(filteredMsgs);
		return conversation;
	}

	private void applyFormatters(IMessage message) {
		for (IMessageFormatter formatter : messsageFormatters) {
			message = formatter.format(message);
		}
	}

	private boolean filterMessage(IMessage message) {
		for (IMessageFilter filter : messageFilters) {
			if (filter.filterMessage(message))
				return true;
		}
		return false;
	}

	/**
	 * Adds a {@link IMessageFilter} to the list of filters.
	 * 
	 * @param msgFilter
	 */
	public void addMessageFilter(IMessageFilter msgFilter) {
		if (msgFilter != null) {
			messageFilters.add(msgFilter);
		}
	}
	
	/**
	 * Adds a {@link IMessageFormatter} to the list of message formatters.
	 * 
	 * @param msgFilter
	 */
	public void addMessageFormatter(IMessageFormatter msgFormatter) {
		if (msgFormatter != null) {
			messsageFormatters.add(msgFormatter);
		}
	}
}
