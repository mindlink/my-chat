package com.mindlinksoft.recruitment.mychat.filter;

import java.util.Set;
import com.mindlinksoft.recruitment.mychat.Message;

/**
 * Filter interface to be implemented for custom behaviours
 * 
 * @author Mohamed Yusuf
 *
 */
public interface Filter {
	public Set<Message> filter(Set<Message> toFilter, String[] filters);
}
