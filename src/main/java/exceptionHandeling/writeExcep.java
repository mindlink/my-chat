package exceptionHandeling;

/**
 * class responsible for writing file exception
 * 
 * @author Asmaa
 *
 */
public class writeExcep extends RuntimeException {

	public writeExcep(){

	}

	public writeExcep(String msg){
		super(msg);
	}
	
	public writeExcep (Throwable issue){
		super(issue);
	}
	
	public writeExcep(String msg, Throwable issue) {
		super(msg, issue);
	}
}
