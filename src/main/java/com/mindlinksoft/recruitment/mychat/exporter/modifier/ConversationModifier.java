package com.mindlinksoft.recruitment.mychat.exporter.modifier;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.exporter.modifier.filter.FilterKeyWord;
import com.mindlinksoft.recruitment.mychat.exporter.modifier.filter.FilterUser;
import com.mindlinksoft.recruitment.mychat.exporter.modifier.hide.HideCreditCardPhoneNumbers;
import com.mindlinksoft.recruitment.mychat.exporter.modifier.hide.HideKeyWord;
import com.mindlinksoft.recruitment.mychat.exporter.modifier.obfuscate.ObfuscateUsers;

/**
 * An implementation of the ConversationModifierService. Responsible for
 * choosing the correct modifier according to the given modifier type
 * (and modifier argument, if specified).
 */
public class ConversationModifier implements ConversationModifierService {

    private final Conversation conversation;
    private final Modifier modifier;
    private final String[] modifierArguments;

    /**
     * Returns an implementation of the ConversationModifierService, which will
     * modify a conversation according to the modifier type and arguments
     *
     * @param conversation      the conversation you wish to modify
     * @param modifier          the type of modification
     * @param modifierArguments which arguments e.g. users/words you wish to find/redact
     */
    public ConversationModifier(Conversation conversation, Modifier modifier, String[] modifierArguments) {
        this.conversation = conversation;
        this.modifier = modifier;
        this.modifierArguments = modifierArguments;
    }

    /**
     * Applies the relevant ModifierBase class to modify the conversation
     * according to this modifier type and arguments
     *
     * @return modified conversation
     */
    public Conversation modify() {
        ModifierBase modifier = chooseModification();
        return modifier.modify();
    }

    /**
     * Chooses the modifier class according to this class' modifier type
     *
     * @return instance of the relevant modifier class
     */
    public ModifierBase chooseModification() {
        switch (modifier) {
            case FILTER_USER:
                return new FilterUser(conversation, modifierArguments);
            case FILTER_KEYWORD:
                return new FilterKeyWord(conversation, modifierArguments);
            case HIDE_KEYWORD:
                return new HideKeyWord(conversation, modifierArguments);
            case HIDE_CREDIT_CARD_AND_PHONE_NUMBERS:
                return new HideCreditCardPhoneNumbers(conversation);
            case OBFUSCATE_USERS:
                return new ObfuscateUsers(conversation);
            default:
                throw new IllegalStateException("System error. The specified modifier has not been integrated.");
        }
    }

    public Conversation getConversation() {
        return conversation;
    }
}