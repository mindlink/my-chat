package com.mindlinksoft.recruitment.mychat;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser{

	//Input File Path.
	public String inputFilePath;
	public boolean hasInput = false;

	//Output File Path.
	public String outputFilePath;
	public boolean hasOutput = false;

	//Blacklist File Path.
	public String blackList;
	public boolean hasBlackList = false;

	//Username Filter.
	public String username;
	public boolean hasUsername = false;

	//Keyword Filter.
	public String keyword;
	public boolean hasKeyword = false;

	//Flag, Usernames to be Redacted.
	public boolean rUsername = false;

	//Flag, Confidential Details to be Redacted (Phone numbers and card numbers).
	public boolean rConfidential = false;

	/**
	 * Parses the given {@code arguments} into the exporter configuration.
	 * @param arguments The command line arguments.
	 * @return The exporter configuration representing the command line arguments.
	 */

	public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) {			

		/*
		 * Possible Argument List
		 * -i: Input File [REQUIRED]
		 * -o: Output File [REQUIRED]
		 * -u: Username Filter
		 * -k: Keyword Filter
		 * -b: Blacklist File
		 * -p: Private Mode, Redact Phone Numbers and Card Numbers
		 * -P: Private Mode, Redact Specified Username
		 */
		
		match(arguments);
		
		return new ConversationExporterConfiguration(inputFilePath, outputFilePath, blackList, username, keyword, hasInput, hasOutput, hasBlackList, hasUsername, hasKeyword, rUsername, rConfidential);

	}
	public void match (String[] arguments) {
		for(int i = 0; i < arguments.length; i ++){
			try{
				String next;
				switch (arguments[i]){
				case "-p": 
					rConfidential = true;
					break;
				case "-P": 
					rUsername = true;
					break;
				case "-i": 
					next = arguments[i+1].trim();
					if(!next.equals("-i") && !next.equals("-o") && !next.equals("-u") && !next.equals("-k") && !next.equals("-b") && !next.equals("-p") && !next.equals("-P")){
						System.out.println("Input File Specified");
						inputFilePath = arguments[i+1];
						hasInput = true;
					}	
					break;
				case "-o": 
					next = arguments[i+1].trim();
					if(!next.equals("-i") && !next.equals("-o") && !next.equals("-u") && !next.equals("-k") && !next.equals("-b") && !next.equals("-p") && !next.equals("-P")){
						System.out.println("Output File Specified");
						outputFilePath = arguments[i+1];
						hasOutput = true;
					}	
					break;
				case "-b": 
					next = arguments[i+1].trim();
					if(!next.equals("-i") && !next.equals("-o") && !next.equals("-u") && !next.equals("-k") && !next.equals("-b") && !next.equals("-p") && !next.equals("-P")){
						blackList = arguments[i+1];
						hasBlackList = true;
					}	
					break;
				case "-u": 
					next = arguments[i+1].trim();
					if(!next.equals("-i") && !next.equals("-o") && !next.equals("-u") && !next.equals("-k") && !next.equals("-b") && !next.equals("-p") && !next.equals("-P")){
						username = arguments[i+1];
						hasUsername = true;
					}	
					break;
				case "-k": 
					next = arguments[i+1].trim();
					if(!next.equals("-i") && !next.equals("-o") && !next.equals("-u") && !next.equals("-k") && !next.equals("-b") && !next.equals("-p") && !next.equals("-P")){
						keyword = arguments[i+1];
						hasKeyword = true;
					}	
					break;
				}
			}
			catch (IndexOutOfBoundsException e){
				System.out.println("An error has occured whilst parsing input variables");
			}
			catch (NullPointerException f){
				System.out.println("An error has occured whilst parsing input variables");
			}
		}
	}
}
