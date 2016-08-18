package com.mindlinksoft.recruitment.mychat;

public class ArgumentsNumberException extends Exception {

	public ArgumentsNumberException() {
		super();
	}

	public String getMessage() {
		return "Providing input and output file names is mandotory.\n A maximum of 100 arguments is allowed";
	}

	
}


