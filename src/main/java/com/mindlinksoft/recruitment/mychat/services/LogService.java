package com.mindlinksoft.recruitment.mychat.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Service to help centralize how the system logs messages.
 */
public final class LogService {

	/**
	 * Prints a message to the console.
	 * 
	 * @param message The message to be logged.
	 */
	public static void logMessage(String message) {
		System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT))
				+ " - INFO: " + message);
	}
	
	/**
	 * Prints a warning to the console.
	 * 
	 * @param message The warning to be logged.
	 */
	public static void logWarning(String message) {
		System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT))
				+ " - WARN: " + message);
	}
	
	/**
	 * Prints an error to the console.
	 * 
	 * @param message The error to be logged.
	 */
	public static void logError(String message) {
		System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.SHORT))
				+ " - ERROR: " + message);
	}
}
