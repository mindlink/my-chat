package com.mindlinksoft.recruitment.mychat.formatters;

import com.mindlinksoft.recruitment.mychat.messages.Message;

public interface IFormatter {
	
	public Message apply(Message message);

}
