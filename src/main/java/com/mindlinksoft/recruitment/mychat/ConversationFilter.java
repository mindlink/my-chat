package com.mindlinksoft.recruitment.mychat;

/**
 * Interface declaring the type of a conversation filter. A Conversation filter
 * is here intended as anything that alters a conversation. Responsible for
 * defining a filter type.*/
interface ConversationFilter {
	
	 /**
     * Filters the conversation parameter according to the implementation of 
     * this method provided by this concrete subtype of {@link ConversationFilter}
     * @param conversation the conversation to which this filter will apply and
     * modify.
     * */
    void apply(Conversation conversation);
}
