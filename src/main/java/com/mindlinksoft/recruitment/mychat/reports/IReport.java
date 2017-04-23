package com.mindlinksoft.recruitment.mychat.reports;

import com.mindlinksoft.recruitment.mychat.conversation.Conversation;

public interface IReport {
	
	public abstract Conversation generateReport(Conversation conversation);
}
