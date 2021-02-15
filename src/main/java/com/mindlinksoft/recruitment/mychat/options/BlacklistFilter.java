package com.mindlinksoft.recruitment.mychat.options;

import com.mindlinksoft.recruitment.mychat.ConversationExporter;
import com.mindlinksoft.recruitment.mychat.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.Message;

import java.util.Collection;
import java.util.regex.Pattern;

/**
 * Represents one of the export options - Black List Filter
 * To blacklist (hide) an array of {@code blackListWord}s in a supplied {@link Conversation}
 */
public class BlacklistFilter implements ConversationExportOptionInterface {
    String[] blacklistWordArray;

    /**
     * Initializes a new instance of the {@link BlacklistFilter} class.
     * @param blacklistWord A string array of words to blacklist as passed from {@link ConversationExporterConfiguration}
     */
    public BlacklistFilter(String[] blacklistWord) {
        this.blacklistWordArray = blacklistWord;
    }

    /**
     * {@code process} method implemented to process the given conversation for a particular export option
     * In this option's case, to blacklist (hide) an array of {@code blackListWord}s in a supplied {@link Conversation}
     * @param conversation  The {@link Conversation} to be processed
     * @return Conversation The processed {@link Conversation}
     */
    @Override
    public Conversation process(Conversation conversation) {
        Collection<Message> messages = conversation.getMessages();
        for (Message message : messages) {
            for (String blacklistWord : blacklistWordArray) {
                String messageContent = message.getContent();
                if (messageContent.toLowerCase().contains(blacklistWord.toLowerCase())) {
                    String redactedMessageContent = messageContent.replaceAll("(?i)" + Pattern.quote(blacklistWord), "*REDACTED*");  // case insensitive replace all
                    message.setContent(redactedMessageContent);
                    ConversationExporter.logger.info("Processed: Blacklist filter - Redacted '" + messageContent + "' to '" + redactedMessageContent + "' as contains blacklist word '" + blacklistWord + "'");
                }
            }
        }
        return conversation;
    }
}
