package com.mindlinksoft.recruitment.mychat;

public abstract class Functionality {
	//Gets if commands needs parameters
	protected Boolean requireParameters;
	//Gets the number of supported parameters
	protected int supportedParameters;

	
	public Functionality(Boolean requireParameters, int supportedParameters) {
		this.requireParameters = requireParameters;
		this.supportedParameters = supportedParameters;
	
		
	}
	public abstract void processParameters(String parameters);
	public abstract Boolean applyFunctionality(ParsedLine parsedLine);
	
	public Boolean getRequireParameters() {
		return this.requireParameters;
	}
	
	
}
