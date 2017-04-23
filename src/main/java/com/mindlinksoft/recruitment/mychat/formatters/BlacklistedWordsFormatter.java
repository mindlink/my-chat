package com.mindlinksoft.recruitment.mychat.formatters;
import com.mindlinksoft.recruitment.mychat.messages.Message;

/**
 * A {@link Formatter} that replace list on specified words
 * by the string "*redacted*"
 * @author Omar
 *
 */

public class BlacklistedWordsFormatter implements IFormatter  {
	
	//List of words to be replaced
	private final String[] blacklistWords;
	
	//The string to replace with 
	private final String replacement = "*redacted*";
	
	public BlacklistedWordsFormatter(String[] blacklistWords)
	{
		this.blacklistWords = blacklistWords;
	}

	/**
	 * Replaces the occurrences of black listed words
	 * in the message content by the string "*redacted*"
     * @param message
	 */
	@Override
	public Message apply(Message message) {

		String content = message.getContent();
		for (String blacklistWord : blacklistWords) {
			content = content.replaceAll("(?i)\\b" + blacklistWord.trim() + "\\b", replacement);
		}
		message.setContent(content);

		return message;
	}
}
