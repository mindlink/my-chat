package com.mindlinksoft.recruitment.mychat;

/**
 * Represents a user, overriding the equals(), hashCode() and compareTo() method, allows for
 * custom ordering and comparisons and users to ensure no duplicates and user defined ordering.
 * 
 * @author Mohamed Yusuf
 *
 */
public class User implements Comparable<User>{
	
	private transient String original_username;
	private int numberOfMessages;
	private String username;
	
	/**
	 * Initialises a user object
	 * @param username name provided.
	 */
	public User(String username) {
		this.original_username = username;
		this.username = username;
	}
	
	/**
	 * Initialises a user object
	 * @param username name provided.
	 * @param numberOfMessages number of messages sent by user.
	 */
	public User(String username, int numberOfMessages) {
		this.original_username = username;
		this.username = username;
		this.numberOfMessages = numberOfMessages;
	}
	
	/**
	 * Obfuscate the user name by using the hash function
	 */
	public void obfuscateUsername() {
		this.username = String.valueOf(this.original_username.hashCode());
	}
	
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.original_username = username;
	}

	public String getOrigianlUsername() {
		return this.original_username;
	}
	
	public int getNumberOfMessages() {
		return numberOfMessages;
	}

	public void setNumberOfMessages(int numberOfMessages) {
		this.numberOfMessages = numberOfMessages;
	}
	
	/**
	 * Allows for more convenient printing of users
	 */
	@Override
    public String toString() { 
        return this.original_username + " " + this.numberOfMessages;
    }
	
	/**
	 * Ensures that the hash code generated only uses the user name and not additional,
	 * information such are memory location. This allows for users with the same name
	 * to be identified as the same user even when in different memory locations.
	 */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.original_username == null) ? 0 : this.original_username.hashCode());
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
    	if (this == obj)	//Check if reference
    		return true;
    	if (obj == null)	//Check if null
    		return false;
    	if (!(obj instanceof User))	//Check if same class
            return false;
    	
    	final User user = (User) obj;
    	
    	return this.original_username.hashCode() == user.original_username.hashCode();
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
	public int compareTo(User user) {
		if(this.equals(user))
			return 0;
		
		return this.numberOfMessages <= user.numberOfMessages ? 1 : -1;
	}
}
