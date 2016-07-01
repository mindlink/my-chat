package com.mindlinksoft.recruitment.mychat;
import com.mindlinksoft.recruitment.mychat.FileIO;

import exceptionHandeling.readExcep;
import exceptionHandeling.writeExcep;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.synth.SynthSeparatorUI;

import org.apache.commons.cli.ParseException;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {


	/**
	 * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
	 * @param inputFilePath The input file path.
	 * @param outputFilePath The output file path.
	 * @throws Exception Thrown when something bad happens.
	 */
	public void exportConversation(String[] configuration) throws Exception  {

		// Parse 
		CommandLineArgumentParser argParser = new CommandLineArgumentParser();
		ConversationExporterConfiguration expConfig = argParser.parseCommandLineArguments(configuration);

		if(expConfig == null){
			System.out.println("ERROR: please insert an input and output file path");
		}else{
			System.out.println("Log Message: Exporting file...");
		}

		// Read
		Conversation convo = readConversation(expConfig);

		// Filter 
		convo = filter(convo, expConfig);

		// Hide 
		convo = hide(convo, expConfig);

		// Write
		writeConversation(convo, expConfig);
	}

	/** 
	 * method responsible for reading the conversation
	 * 
	 * @param takes in the configuration
	 */
	private Conversation readConversation(ConversationExporterConfiguration config) throws Exception, IllegalArgumentException, readExcep  {

		FileIO io = new FileIO();
		Conversation convo = io.readConversation(config.getInputFilePath());

		System.out.println("Log Message: Reading " + config.getInputFilePath() + " conversation .....");

		return convo;

	}

	/**
	 * Method responsible for filtering the conversation
	 * @param convo conversation to be filtered
	 * @param config exporter configuration
	 * @return
	 */
	private Conversation filter(Conversation convo, ConversationExporterConfiguration config){
		Filtering f = new Filtering();
		Conversation filtered = convo;

		/*
		 * filter by user
		 */

		// check that the getUser value is not nul
		if(config.getUser() != null){
			System.out.println("Retrieveing messages sent by " + config.getUser() + ".....");
			filtered = f.byUser(convo, config.getUser()); // filter convo by user
		}

		/*
		 * filter by keyword
		 */

		if(config.getKeyWord() != null){
			System.out.println("Retrieveing messages with " + config.getKeyWord() + " keyword .....");
			filtered = f.byKeyword(convo, config.getKeyWord());
		}

		return filtered;	
	}


	/**
	 * Method responsible for hiding blacklisted words 
	 * @param conversation the conversation where the words to be hided are in
	 * @param config the configuration
	 * @return
	 */

	private Conversation hide(Conversation conversation, ConversationExporterConfiguration config) {

		Blacklisting bl = new Blacklisting();
		Conversation hidden = conversation;

		// if the blacklist value is not null
		if (config.getBl() != null) {
			System.out.println("hiding the blacklisted words from the conversation...");
			hidden = bl.hideWord(hidden, config.getBl()); // hide words
		}

		return hidden;
	}

	/**
	 * method responsible for writing the new conversation after the filtering/hiding is applied
	 * @param convo the conversation to be written
	 * @param config the configuration
	 * @throws Exception
	 * @throws IllegalArgumentException
	 * @throws writeExcep
	 */
	private void writeConversation(Conversation convo, ConversationExporterConfiguration config) throws Exception, IllegalArgumentException, writeExcep  {
		FileIO io = new FileIO();
		io.writeConversation(convo, config.getOutputFilePath());

		System.out.println("Conversation " + convo.getName() + " exported to " + config.getOutputFilePath());

	}
}
