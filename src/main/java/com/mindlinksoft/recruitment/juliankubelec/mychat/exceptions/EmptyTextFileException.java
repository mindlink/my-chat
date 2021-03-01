package com.mindlinksoft.recruitment.juliankubelec.mychat.exceptions;

/**
 * Exception when a text file is empty
 * used in ConversationExporter for the input text file
 */
public class EmptyTextFileException extends Exception {

    public EmptyTextFileException(String errorMessage, Throwable e)
    {
        super(errorMessage, e);
    }
}
