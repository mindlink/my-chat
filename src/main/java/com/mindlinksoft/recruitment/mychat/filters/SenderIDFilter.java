package com.mindlinksoft.recruitment.mychat.filters;

import com.mindlinksoft.recruitment.mychat.messages.Message;
/**
 * A {@link Filter} that Filters messages based on a SenderID
 * @author Omar
 *
 */
public class SenderIDFilter implements IFilter {
	
	private final String senderID;

	public SenderIDFilter(String senderID)
	{
		this.senderID=senderID;
	}

	@Override
	public Boolean apply(Message message) {

		return message.getSenderId().equalsIgnoreCase(senderID);
	}
}