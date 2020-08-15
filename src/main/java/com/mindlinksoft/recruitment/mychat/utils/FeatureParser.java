package com.mindlinksoft.recruitment.mychat.utils;

import com.mindlinksoft.recruitment.mychat.features.BlacklistFeature;
import com.mindlinksoft.recruitment.mychat.features.ChatFeature;
import com.mindlinksoft.recruitment.mychat.features.HideNumbersFeature;
import com.mindlinksoft.recruitment.mychat.features.KeywordFilterFeature;
import com.mindlinksoft.recruitment.mychat.features.ObfuscateUserFeature;
import com.mindlinksoft.recruitment.mychat.features.UserFilterFeature;

/**
 * Class to parse a Feature from a string
 *
 */
public class FeatureParser 
{
	/**
	 * Function takes a string argument and returns appropriate feature indicated by flag
	 * @param arg String argument from command line
	 * @return Appropriate ChatFeature indicated by flag in argument
	 */
	public static ChatFeature parse(String arg) throws IllegalArgumentException
	{
		
		switch(arg.charAt(1))
		{
			case 'u':
				return new UserFilterFeature(parseArgument(arg));
			case 'k':
				return new KeywordFilterFeature(parseArgument(arg));
			case 'b':
				return new BlacklistFeature(parseArgument(arg));
			case 'o':
				return new ObfuscateUserFeature();
			case 'h':
				return new HideNumbersFeature();
			default:
				//Unrecognised flag
				throw new IllegalArgumentException("parseCommandLineArguments: Invalid flag - '" + arg + "'");
		}
	}
	
	/**
	 * Function used to pass argument into ChatFeature if it is required
	 * @param arg String argument from command line
	 * @return String containing argument to be passed into ChatFeature
	 */
    private static String parseArgument(String arg)
    {
    	//Length should be longer than 3 if argument is required
		if(arg.length() <= 3)
		{
			throw new IllegalArgumentException("parseCommandLineArguments: No argument provided for flag - '" + arg + "'");
		}
		else if(arg.charAt(2) != '=')
		{
			//Argument to flag must be provided using a '='
			throw new IllegalArgumentException("parseCommandLineArguments: Arguments not separated from flag using '=' - '" + arg + "'");
		}
		
		return arg.substring(3);
    }
}
