package com.mindlinksoft.recruitment.mychat;

public class CommandProcesser {
	private final String userCommand = "USER";
    private final String keywordCommand = "KEYWORD";
    private final String blacklist = "BLACKLIST";
	private String commandName;
	private String commandArguments;
	
	public CommandProcesser() {
		
	}
	
	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}
	
	public void setCommandArguments(String arguments) {
		this.commandArguments = arguments;
	}
	
	public String getCommandName() {
		return this.commandName;
	}
	
	public String getCommandArguments() {
		return this.commandArguments;
	}
	
	public void commandActivation(ConversationExporterConfiguration exporterConfiguration) {
		switch(this.commandName.toUpperCase()) {
			case userCommand:
				FilterByUser filterByUser = new FilterByUser();
				
				if(filterByUser.getRequireParameters()) {      			//Checks if the command requires parameters  
					filterByUser.processParameters(this.commandArguments);			// Assign parameters to object
				}
				exporterConfiguration.addFunctionality(filterByUser);    // Commands to be performed are included in the exporter configurations
				break;
			case keywordCommand:
				FilterByKeyword filterByKeyword = new FilterByKeyword();
				if(filterByKeyword.getRequireParameters()) {
					filterByKeyword.processParameters(this.commandArguments);
				}
				exporterConfiguration.addFunctionality(filterByKeyword);
				break;
			case blacklist:
				Blacklist blacklist = new Blacklist();
				if(blacklist.getRequireParameters()) {
					blacklist.processParameters(this.commandArguments);
				}
				
				exporterConfiguration.addFunctionality(blacklist);
				break;
			default:
				// If command not recognized 
				System.out.println("Command not found!");
		}
		return;
	}

}
