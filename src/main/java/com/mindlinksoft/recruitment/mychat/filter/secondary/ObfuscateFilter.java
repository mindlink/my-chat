package com.mindlinksoft.recruitment.mychat.filter.secondary;

import com.mindlinksoft.recruitment.mychat.conversation.Conversation;
import com.mindlinksoft.recruitment.mychat.filter.Filter;
import com.mindlinksoft.recruitment.mychat.message.Message;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Obfuscates the message sender ids.
 *
 * Secondary filters should be applied after any primary filters.
 *
 * @author Gabor
 */
public class ObfuscateFilter implements Filter {

    final private String OBFUSCATED_SENDER_PREFIX = "sender_";

    @Override
    public void apply(Conversation conversation) {
        // get distinct senderIds
        Set<String> uniqueSenderIds = new HashSet<>();
        for (Message message : conversation.getMessages()) {
            if (!uniqueSenderIds.contains(message.getSenderId())) {
                uniqueSenderIds.add(message.getSenderId());
            }
        }
        // replace senderIds with obfuscate sender ids
        List<String> senderIds = new ArrayList<>(uniqueSenderIds);
        for (String senderId : senderIds) {
            int index = senderIds.indexOf(senderId);
            for (Message message : conversation.getMessages()) {
                if (message.getSenderId().equals(senderId)) {
                    message.setSenderId(OBFUSCATED_SENDER_PREFIX + index);
                }
            }
        }
    }

}
