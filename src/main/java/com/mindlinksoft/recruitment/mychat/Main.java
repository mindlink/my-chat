package com.mindlinksoft.recruitment.mychat;

public class Main {

	/**
	 * Entry point into the application. Use the command line arguments as the configuration
	 * for the exporter.
	 */
	public static void main(String[] args) {

		try {	
			// Export the conversation based on the parameters supplied via arguments
			ConversationExporter conversationExporter = new ConversationExporter();
			conversationExporter.export(args);
			
		} catch (IllegalArgumentException e) {
			// If the export fails because of the arguments then print usage help to the console
			System.out.print(e + "\n\n" + ConversationExporter.help());
			
		} catch (Exception e) {
			// Something went terribly wrong, exit the application and show the error.
			e.printStackTrace();
		}

	}

}
