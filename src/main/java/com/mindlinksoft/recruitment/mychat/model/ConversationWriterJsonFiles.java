package com.mindlinksoft.recruitment.mychat.model;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.mindlinksoft.recruitment.mychat.util.GsonMaker;

public class ConversationWriterJsonFiles implements ConversationWriter {
	
	private static final Logger LOGGER = Logger.getLogger(ConversationWriterJsonFiles.class.getName());

	/**
	 * Helper method to write the given {@code conversation} as JSON to the given
	 * {@code outputFilePath}.
	 * 
	 * @param conversation   The conversation to write.
	 * @param outputFilePath The file path where the conversation should be written.
	 * @throws Exception Thrown when writing to the output file fails.
	 */
	@Override
	public void writeConversation(Conversation conversation, String outputFilePath) throws Exception {
		try (OutputStream os = new FileOutputStream(outputFilePath, true);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {
			GsonMaker gsonMaker = new GsonMaker();
			Gson g = gsonMaker.createGson();
			bw.write(g.toJson(conversation));
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.SEVERE, "The JSON output file's folder was not found.", e);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "The program was unable to write the conversation data to the JSON output file.", e);
		}
	}
}
