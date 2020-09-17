package com.mindlinksoft.recruitment.mychat;

/**
 * Mock class used to generate config data
 * 
 * @author Mohamed Yusuf
 *
 */
public class GenerateMockConfiguration {
	
	public ConversationExporterConfiguration genMockConfiguration() {
		String[] args = {"chat.txt", "output.json"};
		return new CommandLineArgumentParser().parseCommandLineArguments(args);
	}
}
