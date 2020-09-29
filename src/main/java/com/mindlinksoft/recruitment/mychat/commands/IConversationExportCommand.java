package com.mindlinksoft.recruitment.mychat.commands;

import com.mindlinksoft.recruitment.mychat.Conversation;

/**
 * An interface for extra operations 
 *
 */
public interface IConversationExportCommand {
	
	
	public Conversation doCommand(Conversation conversation);

}
