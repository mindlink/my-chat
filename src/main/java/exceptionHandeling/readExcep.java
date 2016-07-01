package exceptionHandeling;

/**
 * class used to handle reading file exception
 * 
 * @author Asmaa
 *
 */
public class readExcep extends RuntimeException{
	public readExcep(){

	}

	public readExcep(String msg){
		super(msg);
	}
	
	public readExcep (Throwable issue){
		super(issue);
	}
	
	public readExcep(String msg, Throwable issue) {
		super(msg, issue);
	}
}
