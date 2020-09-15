package com.mindlinksoft.recruitment.mychat.exceptions;

public class IOProblemException extends Exception{

    public IOProblemException(){
        super();
    }

    public  IOProblemException(String message,Throwable cause){
        super(message,cause);
    }
}
