package main.java.com.mindlinksoft.recruitment.mychat.exceptions;

public class EmptyFileException extends Exception{
	private static final long serialVersionUID = -8315228118118053215L;

	public EmptyFileException(String details) {
		super(details);
	}
}
