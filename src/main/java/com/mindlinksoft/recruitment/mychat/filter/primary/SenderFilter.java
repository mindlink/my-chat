package com.mindlinksoft.recruitment.mychat.filter.primary;

import com.mindlinksoft.recruitment.mychat.conversation.Conversation;
import com.mindlinksoft.recruitment.mychat.filter.Filter;
import com.mindlinksoft.recruitment.mychat.message.Message;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * Filters the messages based on the sender id.
 *
 * Primary filters should be applied before any secondary filters.
 *
 * @author Gabor
 */
public class SenderFilter implements Filter {

    final private String senderId;

    public SenderFilter(String sender) {
        this.senderId = sender;
    }

    @Override
    public void apply(Conversation conversation) {
        List<Message> filteredMessages = new ArrayList<>();
        for (Message message : conversation.getMessages()) {
            if (StringUtils.equalsIgnoreCase(senderId, message.getSenderId())) {
                filteredMessages.add(message);
            }
        }
        conversation.setMessages(filteredMessages);
    }

}
