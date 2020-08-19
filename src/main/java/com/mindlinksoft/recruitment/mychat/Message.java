package com.mindlinksoft.recruitment.mychat;

import java.awt.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

/**
 * Represents a chat message.
 */
public final class Message {
    /**
     * The message content.
     */
    public String content;

    /**
     * The message timestamp.
     */
    public Instant timestamp;
    public String StringTS;

    /**
     * The message sender.
     */
    public String senderId;

    /** code derived from https://stackoverflow.com/questions/50498949/how-to-convert-utc-date-string-and-remove-the-t-and-z-in-java
     * converts the timestamp to a more user friendly format */
    
    public  String convertToNewFormat(String timestamp) throws ParseException { 
        TimeZone utc = TimeZone.getTimeZone("UTC");
        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sourceFormat.setTimeZone(utc);
        Date convertedDate = sourceFormat.parse(timestamp);
        return destFormat.format(convertedDate);
    }
    
//    public static String ShuffleSenderID(String senderId) {
//    	List shuffledID = new List();
//    	shuffledID.add(senderId);
//    	Collections.shuffle((java.util.List<?>) shuffledID);
//    	String readyID = shuffledID.getItem(0);
//    	return readyID;
//    }
    /**
     * Initializes a new instance of the {@link Message} class.
     * @param timestamp The timestamp at which the message was sent.
     * @param senderId The ID of the sender.
     * @param content The message content.
     * @throws Exception 
     */
    public Message(Instant timestamp, String senderId, String content) throws Exception {	
        this.content = content;
        this.timestamp = timestamp;
        this.senderId = senderId;
        StringTS = this.timestamp.toString();
        try {
			StringTS = convertToNewFormat(StringTS);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new Exception("The timestamp format does not match the expected format");
		}
    }
    
    
}
