package com.mindlinksoft.recruitment.mychat;

/**
 * enum responsible for declaring the command line names for values
 * @author Asmaa
 *
 */
public enum ConvoConfig{
	/*EXPORT("exp"),*/ INPUT("in"),OUTPUT("out"),/*FILTER("f"),*/ USER("u"),KEYWORD("k"), BLACKLIST("bl");
	
	private String value;
	

	private ConvoConfig(String value) {
		this.value = value;
	}	

	public String getValue() {
		return value;
	}
}