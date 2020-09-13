package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {
	private final int userFilterIndex = 0;
	private final int keywordFilterIndex = 1;
	private final int blacklistFilterIndex = 2;

    /**
     * The application entry point.
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        
        ArrayList<ArgumentData> packagedArguments = exporter.packageArguments(configuration.getFilterSettings(), configuration.getFilterValues());

        System.out.println("Exporting conversation from '" + configuration.inputFilePath + "' to '" + configuration.outputFilePath + "'");
        exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, packagedArguments);
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(String inputFilePath, String outputFilePath, ArrayList<ArgumentData> arguments) throws Exception {
        Conversation conversation = this.readConversation(inputFilePath, arguments);

        this.writeConversation(conversation, outputFilePath);

        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath + "'");
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws Exception {
    	
    	//Deletes any old existing JSON files
    	boolean fileDeleted = deleteOldJSON(outputFilePath);
    	if (fileDeleted) {
    		System.out.println("Old JSON file deleted");
    	}
    	
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFilePath, true)))) {

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

            Gson g = gsonBuilder.create();

            bw.write(g.toJson(conversation));
            bw.close();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File " + outputFilePath + " could not be found to written to");
        } catch (IOException e) {
            throw new Exception("Error writing conversation to JSON file. File was written to after the write stream had been closed.");
        }
    }

    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation readConversation(String inputFilePath, ArrayList<ArgumentData> arguments) throws Exception {
        try(BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath)))) {
        	
            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;

            ArrayList<String> users = new ArrayList<String>(); 
            
            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ");
                
                String fullMessage = this.reconstructMessage(Arrays.copyOfRange(split, 2, split.length));
                	
                //Add message if either user filter is disabled OR if filter is enabled and user matches
                if ((arguments.get(userFilterIndex).filterEnabled == false) || (arguments.get(userFilterIndex).filterEnabled && arguments.get(userFilterIndex).filterValue.get(0).toUpperCase().equals(split[1].toUpperCase()))) {
                	
                	//If the keyword filter is disabled OR if the filter is enabled and the string contains the keyword
                	if ((arguments.get(keywordFilterIndex).filterEnabled == false) || (arguments.get(keywordFilterIndex).filterEnabled && fullMessage.toUpperCase().contains(arguments.get(keywordFilterIndex).filterValue.get(0).toUpperCase()))) {
                        
                		//If there are words to redact from the blacklist
                		if (arguments.get(blacklistFilterIndex).filterEnabled) {
                			fullMessage = redact(fullMessage, arguments.get(blacklistFilterIndex).filterValue);
                		}

                		users.add(split[1]);
                		try {
                			messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], fullMessage));
                		} catch (NumberFormatException e) {
                        	System.out.println("Invalid chat log - log appears to be missing a time entry at one stage. Placeholder used");
                        	fullMessage = this.reconstructMessage(Arrays.copyOfRange(split, 1, split.length));
                			messages.add(new Message(Instant.ofEpochSecond(0000000000), split[0], fullMessage));
                        }
                	}
                }
            }

            return new Conversation(conversationName, messages, users);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The following file was not found: " + inputFilePath);
        } catch (IOException e) {
            throw new Exception("Something went wrong");
        } 
    }
    
    private String redact (String message, ArrayList<String> wordsToRedact) {
    	String[] messageComponents = message.split(" ");
    	
    	for (int counter = 0; counter < messageComponents.length; counter++) {
    		for (String blackListWord : wordsToRedact) {
    			
    			//If the word (made uppercase and with punctuation stripped) equals the blacklisted word
    			if (messageComponents[counter].replaceAll("\\p{P}", "").toUpperCase().equals(blackListWord.replaceAll("\\p{P}", "").toUpperCase())) {
    				System.out.println("redacting " + messageComponents[counter]);
    				
    				//If string section ends with punctuation
    				if (messageComponents[counter].matches(".*\\p{Punct}")) {
    					char punctuationToAdd = messageComponents[counter].charAt(messageComponents[counter].length() - 1);
        				messageComponents[counter] = "*REDACTED*" + punctuationToAdd;
    				} else {
        				messageComponents[counter] = "*REDACTED*";
    				}
    			}
    		}
    	}
    	
    	message = this.reconstructMessage(messageComponents);
    	
    	return message;
    }
    
    public ArrayList<ArgumentData> packageArguments(ArrayList<Boolean> filterEnabled, ArrayList<ArrayList<String>> filterValues) {
    	ArrayList<ArgumentData> arguments = new ArrayList<ArgumentData>();
    	
    	for (int index = 0; index < filterEnabled.size(); index++) {
    		arguments.add(new ArgumentData(filterEnabled.get(index), filterValues.get(index)));
    	}
    	
    	return arguments;
    }
    
    /**
     * A method to delete the old JSON file so as not to simply append to the file
     * @param path The path of the file to remove
     * @return Returns a boolean determening if a file was deleted for feedback purposes
     * @throws IOException Thrown when the program is unable to delete a file
     */
    private boolean deleteOldJSON(String path) throws Exception {
    	try {
    		File file = new File(path);
    		boolean result = Files.deleteIfExists(file.toPath());
    		return result;
    	} catch (IOException e) {
    		throw new Exception("Unable to delete file");
    	}
    }
    
    /**
     * Reconstructs a string from an array of sub-strings. In this case,
     * used to reconstruct the split parts of the message
     * @param components The split parts of the message that need to be recombined
     * @return The original message in it's whole form
     */
    private String reconstructMessage(String[] components) {
    	String finalMessage = "";
    	
    	for (int i = 0; i < components.length; i++) {
    		finalMessage = finalMessage.concat(components[i]);
    		if (i < components.length-1) {
    			finalMessage = finalMessage.concat(" ");
    		}
    	}
    	return finalMessage;
    }

    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
