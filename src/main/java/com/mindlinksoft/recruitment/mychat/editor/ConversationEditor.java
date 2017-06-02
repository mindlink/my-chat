package com.mindlinksoft.recruitment.mychat.editor;


import com.mindlinksoft.recruitment.mychat.conversation.ConversationInterface;
import com.mindlinksoft.recruitment.mychat.editor.filters.ConversationFilterInterface;
import com.mindlinksoft.recruitment.mychat.editor.filters.KeywordFilter;
import com.mindlinksoft.recruitment.mychat.editor.filters.UserNameFilter;
import com.mindlinksoft.recruitment.mychat.editor.formatters.BlacklistFormatter;
import com.mindlinksoft.recruitment.mychat.editor.formatters.MessageFormatterInterface;
import com.mindlinksoft.recruitment.mychat.editor.formatters.ObfuscateIdFormatter;
import com.mindlinksoft.recruitment.mychat.editor.formatters.SensitiveDataFormatter;
import com.mindlinksoft.recruitment.mychat.message.MessageInterface;
import com.mindlinksoft.recruitment.mychat.properties.MyChatApplicationConstants;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents the conversation editor
 * Responsible for loading and applying filters and formatters
 */
public class ConversationEditor {
    /**
     * List to keep loaded message formatters
     */
    private List<MessageFormatterInterface> messageFormatters = new ArrayList<MessageFormatterInterface>();
    /**
     * List to keep loaded conversationFilters
     */
    private List<ConversationFilterInterface> conversationFilters = new ArrayList<ConversationFilterInterface>();


    /**
     * Loads filters and formatters by scanning the supplied options from
     * the {@link CommandLine} object. If the option is present a formatter/filter
     * is created and added to the appropriate list.
     * @param cli, a {@link CommandLine} object
     */
    public void loadFiltersAndFormatters(CommandLine cli){
        Option[] options = cli.getOptions();
        for( Option option : options ){
            switch (option.getOpt()) {
                case MyChatApplicationConstants.CLI_USER_FILTER_SHORT_OPTION:
                    this.conversationFilters.add(new UserNameFilter(option.getValue()));
                    System.out.println("Loaded username filter...");
                    break;
                case MyChatApplicationConstants.CLI_KEYWORD_FILTER_SHORT_OPTION:
                    this.conversationFilters.add(new KeywordFilter(option.getValue()));
                    System.out.println("Loaded keyword filter...");
                    break;
                case MyChatApplicationConstants.CLI_BLACKLIST_SHORT_OPTION:
                    this.messageFormatters.add(new BlacklistFormatter(option.getValue()));
                    System.out.println("Loaded blacklist word formatter...");
                    break;
                case MyChatApplicationConstants.CLI_OBFUSCATE_SHORT_OPTION:
                    this.messageFormatters.add(new ObfuscateIdFormatter());
                    System.out.println("Loaded user id obfuscator...");
                    break;
                case MyChatApplicationConstants.CLI_HIDE_SD_SHORT_OPTION:
                    this.messageFormatters.add(new SensitiveDataFormatter());
                    System.out.println("Loaded sensitive data obfuscator...");
                    break;
            }
        }
        System.out.println();
    }

    /**
     * Applies filters
     * @param conversation object that implemets {@link ConversationInterface}
     * @return the filtered covnversation {@link ConversationInterface}
     */
    public ConversationInterface applyFilters(ConversationInterface conversation){
        //TODO optimize maybe?? combine with message formatting loop iterations
        for(ConversationFilterInterface filter : this.conversationFilters ){
            conversation = filter.filterConversation(conversation);
        }
        System.out.println(this.conversationFilters.size() + " Filters Applied");
        return conversation;
    }

    /**
     * Applies formatters
     * @param conversation object that implemets {@link ConversationInterface}
     * @return the formatted covnversation {@link ConversationInterface}
     */
    public ConversationInterface applyFormatters(ConversationInterface conversation){
        Collection<MessageInterface> messages = conversation.getMessages();
        List<MessageInterface> formattedMessages = new ArrayList<MessageInterface>();

        for( MessageInterface message : messages ){
            for ( MessageFormatterInterface formatter : this.messageFormatters ){
                message = formatter.formatMessage(message);
            }
        }
        System.out.println(this.messageFormatters.size() + " Formatters Applied");
        return conversation;
    }

    /**
     * Get message formatters list - used mainly for testing
     * @return a {@link List<MessageFormatterInterface>}
     */
    public List<MessageFormatterInterface> getMessageFormatters(){
        return this.messageFormatters;
    }

    /**
     * Get conversation filters list - used mainly for testing
     * @return a {@link List<ConversationFilterInterface>}
     */
    public List<ConversationFilterInterface> getConversationFilters(){
        return this.conversationFilters;
    }
}
