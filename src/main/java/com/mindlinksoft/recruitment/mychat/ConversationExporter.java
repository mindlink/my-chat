package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {

	/**
	 * The application entry point.
	 * @param args The command line arguments.
	 * @throws Exception Thrown when something bad happens.
	 */
	public static void main(String[] args) throws Exception {
		ConversationExporter exporter = new ConversationExporter();
		ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
		Conversation conversation = exporter.readConversation(configuration.inputFilePath);			
		
		/*
		 * The functions are written to return conversation objects instead of to write to file directly 
		 * to allow for further expansion where a combination of functions may be called instead of just
		 * the one call e.g. filtering by a user and redacting words / card numbers from that user's messages
		 * */
		
		if (configuration.user == true) {
			conversation = exporter.filterMessagesByName(conversation, configuration.userArg);
		}
		
		if (configuration.keyword == true) {
			conversation = exporter.filterMessagesByKeyword(conversation, configuration.kwArg);
		}
		
		if (configuration.redact == true) {
			conversation = exporter.redactMessages(conversation, configuration.redactArg);
		}
		
		if (configuration.redactPhoneCard == true) {
			conversation = exporter.redactPhoneAndCardNumbers(conversation);
		}
		
		if (configuration.obfuscate == true) {
			conversation = exporter.obfuscateUserID(conversation);
		}
		if (configuration.activity == true) {
			conversation = exporter.getMostActiveUser(conversation);
		}
		// no further modification to conversation, now write it to output
		exporter.writeConversation(conversation, configuration.outputFilePath);
	}
	
	/**
	 * Returns only the messages where the senderId matches the provided name.
	 * Uses predicates to filter such messages, then writes the filtered conversation.
	 * @param inputFilePath The input file path.
	 * @param outputFilePath The output file path.
	 * @param name The username of the message's author.
	 * @return A conversation with only messages sent by the matching userId
	 * @throws Exception Thrown when something bad happens.
	 */
	public Conversation filterMessagesByName(Conversation conversation, String name) throws Exception {
		Conversation unfilteredConversation = conversation;
		Collection<Message> messages = unfilteredConversation.messages;
		
		// String.equals() comes with case ignorance, bob might be entered as Bob or BOB
		Predicate<Message> pr = (Message mess) -> (mess.senderId.equalsIgnoreCase(name) == false);
		messages.removeIf(pr);

		Conversation filteredConversation = new Conversation(unfilteredConversation.name + ", filtered by name: " + name, messages);
		return filteredConversation;
	}
	
	/**
	 * Returns only the messages where the content contains a keyword.
	 * Uses predicates to filter such messages, then writes the filtered conversation.
	 * @param inputFilePath The input file path.
	 * @param outputFilePath The output file path.
	 * @param keyword The keyword to find in messages.
 	 * @return A conversation with only messages containing the keyword
	 * @throws Exception Thrown when something bad happens.
	 */
	public Conversation filterMessagesByKeyword(Conversation conversation, String keyword) throws Exception {
		Conversation unfilteredConversation = conversation;
		Collection<Message> messages = unfilteredConversation.messages;
		
		// case-insensitive word detection
		Predicate<Message> pr = (Message mess) -> (mess.content.toUpperCase().contains(keyword.toUpperCase()) == false);
		messages.removeIf(pr);

		Conversation filteredConversation = new Conversation(unfilteredConversation.name + ", filtered by keyword: " + keyword, messages);
		return filteredConversation;
	}
	
	/**
	 * Iterates over the messages in a conversation and redacts instances of the provided word,
	 * returning the complete conversation with the word replaced by "*redacted*"
	 * @param inputFilePath The input file path.
	 * @param outputFilePath The output file path.
	 * @param word The keyword to redact
	 * @return A conversation with the word parameter redacted
	 * @throws Exception Thrown when something bad happens.
	 */
	public Conversation redactMessages(Conversation conversation, String word) throws Exception {
		Conversation unfilteredConversation = conversation;
		Collection<Message> messages = unfilteredConversation.messages;

		// get iterator to sweep over all messages, redacting each instance of the word parameter
		Iterator<Message> iter = messages.iterator();

		// declare outside loop, avoid re-declaring at every iteration
		Message next;

		while(iter.hasNext()) {
			next = iter.next();
			// case-insensitive redaction to avoid the embarrassment of sensitive information skipping redaction by being 
			// the first word in a sentence and hence with a capitalised first letter where keyword is lowercase
			next.content = next.content.replaceAll("(?i)"+word, "*redacted*");
		}

		Conversation filteredConversation = new Conversation(unfilteredConversation.name + ", with redaction.", messages);
		return filteredConversation;
	}
	
	/**
	 * Iterates over the messages in a conversation and redacts phone numbers and credit card numbers by matching
	 * the message content with card and phone number regexes. 
	 * Card regex from https://howtodoinjava.com/java/regex/java-regex-validate-credit-card-numbers/.
	 * Phone regex just 6-14 digits preceded by a + or a 00 as either may be accepted by a given company.
	 * @param inputFilePath The input file path.
	 * @param outputFilePath The output file path.
	 * @return A conversation with phone numbers and card numbers redacted
	 * @throws Exception Thrown when something bad happens.
	 */
	public Conversation redactPhoneAndCardNumbers(Conversation conversation) throws Exception {		
		Conversation unfilteredConversation = conversation;
		Collection<Message> messages = unfilteredConversation.messages;	
		
		/* below regex from https://howtodoinjava.com/java/regex/java-regex-validate-credit-card-numbers/
		   altered to exclude the start-of-line ^ and end-of-line $ to allow it to match with a substring that can occur
		   anywhere in a string (someone might wrap their number with other strings).
		   note it doesn't account for whitespace in between digits in a card number */
		String cardRegex = "(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" +
				"(?<mastercard>5[1-5][0-9]{14})|" +
		        "(?<discover>6(?:011|5[0-9]{2})[0-9]{12})|" +
		        "(?<amex>3[47][0-9]{13})|" +
		        "(?<diners>3(?:0[0-5]|[68][0-9])?[0-9]{11})|" +
		        "(?<jcb>(?:2131|1800|35[0-9]{3})[0-9]{11})";
		
		// modest range of numbers preceded by a plus or 00 
		String phoneRegex = "(\\+|00)[0-9]{6,14}";
		
		Iterator<Message> iter = messages.iterator();

		// declare outside loop, avoid re-declaring at every iteration
		Message next;

		while(iter.hasNext()) {
			next = iter.next();
			next.content = next.content.replaceAll(cardRegex, "*redacted*");
			next.content = next.content.replaceAll(phoneRegex, "*redacted*");
		}
		Conversation filteredConversation = new Conversation(unfilteredConversation.name + ", with redacted card numbers.", messages);
		return filteredConversation;
	}
	
	/**
	 * Iterates over the messages in a conversation and hashes the userId to obfuscate them. 
	 * By hashing, the association between a user and the messages they sent is maintained, but
	 * the userId is replaced with an integer that needs knowledge of the inverse of the function 
	 * to recover it. For simplicity's sake, default java hash function is used.  
	 * @param inputFilePath The input file path.
	 * @param outputFilePath The output file path.
	 * @return A conversation with userIds represented as their integer hash value
	 * @throws Exception Thrown when something bad happens.
	 */
	public Conversation obfuscateUserID(Conversation conversation) throws Exception {
		Conversation unfilteredConversation = conversation;
		Collection<Message> messages = unfilteredConversation.messages;
		
		Iterator<Message> iter = messages.iterator();

		// declare outside loop, avoid re-declaring at every iteration
		Message next;

		while(iter.hasNext()) {
			next = iter.next();
			int obfuscatedSenderId = next.senderId.hashCode();
			next.senderId = "" + obfuscatedSenderId;
		}
		
		Conversation filteredConversation = new Conversation(unfilteredConversation.name + ", with obfuscated userID.", messages);
		return filteredConversation;
	}

	/**
	 * Iterates over the messages in a conversation, creates an ArrayList of <String UserId, int count> UserNodes
	 * which represent the most active users in the conversation.  
	 * @param inputFilePath The input file path.
	 * @param outputFilePath The output file path.
	 * @return A conversation with the list of most active users in descending order 
	 * @throws Exception Thrown when something bad happens.
	 */
	public Conversation getMostActiveUser(Conversation conversation) throws Exception {
		
		Conversation c = conversation;
		Collection<Message> messages = c.messages;
		
		Iterator<Message> iter = messages.iterator();
		ArrayList<UserNode> activity = new ArrayList<UserNode>();

		// declare outside loop, avoid re-declaring at every iteration
		Message next;
		
		// boolean to set true when a UserNode is found and its count is incremented
		boolean activityIncremented = false;
		
		while(iter.hasNext()) {
			next = iter.next();
			activityIncremented = false;
			
			// on first iteration, this loop is skipped as activity has no size
			for (int i = 0; i < activity.size(); i++) {
				UserNode node = activity.get(i);

				// if that node contains the senderID under consideration, increment count
				if (node.userId.equals(next.senderId)) {
					activity.get(i).count++;
					activityIncremented = true;
					break; // break inner for loop 
				}
			}
			
			// add new node if user not found in list
			if (activityIncremented == false) {
				UserNode node = new UserNode(next.senderId, 1);
				activity.add(node);
			}
		}

		Collections.sort(activity);
		c.activity = activity;
		return c;
	}
	
	/**
	 * Exports conversation in similar fashion to exportConversation below, but with the ArrayList of UserNode objects, 
	 * and in append mode so it writes the report to the end of the file, not over the file.  
	 * @param activity The ArrayList of UserNodes with userId and count of messages sent.
	 * @param outputFilePath The output file path.
	 * @throws Exception Thrown when something bad happens.
	 */
	
	/**
	 * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
	 * @param inputFilePath The input file path.
	 * @param outputFilePath The output file path.
	 * @throws Exception Thrown when something bad happens.
	 */
	public void exportConversation(String inputFilePath, String outputFilePath) throws Exception {
		Conversation conversation = this.readConversation(inputFilePath);

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
		// try-with-resources. setting append=false for testing 
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath, false))) {

			// TODO: Maybe reuse this? Make it more testable...
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

			Gson g = gsonBuilder.create();
			bw.write(g.toJson(conversation));
			bw.close(); // closing writer will close the output stream

		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("The file was not found. "
					+ "\nPlease check the output file path leads to an existing directory that you have permission to write to.");
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception(e.getCause().toString());
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
				// without index, only the first word of the message makes it to split[2]
				String[] split = line.split(" ", 3); 
				messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
			}

			return new Conversation(conversationName, messages);

		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("The file was not found.");
		} catch (IOException e) {
			throw new Exception("Something went wrong");
		}
	}

	class InstantSerializer implements JsonSerializer<Instant> {
		@Override
		public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
			return new JsonPrimitive(instant.getEpochSecond());
		}
	}
}
