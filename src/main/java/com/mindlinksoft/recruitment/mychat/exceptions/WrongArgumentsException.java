package com.mindlinksoft.recruitment.mychat.exceptions;

public class WrongArgumentsException extends BadArgumentsException {

    private static final String EXCEPTION_MSG = "Please specify the input and output files correctly.";

    public WrongArgumentsException() {
        super(EXCEPTION_MSG);
    }
}