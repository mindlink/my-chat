package com.mindlinksoft.recruitment.mychat.filter;

import com.mindlinksoft.recruitment.mychat.conversation.Conversation;

/**
 * Interface for filters.
 *
 * @author Gabor
 */
public interface Filter {

    public abstract void apply(Conversation conversation);
}
