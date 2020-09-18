package com.mindlinksoft.recruitment.mychat.exceptions;

public class InsufficientArgumentsException extends BadArgumentsException {

    private static final String EXCEPTION_MSG = "Please add the input and output file paths.";

    public InsufficientArgumentsException() {
        super(EXCEPTION_MSG);
    }
}
