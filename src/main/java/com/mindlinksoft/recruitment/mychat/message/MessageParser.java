package com.mindlinksoft.recruitment.mychat.message;

import com.mindlinksoft.recruitment.mychat.exception.MessageFormatException;
import java.time.Instant;
import org.apache.commons.lang3.StringUtils;

/**
 * Message parser class.
 *
 * @author Gabor
 */
public class MessageParser {

    /**
     * Parses the raw message to {@link Message}. As the format of the messages
     * should always be the same it uses a simple split. (Using regex here would
     * be an overkill.)
     *
     * @param message
     * @return
     * @throws NumberFormatException
     * @throws MessageFormatException
     */
    public Message parse(final String message) throws NumberFormatException, MessageFormatException {
        String[] messageParts = StringUtils.split(message, " ", 3);

        if (messageParts.length != 3) {
            throw new MessageFormatException(message);
        }

        Instant timestamp = Instant.ofEpochSecond(Long.parseUnsignedLong(messageParts[0]));
        String senderId = messageParts[1];
        String content = messageParts[2];

        return new Message(timestamp, senderId, content);
    }
}
