package com.mindlinksoft.recruitment.mychat.exporter.modifier;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.exporter.modifier.filter.FilterKeyWord;
import com.mindlinksoft.recruitment.mychat.exporter.modifier.filter.FilterUser;
import com.mindlinksoft.recruitment.mychat.exporter.modifier.hide.HideKeyWord;

/**
 * An implementation of the ConversationModifierService. Responsible for
 * choosing the correct modifier according to the given modifier type
 * (and modifier argument, if specified).
 */
public class ConversationModifier implements ConversationModifierService {

    private final Conversation conversation;
    private final Modifier modifier;
    private final String[] modifierArguments;

    public ConversationModifier(Conversation conversation, Modifier modifier, String[] modifierArguments) {
        this.conversation = conversation;
        this.modifier = modifier;
        this.modifierArguments = modifierArguments;
    }

    public Conversation modify() {
        ModifierBase modifier = chooseModification();
        return modifier.modify();
    }

    public ModifierBase chooseModification() {
        switch(modifier) {
            case FILTER_USER:
                return new FilterUser(conversation, modifierArguments);
            case FILTER_KEYWORD:
                return new FilterKeyWord(conversation, modifierArguments);
            case HIDE_KEYWORD:
                return new HideKeyWord(conversation, modifierArguments);
            default:
                // TODO: add other Modifier types
                throw new IllegalStateException("The specified modifier does not exist.");
        }
    }

    public Conversation getConversation() {
        return conversation;
    }
}