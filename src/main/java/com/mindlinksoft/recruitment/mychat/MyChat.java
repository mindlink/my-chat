package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.model.ConversationExporter;
import com.mindlinksoft.recruitment.mychat.model.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.util.CommandLineArgumentParser;

public class MyChat {

	public static void main(String[] args) throws Exception {
		ConversationExporter exporter = new ConversationExporter();
		ConversationExporterConfiguration configuration = new CommandLineArgumentParser()
				.parseCommandLineArguments(args);
		exporter.exportConversation(configuration);
	}

}
