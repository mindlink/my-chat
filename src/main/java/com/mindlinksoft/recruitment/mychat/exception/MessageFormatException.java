package com.mindlinksoft.recruitment.mychat.exception;

/**
 *
 * @author Gabor
 */
public class MessageFormatException extends Exception {

    public MessageFormatException(final String message) {
        super("Could not parse the following message: \"" + message + "\"");
    }
}
