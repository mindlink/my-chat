package com.mindlinksoft.recruitment.mychat;

/**
 * Exception for Invalid Arguments using command line
 * Created by alvaro on 15/03/16.
 */
public class InvalidArgumentException extends Exception {

    public InvalidArgumentException() { super(); }
    public InvalidArgumentException(String message) { super(message); }
    public InvalidArgumentException(String message, Throwable cause) { super(message, cause); }
    public InvalidArgumentException(Throwable cause) { super(cause); }
}
