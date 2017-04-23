package com.mindlinksoft.recruitment.mychat.filters;

import com.mindlinksoft.recruitment.mychat.messages.Message;

public interface IFilter {

	public Boolean apply(Message message);
}
