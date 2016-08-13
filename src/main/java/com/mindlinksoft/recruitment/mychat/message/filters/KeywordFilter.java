package main.java.com.mindlinksoft.recruitment.mychat.message.filters;

import main.java.com.mindlinksoft.recruitment.mychat.message.Message;


public class KeywordFilter implements IMessageFilter {
	private final String keywordFilterPattern;
	
    public KeywordFilter(String keywordFilter) {
		if (keywordFilter == null || keywordFilter.equals("")) {
			throw new IllegalArgumentException(
					"Cannot initialize a KeywordFilter with a null or empty keyword");
		}
        this.keywordFilterPattern = String.format(".*\\b%s\\b.*", keywordFilter.toLowerCase());
    }
	
	public boolean validate(Message candidate){
		if (candidate.getContent() == null) return false;
		
		return candidate.getContent().toLowerCase()
				.matches(keywordFilterPattern.toLowerCase());
	}
}
