package com.mindlinksoft.recruitment.mychat;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;

public class TestUtilities {
	
	private static final String[] sampleConversation = {
			"1448470901 bob Hello there!",
			"1448470905 mike how are you?",
			"1448470906 bob I'm good thanks, do you like pie?",
			"1448470910 mike no, let me ask Angus...",
			"1448470912 angus Hell yes! Are we buying some pie?",
			"1448470914 bob No, just want to know if there's anybody else in the pie society...",
			"1448470915 angus YES! I'm the head pie eater there..."
	};
	
	public static Conversation getSampleConversation(String[] msgStrings) {
		Collection<Message> messages = new ArrayList<Message>();
		
		for(String msg: msgStrings) {
			messages.add(Message.parseMessage(msg));
		}
		
		return new Conversation("My Conversation", messages);	
	}
	
	
	public static void createSampleReadFile(String fileName) {
        try (OutputStream os = new FileOutputStream(fileName, false);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {
        	
        	bw.write("My Conversation\n");
        
        	for(String message: sampleConversation) {
        		bw.write(message + "\n");
        	}		
        	
        } catch (IOException e) {
        	throw new RuntimeException("TestUtilities failed: createSampleReadFile", e);
        }
        
	}
}
