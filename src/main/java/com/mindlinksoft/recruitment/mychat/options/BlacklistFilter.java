package com.mindlinksoft.recruitment.mychat.options;

import com.mindlinksoft.recruitment.mychat.ConversationExporter;
import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;

import java.util.Collection;
import java.util.regex.Pattern;

public class BlacklistFilter implements ConversationExportOptionInterface {
    String[] blacklistWordArray;

    public BlacklistFilter(String[] blacklistWord) {
        this.blacklistWordArray = blacklistWord;
    }

    @Override
    public Conversation process(Conversation conversation) {
        Collection<Message> messages = conversation.getMessages();
        for (Message message : messages) {
            for (String blacklistWord : blacklistWordArray) {
                String messageContent = message.getContent();
                if (messageContent.toLowerCase().contains(blacklistWord.toLowerCase())) {
                    String redactedMessageContent = messageContent.replaceAll("(?i)" + Pattern.quote(blacklistWord), "*REDACTED*");  // case insensitive replace all
                    message.setContent(redactedMessageContent);
                    ConversationExporter.logger.info("Processed: Blacklist filter - Redacted \'"+ messageContent + "\' to \'" + redactedMessageContent + "\' as contains blacklist word \'" + blacklistWord + "\'");
                }
            }
        }
        return conversation;
    }
}
