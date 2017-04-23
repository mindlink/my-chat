package com.mindlinksoft.recruitment.mychat.messages;

public class InvalidMessageException extends Exception {


	public InvalidMessageException(String message) {
        super("Unable to parse folowing message:\""+message+"\"");
    }
}
