package com.mindlinksoft.recruitment.mychat;

import java.util.HashMap;

/**
 * Arguments use a flag/target model, where each argument can either accept optional targets or not,
 * this HashMap defines the argument flags and if additional targets are permitted.
 * 
 * @author Mohamed Yusuf
 *
 */
public class FlagProperties {
	//Specifies flag and number of targets
	private HashMap<String, Integer> FLAGS;
	
	/**
	 * The constructor initialises, the acceptable flags
	 * 
	 * 1: Allows for any number of optional arguments
	 * -1: No arguments allowed
	 */
	public FlagProperties() {
		this.FLAGS = new HashMap<String, Integer>()
        {{
            put("-fu", 1);
            put("-fw", 1);
            put("-b", 1);
            put("-oc", -1);
            put("-ou", -1);
            put("-r", -1);
        }};
	}

	public HashMap<String, Integer> getFLAGS() {
		return FLAGS;
	}
}
