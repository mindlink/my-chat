package com.mindlinksoft.recruitment.mychat;

public class Main {

	public static void main(String[] args) {

		try {	
			// Export the conversation based on the parameter supplied via arguments
			ConversationExporter conversationExporter = new ConversationExporter();
			conversationExporter.export(args);
			
		} catch (IllegalArgumentException e) {
			// If the export fails then print usage help to the console
			// TODO: Implement ConversationExporter.help()
			e.printStackTrace();
			
		} catch (Exception e) {
			// Something went terribly wrong, exit the application and show the error.
			e.printStackTrace();
		}

	}

}
