package com.mindlinksoft.recruitment.mychat;

/**
 * Class to store MyChat program constants.
 */
public class Const {
	
	public static final String USER_FILTER_KEY = "-u";
	public static final String WORD_FILTER_KEY = "-w";
	public static final String REDACT_FILTER_KEY = "-r";
	public static final String SECURE_FILTER_KEY = "-secure";
	public static final String REPORT_KEY = "-report";
	public static final String ANONYMOUS_ID_KEY = "-anonymous";
	public static final String REDACTED = "*redacted*";
	public static final String USER_MASK = "User";
	
	public static final String MAKING_REPORT = "Compiling report ... ";
	public static final String APPLYING_FILTERS = "Applying filters ... ";
	public static final String READING_CONVERSATION = "Reading conversation from '%s' ... ";
	public static final String WRITING_CONVERSATION = "Writing conversation to '%s' ... ";
	public static final String COMPLETE = "Complete";
	public static final String SUCCESSFUL_EXPORT = "\nConversation successfully exported from '%s' to '%s'";
	public static final String EXIT = "\nQuitting ...";
	
	public static final String LOG_ERROR = "WARNING: Unable to write to logs! '%s'";
	public static final String LOG_FILENAME = "Error_Log.txt";
	public static final String ERROR = "\nError: %s\nCheck '" + LOG_FILENAME + "' for a more detailed description";
	public static final String FILE_NOT_FOUND = "The file '%s' does not exist or is inaccessible";
	public static final String FILE_READ_ERROR = "Unable to read file '%s'";
	public static final String FILE_CREATE_ERROR = "Cannot create/open output file '%s'";
	public static final String FILE_WRITE_ERROR = "Cannot write to output file '%s'";
	public static final String INVALID_RESPONSE = "Error: Invalid Response";
	public static final String INSUFFICIENT_ARGUMENTS = "Insufficient arguments";
	public static final String BAD_ARGUMENTS = "Unrecognised argument: '%s'";
	public static final String INVALID_MESSAGE = "Invalid message string: '%s'";
	public static final String INVALID_TIMESTAMP = "Invalid timestamp in: '%s'";
	
	public static final String FILE_ALREADY_EXISTS = "The file '%s' already exists. ";
	public static final String ASK_OVERWRITE = "Would you like to overwrite it? (y/n)";
	public static final String YES = "y";
	public static final String NO = "n";

	public static final String CARD_REGEX = "\\b(?:3[47]\\d{2}([\\s-]?)\\d{6}\\1\\d|(?:(?:4\\d|5[1-5]|65)\\d{2}|6011)([\\s-]?)\\d{4}\\2\\d{4}\\2)\\d{4}\\b";
	public static final String PHONE_REGEX = "\\b(0)(\\d{10})\\b";

}
