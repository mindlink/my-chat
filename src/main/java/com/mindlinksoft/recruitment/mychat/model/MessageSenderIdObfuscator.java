package com.mindlinksoft.recruitment.mychat.model;

import java.util.HashMap;
import java.util.Map;

import com.mindlinksoft.recruitment.mychat.bean.Message;

/**
 * Obfuscates sender ids
 */
public class MessageSenderIdObfuscator {

	private static final int TOTAL_LETTERS = 26;
	private Map<String, String> map_;

	public MessageSenderIdObfuscator() {
		map_ = new HashMap<>();
	}

	public Message obfuscate(Message message) {
		return new Message(message.getTimestamp(), getObfuscatedId(message), message.getContent());
	}

	private String getObfuscatedId(Message message) {
		if (!map_.containsKey(message.getSenderId()))
			generateObfuscatedId(message);
		return map_.get(message.getSenderId());
	}

	private void generateObfuscatedId(Message message) {
		String id = getNextId(map_.size());
		map_.put(message.getSenderId(), id);
	}

	/**
	 * Converts the index value into a base 26 number and uses that number to
	 * create a string that will serve as the obfuscated id.
	 * 
	 * eg. 0 -> A
	 * 
	 * 1 -> B
	 * 
	 * 25 -> Z
	 * 
	 * 26 -> BA
	 */
	protected String getNextId(int index) {
		String id = "";
		while (true) {
			int mod = index % TOTAL_LETTERS;
			char c = (char) ((int) 'A' + mod);
			id = c + id;
			if (index < TOTAL_LETTERS)
				return id;
			index /= TOTAL_LETTERS;
		}
	}
}
