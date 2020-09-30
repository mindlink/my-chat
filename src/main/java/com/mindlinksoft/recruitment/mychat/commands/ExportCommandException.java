package com.mindlinksoft.recruitment.mychat.commands;

/**
 * A custom exception thrown when something goes wrong carrying out optional commands
 */
public class ExportCommandException extends Exception{
	
	IConversationExportCommand causingCommand;
	
	public ExportCommandException(IConversationExportCommand cmd, String message) {
		super(message);
		causingCommand = cmd;
	}
	
	public IConversationExportCommand getCausingCommand () {
		return causingCommand;
	}

}
