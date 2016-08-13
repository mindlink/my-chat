package main.java.com.mindlinksoft.recruitment.mychat.message.processors;

import java.util.List;
import java.util.regex.Pattern;

import main.java.com.mindlinksoft.recruitment.mychat.message.Message;

public class MessageContentObfuscator implements IMessageProcessor{

	private final String obfuscationPattern;
	
	public MessageContentObfuscator(boolean hideSensitiveNumbers, List<String> blacklistedWords){
		if (!hideSensitiveNumbers && blacklistedWords.isEmpty()) 
			throw new IllegalArgumentException("Cannot start a Content obfuscator with both hideSensitiveNumbers false and blacklistedWords empty");
		
		this.obfuscationPattern = MessageContentObfuscator.buildPattern(hideSensitiveNumbers, blacklistedWords);
	}
	
	private static String buildPattern(boolean matchSensitiveNumbers, List<String> wordsList){
		boolean wordsListExists = !wordsList.isEmpty();
		
		StringBuilder builder = new StringBuilder();
		
		if (matchSensitiveNumbers && wordsListExists){
			builder.append("(?:");
		}
		if (matchSensitiveNumbers) {
			//matches both phone numbers and credit cards
			//for security reasons and global localization we prefer more false positives than not being secure enough
			builder.append("\\b(?:\\d[-]*){6,20}\\b");
		}
		if (matchSensitiveNumbers && wordsListExists){
			builder.append("|");
		}
		if (wordsListExists) {
			builder.append("(\\b");
			for (int i=0; i < wordsList.size(); i++){
				if (i > 0) builder.append("\\b|\\b");
				builder.append(Pattern.quote(wordsList.get(i)));
			}
			builder.append("\\b)");
		}
		if (matchSensitiveNumbers && wordsListExists){
			builder.append(")");
		}
		
		return builder.toString();
	}

	public void process(Message message) {
		String content = message.getContent();
		String result = content.replaceAll(this.obfuscationPattern, "*redacted*");
		message.setContent(result);
	}
}