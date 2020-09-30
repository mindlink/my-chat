package com.mindlinksoft.recruitment.mychat.commands;

import com.mindlinksoft.recruitment.mychat.conversation.Conversation;

public class ReportCommand implements IConversationExportCommand {

	@Override
	public Conversation doCommand(Conversation conversation) throws ExportCommandException {
		conversation.generateReport();
		return conversation;
	}

}
