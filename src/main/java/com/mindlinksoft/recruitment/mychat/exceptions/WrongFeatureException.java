package com.mindlinksoft.recruitment.mychat.exceptions;

public class WrongFeatureException extends Exception{
    public WrongFeatureException(String errorMessage){
        super(errorMessage+":\n <report> OR <obfuscate>");
    }
}
