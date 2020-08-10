package com.mindlinksoft.recruitment.mychat.exporter.modifier;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;

public class ConversationModifier implements ConversationModifierService {

    private final Conversation conversation;
    private final Modifier modifier;
    private final String modifierArgument;

    public ConversationModifier(Conversation conversation, Modifier modifier, String modifierArgument) {
        this.conversation = conversation;
        this.modifier = modifier;
        this.modifierArgument = modifierArgument;
    }
    
    public Conversation modify() {
        /*
        switch(modifier) {
            case FILTER_USER:
                Filter filterUser = new FilterUser(modifierArgument);
                return filterUser.filter();
            case FILTER_KEYWORD:
                Filter filterWord = new FilterWord(modifierArgument);
                return filterWord.filter();
            case HIDE_KEYWORD:
                Hide hideWord = new HideWord(modifierArgument);
                return hideWord.hide();
            case HIDE_CREDIT_CARD_AND_PHONE_NUMBERS:
                Hide hideNumbers = new HideNumbers();
                return hideNumbers.hide();
            case OBFUSCATE_USERS:
                Obfuscate obfuscateUsers = new ObfuscateUsers();
                return obfuscateUsers.obfuscate();
            default:
                throw new IllegalStateException("The specified modifier does not exist.");
        }
        */
        return null;
    }

    public Conversation getConversation() {
        return conversation;
    }
}