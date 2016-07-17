package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.bean.Conversation;
import com.mindlinksoft.recruitment.mychat.model.CommandLineArgumentParser;
import com.mindlinksoft.recruitment.mychat.model.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.model.ConversationFileReader;
import com.mindlinksoft.recruitment.mychat.model.ConversationFileWriter;

/**
 * Represents a conversation exporter that can read a conversation and write it
 * out in JSON.
 * 
 * Usage: ConversationExporter -i <Input file> -o <Output file>
 * 
 * Optional Arguments:
 * 
 * -b "<list of words>" - Blacklisted words
 * 
 * -k <keyword> - Filter by keyword
 * 
 * -u <username> - Filter by username
 * 
 * -h - Hide credit cards and phone numbers
 * 
 * -s - Obfuscate sender id
 * 
 * @author Yiannis Pericleous
 */
public class ConversationExporter {

	private static final int FAIL = -1;

	public static void main(String[] args) {
		ConversationExporterConfiguration config = new CommandLineArgumentParser().parseCommandLineArguments(args);
		if (config == null)
			System.exit(FAIL);
		exportConversation(config);
	}

	public static void exportConversation(ConversationExporterConfiguration config) {
		try {
			ConversationFileReader reader = new ConversationFileReader(config);
			Conversation conversation = reader.read();
			ConversationFileWriter writer = new ConversationFileWriter(conversation, config);
			writer.write();
			System.out.println(
					"Conversation exported from '" + config.getInputFile() + "' to '" + config.getOutputFile());
		} catch (Exception e) {
			System.out.println("Export Failed! :(");
			System.out.println(e.getMessage());
			System.exit(FAIL);
		}
	}
}
