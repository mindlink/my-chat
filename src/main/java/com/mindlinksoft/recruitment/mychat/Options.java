package com.mindlinksoft.recruitment.mychat;

/**
 * Defines acceptable option values
 * */
final class Options {
		final static char FILTER_USERNAME 	= 'u';
		final static char FILTER_KEYWORD 	= 'k';
		final static char FILTER_BLACKLIST	= 'b';
		
		static boolean needsSingleValue(char c) {
			return false;
			
		}
		
		static boolean needsManyValues(char c) {
			return false;
			
		}
		
		static boolean needsNoValue(char c) {
			return false;
			
		}
		
//		static boolean isFilter(char c) {
//			return false;
//		}
};
