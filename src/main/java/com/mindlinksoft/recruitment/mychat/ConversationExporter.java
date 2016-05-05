package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {

    /**
     * The application entry point.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {   	
        ConversationExporter exporter = new ConversationExporter();
        Logger logger = null;
        
        try {
        	logger = exporter.getLogger();
        } 
        catch (SecurityException | IOException e) {
        	System.out.format(Const.LOG_ERROR, e.getMessage());
        }
        
        try {
        	ConversationExporterConfiguration configuration = CommandLineArgumentParser.parseCommandLineArguments(args);   	
        	exporter.exportConversation(configuration); 
        } 
        catch (Exception e) { // Catch all exceptions so we don't output un-user-friendly stuff to the console, stack trace etc.
        	
        	System.out.format(Const.ERROR, e.getMessage());
        	
        	if(logger!=null) {
        		logger.log(Level.SEVERE, e.getMessage(), e);
        	}
        }
        
        System.out.println(Const.EXIT);
    }
    
    /**
     * Gets Java.util.Logging.Logger to log Runtime Exceptions before close.
     * @return Logger
     * @throws SecurityException If a security manager exists and {@code fileTxt} doesn't have permission.
     * @throws IOException If there are IO problems opening the file.
     */
    public Logger getLogger() throws SecurityException, IOException {
    	FileHandler fileTxt = new FileHandler(Const.LOG_FILENAME, true);
        SimpleFormatter formatter = new SimpleFormatter();
        fileTxt.setFormatter(formatter);
        
    	Logger logger = Logger.getLogger(ConversationExporter.class.getName());
    	logger.addHandler(fileTxt);
    	logger.setUseParentHandlers(false);
    	
    	return logger;
    }
    
    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param configuration The ConversationExporterConfiguration.
     */
    public void exportConversation(ConversationExporterConfiguration configuration) throws FileNotFoundException, IOException {
        String outputFilePath = configuration.getOutputFilePath();      	
    	String inputFilePath = configuration.getInputFilePath();
    	      
        Conversation conversation = this.readConversation(inputFilePath);        
        
        boolean canWrite = checkWrite(outputFilePath, configuration.forceOverwrite());
        
        if(configuration.hasFilters()) {
        	conversation = Conversation.applyFilters(conversation, configuration.getFilters());
        }
        
        if(configuration.useReport()) {
        	conversation = Conversation.addReport(conversation, Report.getUserReport(conversation));
        }
        
        if(canWrite) {       	
        	this.writeConversation(conversation, outputFilePath);
       	
        	System.out.format(Const.SUCCESSFUL_EXPORT, inputFilePath, outputFilePath);
        }
    }
    
    /**
     * Represents a helper to check if {@link ConversationExporter} should overwrite {@code outputFilePath}.
     * @param outputFilePath The path to the output file.
     * @param forceOverwrite Boolean set in {@link ConversationExporterConfiguration}.
     * @return Boolean Overwrite.
     */
    public boolean checkWrite(String outputFilePath, boolean forceOverwrite) {   
    	if(forceOverwrite) {
    		return true;
    	}
    	
    	File f = new File(outputFilePath);
    	
    	if(f.exists()) {    		
    		System.out.format(Const.FILE_ALREADY_EXISTS, outputFilePath);
    		Scanner s = new Scanner(System.in);
    		
    		while(true) {
    			System.out.println(Const.ASK_OVERWRITE); 
    			String response = s.next();

    			if(response.equals(Const.YES)) {
    				s.close();
    				return true;
    			}
    			else if(response.equals(Const.NO)) {
    				s.close();
    				return false;
    			} else {
    				System.out.println(Const.INVALID_RESPONSE); 
    			}
    		}
    	} else {
    		return true;
    	}    	
    }
    
    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} represented by the input file.
     * @throws FileNotFoundException Thrown file can not be found/opened.
     * @throws IOException Thrown if there is an error while reading.
     */
    public Conversation readConversation(String inputFilePath) throws FileNotFoundException, IOException {
    	System.out.format(Const.READING_CONVERSATION, inputFilePath);  
    	
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                Message m = Message.parseMessage(line);    
                messages.add(m);
            }
            
            Conversation conversation = new Conversation(conversationName, messages);
            
            System.out.println(Const.COMPLETE);
            return conversation;
        } catch (FileNotFoundException e) {
        	throw new FileNotFoundException(String.format(Const.FILE_NOT_FOUND, inputFilePath));
        } catch (IOException e) {
            throw new IOException(String.format(Const.FILE_READ_ERROR, inputFilePath), e);
        }
    }
    
    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws FileNotFoundException Thrown file can not be created or opened.
     * @throws IOException Thrown if there is an error while writing.
     */
    public void writeConversation(Conversation conversation, String outputFilePath) throws FileNotFoundException, IOException {
    	System.out.format(Const.WRITING_CONVERSATION, outputFilePath);
    	
        try (OutputStream os = new FileOutputStream(outputFilePath, false);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

        	Gson g = setupGson();
        			
            bw.write(g.toJson(conversation));
            
            System.out.println(Const.COMPLETE);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(String.format(Const.FILE_CREATE_ERROR, outputFilePath));
        } catch (IOException e) {
            throw new IOException(String.format(Const.FILE_WRITE_ERROR, outputFilePath), e);
        }
    }
    
    /**
     * Helper method to build MyChat Gson exporter. Adds serializers for any unsupported classes.
     * @return Gson Google's JSON exporter tool.
     */
    public Gson setupGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

        return gsonBuilder.create();
    }

    /**
     * Custom Json Serializer for Time.Instant which isn't currently a supported GSON type.
     */
    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
