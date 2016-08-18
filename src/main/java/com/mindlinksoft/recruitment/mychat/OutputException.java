package com.mindlinksoft.recruitment.mychat;

import java.io.IOException;

public class OutputException extends IOException{
	public OutputException() {
		super();
	}
	
	public String getMessage() {
		return "Writing the output has failed. Please check your access rights to the file.\n";
	}
}
