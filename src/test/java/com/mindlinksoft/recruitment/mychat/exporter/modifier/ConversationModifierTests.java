package com.mindlinksoft.recruitment.mychat.exporter.modifier;

import static org.junit.Assert.assertTrue;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.exporter.modifier.filter.FilterKeyWord;
import com.mindlinksoft.recruitment.mychat.exporter.modifier.filter.FilterUser;
import com.mindlinksoft.recruitment.mychat.exporter.modifier.hide.HideKeyWord;

import org.junit.Test;

public class ConversationModifierTests {
    
    ConversationModifier conversationModifier;

    Conversation conversation;

    @Test
    public void setUp() {
        conversation = new Conversation(); // empty conversation
    }

    @Test
    public void chooseModifier() {
        // filter key word should return relevant class
        conversationModifier = new ConversationModifier(conversation, Modifier.FILTER_KEYWORD, new String[]{"pie"});
        ModifierBase result = conversationModifier.chooseModification();
        assertTrue(result instanceof FilterKeyWord);

        conversationModifier = new ConversationModifier(conversation, Modifier.FILTER_USER, new String[]{"bob"});
        result = conversationModifier.chooseModification();
        assertTrue(result instanceof FilterUser);

        conversationModifier = new ConversationModifier(conversation, Modifier.HIDE_KEYWORD, new String[]{"pie"});
        result = conversationModifier.chooseModification();
        assertTrue(result instanceof HideKeyWord);
    }
}