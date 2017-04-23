package com.mindlinksoft.recruitment.mychat.filters;

import java.util.ArrayList;

import org.hamcrest.Factory;

import com.mindlinksoft.recruitment.mychat.conversation.ConversationExporterConfiguration;

/**
 * A {@link Factory} that generates Filters based on the Conversation Exporter Configuration
 * @author Omar
 *
 */
public class FiltersFactory {
	public static ArrayList<IFilter> getFilters(ConversationExporterConfiguration configuration)
	{
    	ArrayList<IFilter> filters = new ArrayList<IFilter>();
    	if(configuration.getUser()!=null)
    	{
    		filters.add(new SenderIDFilter(configuration.getUser()));
    	}
    	if(configuration.getKeyword()!=null)
    	{
    		filters.add(new KeywordFilter(configuration.getKeyword()));
    	}

		return filters;
	}
}
