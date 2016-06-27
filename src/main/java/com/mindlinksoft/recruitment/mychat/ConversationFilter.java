package com.mindlinksoft.recruitment.mychat;

import java.util.logging.Level;

interface ConversationFilter {
	
	 /**
     * Applies filter specified as key-value pair.
     * */
    void apply(Conversation conversation);
}
