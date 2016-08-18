package com.mindlinksoft.recruitment.mychat;

import java.io.IOException;

public class InputException extends IOException{
	public InputException() {
		super();
	}
	
	public String getMessage() {
		return ("Reading the input has failed. Please check your access rights to the file.\n");	
	}
}
