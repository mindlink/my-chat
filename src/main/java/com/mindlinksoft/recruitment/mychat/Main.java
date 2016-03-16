package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.exceptions.ReadConversationException;
import com.mindlinksoft.recruitment.mychat.exceptions.WriteConversationException;
import com.mindlinksoft.recruitment.mychat.services.LogService;

public class Main {

	/**
	 * Entry point into the application.
	 * Using the command line arguments as configuration for the exporter.
	 */
	public static void main(String[] args) {

		try {	
			// The exporter will pass through the appropriate exception with a helpful message
			// included within. The error handling here is purely for additional logging purposes.
			ConversationExporter conversationExporter = new ConversationExporter();
			conversationExporter.export(args);
			
		} catch (IllegalArgumentException e) {
			LogService.logError("The arguments entered were invalid.");
			e.printStackTrace();
			ConversationExporter.printHelp();
			
		} catch (ReadConversationException e) {
			LogService.logError("Problem reading from the file system.");
			e.printStackTrace();
			
		} catch (WriteConversationException e) {
			LogService.logError("Problem writing to the file system.");
			e.printStackTrace();
			
		} catch (Exception e) {
			LogService.logError("Unknown error occurred.");
			e.printStackTrace();
		}

	}

}
