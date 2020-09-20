package com.mindlinksoft.recruitment.mychat;

import java.time.Instant;
/**
 * Represents a chat message, overriding the equals and compareTo methods, allows for custom
 * comparisons to ensure messages are unique.
 * 
 * @author Mohamed Yusuf
 */
public final class Message implements Comparable<Message>{
	
    private String content;
    private Instant timestamp;
    private String username;
    private transient User userObject;
    //Rather crude concatenation of everything that makes a message
    private transient String messageID;

    /**
     * Initialises a new instance of the {@link Message} class.
     * @param timestamp The time stamp at which the message was sent.
     * @param username The ID of the sender.
     * @param content The message content.
     */
    public Message(Instant timestamp, String username, String content) {
        this.content = content;
        this.timestamp = timestamp;
        this.userObject = new User(username);
        this.username = this.userObject.getUsername();
        this.messageID = this.content + this.getUserObject().getOrigianlUsername() + this.timestamp;
    }
    
    /**
     * Obfuscate user name by calling the obfuscate 
     * method on this user 
     */
    public void obfuscateUsername() {
    	this.userObject.obfuscateUsername();
    	this.username = this.userObject.getUsername();
    }

	public String getContent() {
		return content;
	}

	public Instant getTimestamp() {
		return timestamp;
	}
	
	public User getUserObject() {
		return userObject;
	}

	public String getUsername() {
		return userObject.getUsername();
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public void setUsername(User username) {
		this.userObject = username;
	}
	
	/**
	 * Allows for easy printing of message objects
	 */
	@Override
    public String toString() { 
        return this.userObject.getUsername() + ", " + this.getContent();
    }
	
	/**
	 * Ensures that the hash code generated only uses the user name and not additional,
	 * information such as memory location. This allows for messages with the same messageID
	 * to be identified as the same even when in different memory locations.
	 */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.messageID == null) ? 0 : this.messageID.hashCode());
        return result;
    }
	
	/**
	 * This method overrides the equal method to be used when
	 * comparing message objects. This is essential in ensuring 
	 * the set data structure can compare objects meaningfully and
	 * ensure no duplicates
	 */
    @Override
    public boolean equals(Object obj) {
    	if (this == obj)				//Check if reference
    		return true;	
    	if (obj == null)				//Check if null
    		return false;
    	if(!(obj instanceof Message))	//Check if same class
    		return false;
	
    	final Message mess = (Message) obj;
    	
    	//Note the equals method used is for the String object NOT Message object
    	return this.messageID.equals(mess.messageID);
    }
    
    /**
     * Tree-set comparison relies on this method, it it returns
     * 0 the two items are the same. This relies on the equal method which 
     * was previously overridden.
     * 
     * Criteria used by tree set: 
     * 
     * Positive:  Current instance is greater than Other.
     * Zero:  Two instances are equal.
     * Negative: Current instance is smaller than Other.
     * 
     * where: 
     * 		current instance = item
     * 		other = this
     * 
     */
	@Override
	public int compareTo(Message mess) {
		if(this.equals(mess))
			return 0;
		
		return this.timestamp.compareTo(mess.timestamp);
	}
}
