package com.mindlinksoft.recruitment.mychat.message;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.Validate;

/**
 * Message formatter that redacts regex matches.
 *
 */
public class RegexRedactingMessageFormatter implements IMessageFormatter {

	private static final String REDACTED_STRING = "*redacted*";
	private Collection<String> regexList = new ArrayList<String>();
	
	public RegexRedactingMessageFormatter(String regex) {
		this.regexList.add(Validate.notNull(regex));
	}
	
	public RegexRedactingMessageFormatter(Collection<String> regexList) {
		this.regexList = Validate.noNullElements(regexList);
	}
	
	@Override
	public IMessage format(IMessage message) {
		String msgContent = message.getContent();
		for (String regex : regexList) {
			msgContent = msgContent.replaceAll(regex, REDACTED_STRING);
		}
		message.setContent(msgContent);
		return message;
	}

	/**
	 * Adds a regex to the blacklist.
	 * @param regex
	 */
	public void addRegex(String regex) {
		if (regex != null) {
			regexList.add(regex);
		}
	}
}
