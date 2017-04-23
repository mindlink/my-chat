package com.mindlinksoft.recruitment.mychat.messages;

import java.time.Instant;

public class MessageParser {
	private final Integer MESSAGECOMPONENTSCOUNT = 3;
	
	/**
	 * Parses the {@code messageString} into a Message
	 * @param messageString
	 * @return Message
	 * @throws NumberFormatException
	 * @throws InvalidMessageException
	 */
	public Message parse(String messageString) throws NumberFormatException,InvalidMessageException {

		String[] messageComponents = messageString.split(" ", MESSAGECOMPONENTSCOUNT);

		try {
			if (messageComponents.length < MESSAGECOMPONENTSCOUNT)
				throw new InvalidMessageException(messageString);

			Instant timestamp = Instant.ofEpochSecond(Long.parseUnsignedLong(messageComponents[0]));
			String username = messageComponents[1];
			String text = messageComponents[2];
			return new Message(timestamp, username, text);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Could not parse the value:"+ messageComponents[0]+" to an Instance:");
		}
	}
}
