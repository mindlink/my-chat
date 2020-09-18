package com.mindlinksoft.recruitment.mychat.exceptions;

public class WrongFeatureException extends Exception {

    private static final String EXCEPTION_MSG = "Wrong format of the features.";

    public WrongFeatureException() {
        super(EXCEPTION_MSG);
    }
}

