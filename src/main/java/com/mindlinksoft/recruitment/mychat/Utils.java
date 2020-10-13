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
public class Utils {
	private static Logger logger = Logger.getLogger(Utils.class);

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws Exception {
        try(Writer writer = new FileWriter(outputFilePath)){
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
            Gson g = gsonBuilder.excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
			g.toJson(conversation, writer);
        } catch (FileNotFoundException e) {
			logger.error("The output file was not found, check the path.", e);
        } catch (IOException e) {
			logger.error("Error writing to file.", e);
        }
    }

    /**
     * Represents a helper to read a conversation from the given file.
     * @param inputFilePath File path to read from.
     * @return A list of all lines read from file.
     * @throws Exception Thrown file could not be read.
     */
    public List<String> readChatFile(String inputFilePath) throws Exception {
		logger.info("Reading conversation from " + inputFilePath);
        try{
			return Files.readAllLines(Paths.get(inputFilePath), StandardCharsets.UTF_8); 
        } catch (FileNotFoundException e) {
			logger.error("The input file was not found, check the path.", e);
        } catch (IOException e) {
            logger.error("Error reading from file.", e);
			
        }
		return null;
    }


    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
