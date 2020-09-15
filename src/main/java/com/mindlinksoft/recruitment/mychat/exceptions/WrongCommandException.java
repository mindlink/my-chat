package com.mindlinksoft.recruitment.mychat.exceptions;

public class WrongCommandException extends Exception{
    public WrongCommandException(String errorMessage){
        super(errorMessage+":\n <user>:<target user> OR <word>:<target word> OR <redact>:<list,of,words>");
    }
}
