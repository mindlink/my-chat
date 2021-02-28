package com.mindlinksoft.recruitment.mychat.exceptions;

public class EmptyTextFileException extends Exception {

    public EmptyTextFileException(String errorMessage, Throwable e)
    {
        super(errorMessage, e);
    }
}
