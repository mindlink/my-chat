package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {

	private static ConversationExporterConfiguration configuration;

	/**
	 * The application entry point.
	 * @param args The command line arguments.
	 * @throws Exception Thrown when something bad happens.
	 */
	public static void main(String[] args) throws Exception {
		ConversationExporter exporter = new ConversationExporter();

		configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);

		exporter.exportConversation(configuration);
	}

	/**
	 * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
	 * @param inputFilePath The input file path.
	 * @param outputFilePath The output file path.
	 * @throws Exception Thrown when something bad happens.
	 */
	public void exportConversation(ConversationExporterConfiguration cofiguration) throws Exception {
		if(configuration.hasInput && configuration.hasOutput){
			Object [] conversationData = this.readConversation(configuration.inputFilePath);

			Conversation conversation = (Conversation)conversationData[0];
			UserReport report = (UserReport)conversationData[1];

			this.writeConversation(conversation, configuration.outputFilePath);
			this.writeReport(report, configuration.outputFilePath);

			// TODO: Add more logging...
			System.out.println("Conversation exported from '" + configuration.inputFilePath + "' to '" + configuration.outputFilePath);    
		}else {
			System.out.println("Please specify valid input and output locations");    
		}
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
			GsonBuilder gsonBuilder = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting();

			gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());


			Gson g = gsonBuilder.create();

			bw.write(g.toJson(conversation));
		} catch (FileNotFoundException e) {
			// TODO: Maybe include more information?
			throw new IllegalArgumentException("The file was not found.");
		} catch (IOException e) {
			// TODO: Should probably throw different exception to be more meaningful :/
			throw new Exception("Something went wrong");
		}
	}

	public void writeReport(UserReport report, String outputFilePath) throws Exception {
		// TODO: Do we need both to be resources, or will buffered writer close the stream?
		try (OutputStream os = new FileOutputStream(outputFilePath, true);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

			// TODO: Maybe reuse this? Make it more testable...
			GsonBuilder gsonBuilder = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting();

			gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());


			Gson g = gsonBuilder.create();

			bw.write(g.toJson(report));
		} catch (FileNotFoundException e) {
			// TODO: Maybe include more information?
			throw new IllegalArgumentException("The file was not found.");
		} catch (IOException e) {
			// TODO: Should probably throw different exception to be more meaningful :/
			throw new Exception("Something went wrong");
		}
	}

	/**
	 * Represents a helper to read a conversation from the given {@code inputFilePath}.
	 * @param inputFilePath The path to the input file.
	 * @return The {@link Conversation} representing by the input file.
	 * @throws Exception Thrown when something bad happens.
	 */
	public Object[] readConversation(String inputFilePath) throws Exception {
		try(InputStream is = new FileInputStream(inputFilePath);
				BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

			List<Message> messages = new ArrayList<Message>();
			List<User> users = new ArrayList<User>();

			List<String> usernames = new ArrayList<String>();
			List<String> oldUsernames = new ArrayList<String>();

			List<String> allUsers = new ArrayList<String>();
			List<Integer> totalMessageCount = new ArrayList<Integer>();

			String conversationName = r.readLine();

			String line;

			List<String> blackList = new ArrayList<String>();


			//Read blacklist file before checking the messages, saves i/o time compared to reading the list every time it is required.
			if(configuration.hasBlackList){
				InputStream isB = new FileInputStream(configuration.blackList);
				BufferedReader rB = new BufferedReader(new InputStreamReader(isB));
				while ((line = rB.readLine()) != null) {
					blackList.add(line);
				}
			}


			int n = 0;
			while ((line = r.readLine()) != null) {
				String[] split = line.split(" ");

				int splitSize = split.length;

				//Calculate timestamp in correct notation.
				Instant timestamp = Instant.ofEpochSecond(Long.parseUnsignedLong(split[0]));

				//Split username.
				String userName = split [1];

				//Initialize variable for message content.
				String messageContent = "";

				//After the timestamp and user are split, add the remaining contents to the message body.
				for(int i=2; i < splitSize; i++){
					messageContent = messageContent + split[i] + " ";
				}
				
				messageContent = redactPhoneCard(messageContent);

				messageContent = applyBlacklist(messageContent, blackList);	

				boolean add = usernameCheck(userName) && keywordCheck(messageContent);												

				//Replace Usernames with generic usernames whilst maintaining the relationship between them withing the chat.
				if(configuration.rUsername){
					boolean replaced = false;
					int i = 0;

					for(i = 0; i < oldUsernames.size(); i++){
						if(userName.equals(oldUsernames.get(i))){
							userName = usernames.get(i);
							replaced = true;
						}
					}

					if(!replaced){
						n++;
						oldUsernames.add(userName);
						userName = "user" + n;						
						usernames.add(userName);
					}
				}

				messageContent.trim();			

				//Add message to output conversation if all criteria matches.
				if(add){
					messages.add(new Message(timestamp, userName, messageContent));
				}

				boolean found = false;
				for(int i = 0; i< allUsers.size(); i++){
					if(allUsers.get(i).equals(userName)){
						found = true;
						totalMessageCount.set(i, totalMessageCount.get(i) + 1);
					}
				}		
				if(!found){
					allUsers.add(userName);
					totalMessageCount.add(1);
				}

			}

			for(int i = 0; i< allUsers.size(); i++){
				users.add(new User(allUsers.get(i), totalMessageCount.get(i)));
			}

			return new Object [] {new Conversation(conversationName, messages), new UserReport(users)};
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("The file was not found.");
		} catch (IOException e) {
			throw new Exception("Something went wrong");
		}
	}
	
	public boolean usernameCheck (String userName) {
		boolean add = true;
		if(configuration.hasUsername){
			//Check if message is from a specific user.
			if(!configuration.username.equals(userName)){
				return false;
			}
		}
		return add;
	}
	
	public boolean keywordCheck (String messageContent){
		boolean add = true;
		if(configuration.hasKeyword){
			//Check if message contains keyword.
			if(!messageContent.toLowerCase().contains(configuration.keyword.toLowerCase())){
				add = false;
			}
		}
		return add;
	}
	public String redactPhoneCard (String messageContent) {
		if(configuration.rConfidential){
			//Replace Phone Numbers with *redacted*
			messageContent = messageContent.replaceAll("(0[12357])[0-9]{9}",  "*redacted*");

			//Replace Card Numbers with *redacted*

			//VISA
			messageContent = messageContent.replaceAll("4[0-9]{6,}", "*redacted* (VISA)");
			//MasterCard
			messageContent = messageContent.replaceAll("5[1-5][0-9]{5,}", "*redacted* (MasterCard");
			//American Express
			messageContent = messageContent.replaceAll("3[47][0-9]{5,}", "*redacted* (Amex)");

		}
		return messageContent;
	}
	public String applyBlacklist (String messageContent, List<String> blackList) {
		if(configuration.hasBlackList){
			for(int i = 0; i < blackList.size(); i++){
				//Remove Any Blacklisted Words and Replace with *redacted*
				messageContent = messageContent.replaceAll(blackList.get(i).toString(), "*redacted*");
			}
		}
		return messageContent;
	}

	class InstantSerializer implements JsonSerializer<Instant> {
		@Override
		public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
			return new JsonPrimitive(instant.getEpochSecond());
		}
	}
}
