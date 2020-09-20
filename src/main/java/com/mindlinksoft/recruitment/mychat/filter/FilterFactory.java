package com.mindlinksoft.recruitment.mychat.filter;

import java.util.HashMap;
import java.util.Map;

/**
 * This class handles all the creation of filter objects,
 * furthermore new filters will need to be "registered" within the
 * filter block.
 * 
 * @author Mohamed Yusuf
 *
 */
public class FilterFactory {
	
	private static final Map<FilterType, Filter> filters = new HashMap<>();
	
	/**
	 * Static block used to add instances of filters to 
	 * filter map(static, therefore cannot use constructor)
	 * and executed only once in the program life-cycle
	 */
	static {
		filters.put(FilterType.FILTERUSER, new FilterUser());
		filters.put(FilterType.FILTERWORD, new FilterWord());
		filters.put(FilterType.BLACKLIST, new BlackList());
		filters.put(FilterType.OBFSCREDIT, new ObfuscateCreditCard());
		filters.put(FilterType.OBFSUSER, new ObfuscateUser());
	}
	
	/**
	 * Returns the requested filter
	 * @param filterType requested filter
	 * @return the relevant filter object
	 */
	public static Filter getFilter(FilterType filterType) {
		if(filters.containsKey(filterType))
			return filters.get(filterType);
		
		return null;
	}
}
