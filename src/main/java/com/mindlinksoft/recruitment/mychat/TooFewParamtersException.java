package com.mindlinksoft.recruitment.mychat;

public class TooFewParamtersException extends MalformedOptionalCLIParameterException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3417759324411187812L;

	public TooFewParamtersException() {
		super();
	}
	
	public TooFewParamtersException(String message) {
		super(message);
	}
}
