package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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

        exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, configuration.feature);
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param feature Essential and additional features of the program
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(String inputFilePath, String outputFilePath, String feature) throws Exception {
        switch(feature.toLowerCase()) {
                case "read":
                    Conversation conversation = this.readConversation(inputFilePath);
                    this.writeConversation(conversation, outputFilePath);
                    break;
                case "username":
                    conversation = this.usernameSearch(inputFilePath);
                    this.writeConversation(conversation, outputFilePath);
                    break;
                case "keyword":
                    conversation = this.keywordSearch(inputFilePath);
                    this.writeConversation(conversation, outputFilePath);
                    break;
                case "keyword_redact":
                    conversation = this.keywordSearchAndReplace(inputFilePath);
                    this.writeConversation(conversation, outputFilePath);
                    break;
                case "numbers":
                    conversation = this.replacePhoneAndCard(inputFilePath);
                    this.writeConversation(conversation, outputFilePath);
                    break;
                case "obfuscate":
                    conversation = this.obfuscateUsername(inputFilePath);
                    this.writeConversation(conversation, outputFilePath);
                    break;
                default:
                    break;       
            }
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
 		// TODO: Do we need both to be resources, or will buffered writer close
 		// the stream?
 		try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) {
 			// TODO: Maybe reuse this? Make it more testable...
 			GsonBuilder gsonBuilder = new GsonBuilder();
 			gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
 			Gson g = gsonBuilder.create();
 			String jsonElement = g.toJson(conversation);
 
 			// System.out.println("Writing JSON: " + jsonElement);
 			bw.write(jsonElement);
 			bw.close();
 
 		} catch (FileNotFoundException e) {
 			// // TODO: Maybe include more information?
 			throw new IllegalArgumentException("The file was not found : " + e.getMessage());
 		} catch (IOException e) {
 			throw new Exception("IO Exception: " + e.getMessage());
 		} catch (NullPointerException e) {
 			// OR stacktrace
 			e.printStackTrace();
 		}
 	}

 /**
     * BLOCK OF ESSENTIAL FEATURES
     */
    
    /**
     * General reading method
     * @param inputFilePath
     * @return Conversation
     * @throws Exception 
     */
    public Conversation readConversation(String inputFilePath) throws Exception {
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ");
                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found.");
        } catch (IOException e) {
            throw new Exception("Something went wrong");
        }
    }
    
    /**
     * Method that reads messages sent only by a specific user
     * @param inputFilePath
     * @return conversation
     * @throws Exception 
     */
    public Conversation usernameSearch(String inputFilePath) throws Exception{
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {
            
            //set up string scanner
            Scanner scan = new Scanner(System.in);
            
            //ask user for a username to search
            System.out.println("What username do you want to find?");
            String name = scan.nextLine();
            
            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;
            
            while((line = r.readLine()) != null) {
                String[] split = line.split(" ");
                if ((split[1].toLowerCase().contains(name.toLowerCase())) == true) {
                    messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
                }
                    
            }
            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found: " + e.getMessage());
        } catch (IOException e) {
            throw new Exception("Caught IO exception: " + e.getMessage());
        }     
    }
    
    /**
     * Method that generates the output only with messages that contain a keyword
     * @param inputFilePath
     * @return
     * @throws Exception 
     */
    public Conversation keywordSearch(String inputFilePath) throws Exception{
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {
            
            //set up string scanner
            Scanner scan = new Scanner(System.in);
            
            //ask user for a username to search
            System.out.println("What word do you want to find?");
            String keyword = scan.nextLine();
            
            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;
            
            while((line = r.readLine()) != null) {
                String[] split = line.split(" ");
                if ((split[2].toLowerCase().contains(keyword.toLowerCase())) == true) {
                    messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
                }
                    
            }
            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found: " + e.getMessage());
        } catch (IOException e) {
            throw new Exception("Caught IO exception: " + e.getMessage());
        }
            
    }
    
    /**
     * Method that searches for the keyword in the conversation and redacts it
     * @param inputFilePath
     * @return
     * @throws Exception 
     */
    public Conversation keywordSearchAndReplace(String inputFilePath) throws Exception{
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {
            
            //set up string scanner
            Scanner scan = new Scanner(System.in);
            
            //ask user for a username to search
            System.out.println("What word do you want to redact?");
            String keyword_redact = scan.nextLine();
            
            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;
            String red = "REDACTED";
            
            while((line = r.readLine()) != null) {
                String[] split = line.split(" ");
                if ((split[2].toLowerCase().contains(keyword_redact.toLowerCase())) == true) {
                    keyword_redact = red;
                }
                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));   
            }
            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found: " + e.getMessage());
        } catch (IOException e) {
            throw new Exception("Caught IO exception: " + e.getMessage());
        }
            
    }
    
    /**
     * BLOCK OF ADDITIONAL FEATURES
     */
    
    /**
     * Method that replaces all phone and card numbers
     * @param inputFilePath
     * @return
     * @throws Exception 
     */
    public Conversation replacePhoneAndCard(String inputFilePath) throws Exception{
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {
                        
            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;
            String red = "*REDACTED*";
            String pattern =  "\\b(([0-9]{11})|([0-9]{16}))\\b";
            
            while((line = r.readLine()) != null) {
                String[] split = line.split(" ");
                if((split[2].contains(pattern)) == true) {
                    pattern = red;
                }
                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));     
            }
            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found: " + e.getMessage());
        } catch (IOException e) {
            throw new Exception("Caught IO exception: " + e.getMessage());
        }     
    }
    
    /**
     * Method that obfuscaes the username
     * @param inputFilePath
     * @return
     * @throws Exception 
     */
    public Conversation obfuscateUsername(String inputFilePath) throws Exception{
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {
                        
            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;
            String source = "POIUYTREWQLKJHGFDSAMNBVCXZpoiuytrewqlkjhgfdsamnbvcxz1234567890";
            String target = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM0987654321";
            
            while((line = r.readLine()) != null) {
                String[] split = line.split(" ");
                char[] result = new char[split[1].length()];    //create char array equal to the length of the name
                for (int i = 0; i < split[1].length(); ++i) {
                    char c = split[1].charAt(i);
                    int j = source.indexOf(c);
                    result[i] = target.charAt(j);
                }
                String res = new String(result);
                split[1] = res;
                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
                    
            }
            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file was not found: " + e.getMessage());
        } catch (IOException e) {
            throw new Exception("Caught IO exception: " + e.getMessage());
        }     
    }

    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
