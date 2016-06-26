package com.mindlinksoft.recruitment.mychat;

/**
 * Defines acceptable option parameters (other than the mandatory file paths)
 * and operations that help identify the type of option they represent to both
 * the parser and the filtering units.
 * */
final class Options {

	/**
	 * The array of acceptable option values
	 * */
	final static char [] set = {
			'u',
			'k',
			'b'
	};
	
	/**
	 * @return true Where the option is recognized as a flag, false otherwise
	 * */
	static boolean isFlag(char c) {
		return false;
	}
	
	/**
	 * @return true Where the option can accept more than one parameter value,
	 * false otherwise
	 * */
	static boolean mayBeList(char c) {
		switch(c) {
		case 'b':
			return true;
			default:
				return false;
		}
	}
	
	/**
	 * @return true Where the option must be followed by one parameter value, 
	 * false otherwise
	 * */
	static boolean needsValue(char c) {
		switch(c) {
		case 'u':
		case 'k':
			return true;
			default:
				return false;
		}
	}
}
