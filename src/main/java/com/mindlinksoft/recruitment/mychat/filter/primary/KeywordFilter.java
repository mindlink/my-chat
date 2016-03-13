package com.mindlinksoft.recruitment.mychat.filter.primary;

import com.mindlinksoft.recruitment.mychat.conversation.Conversation;
import com.mindlinksoft.recruitment.mychat.filter.Filter;
import com.mindlinksoft.recruitment.mychat.message.Message;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * Filters the messages based on a keyword. It uses a simple contain on the
 * content of the message. (Would be more elegant to tokenize the text first so
 * only whole matches are found.)
 *
 * Primary filters should be applied before any secondary filters.
 *
 * @author Gabor
 */
public class KeywordFilter implements Filter {

    final private String keyword;

    public KeywordFilter(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void apply(Conversation conversation) {
        List<Message> filteredMessages = new ArrayList<>();
        for (Message message : conversation.getMessages()) {
            if (StringUtils.containsIgnoreCase(message.getContent(), keyword)) {
                filteredMessages.add(message);
            }
        }
        conversation.setMessages(filteredMessages);
    }

}
