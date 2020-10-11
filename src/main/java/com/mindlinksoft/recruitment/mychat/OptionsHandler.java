package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import picocli.CommandLine;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.ParseResult;
import picocli.CommandLine.UnmatchedArgumentException;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.nio.charset.StandardCharsets; 
import java.nio.file.*; 

import org.apache.log4j.Logger;
/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class OptionsHandler {
	private static Logger logger = Logger.getLogger(Utils.class);

	public List<Message> options(ConversationExporterConfiguration configuration, List<Message> messages){
		for(Message m : messages){
				m.redact(configuration.blacklist);
				m.filterByWord(filter_word);
				m.filterByUser(configuration.filter_user);
			
		}
		
		configuration.report;
		return null;
	}



}
