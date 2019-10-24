package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 * 
 * Features Implemented: Filter by senderId, keyword, a txt file which contains a list of 
 * words that are blacklisted, by card and phone number. It also generates a report with
 * the active users.
 * 
 */
public class ConversationExporter {

	static String method;
	static String filter="";
	/**
	 * The application entry point.
	 * @param args The command line arguments.
	 * @throws Exception Thrown when something bad happens.
	 */
	public static void main(String[] args) throws Exception {

		method=args[2];
		if(!method.equals("4"))
			filter=args[3];
		ConversationExporter exporter = new ConversationExporter();
		ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

		exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, method, filter);
	}

	/**
	 * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
	 * @param inputFilePath The input file path.
	 * @param outputFilePath The output file path.
	 * @throws Exception Thrown when something bad happens.
	 */
	public void exportConversation(String inputFilePath, String outputFilePath, String method, String filter) throws Exception {
		Conversation conversation = this.readConversation(inputFilePath);
		filteringMethod fm = new filteringMethod(method, filter, conversation.messages);
		conversation.messages=fm.showMethod();
		conversation.report=fm.activityCounter();
		this.writeConversation(conversation, outputFilePath);

		// TODO: Add more logging...
		System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
	}

	/**
	 * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
	 * @param conversation The conversation to write.
	 * @param outputFilePath The file path where the conversation should be written.
	 * @throws Exception Thrown when something bad happens.
	 */
	public void writeConversation(Conversation conversation, String outputFilePath) throws Exception {
		// TODO: Do we need both to be resources, or will buffered writer close the stream?
		try (OutputStream os = new FileOutputStream(outputFilePath, true);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

			// TODO: Maybe reuse this? Make it more testable...
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

			Gson g = gsonBuilder.create();

			bw.write(g.toJson(conversation));
		} catch (FileNotFoundException e) {
			// TODO: Maybe include more information?
			throw new IllegalArgumentException("The file was not found.");
		} catch (IOException e) {
			// TODO: Should probably throw different exception to be more meaningful :/
			throw new Exception("File not created.");
		}catch (NullPointerException e){
			throw new Exception("File is emtpy.");
		}
	}

	/**
	 * Represents a helper to read a conversation from the given {@code inputFilePath}.
	 * @param inputFilePath The path to the input file.
	 * @return The {@link Conversation} representing by the input file.
	 * @throws Exception Thrown when something bad happens.
	 */
	public Conversation readConversation(String inputFilePath) throws Exception {
		try(InputStream is = new FileInputStream(inputFilePath);
				BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

			List<Message> messages = new ArrayList<Message>();

			String conversationName = r.readLine();
			String line;

			while ((line = r.readLine()) != null) {
				String[] split = line.split(" ");
				String sentence="";
				for(int i=2; i<split.length; i++){
					if(i==2){
						sentence=split[i];
					}else{
						sentence=sentence+" "+split[i];}
				}
				messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], sentence));
			}

			return new Conversation(conversationName, messages);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("The file was not found.");
		} catch (IOException e) {
			throw new Exception("Something went wrong");
		}catch (NullPointerException e){
			throw new Exception("File is emtpy");
		}
	}

	class InstantSerializer implements JsonSerializer<Instant> {
		public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
			return new JsonPrimitive(instant.getEpochSecond());
		}
	}

}
