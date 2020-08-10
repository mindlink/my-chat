package com.mindlinksoft.recruitment.mychat.exporter.modifier.hide;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;

public class HideKeyWord implements Hide {

    /**
     * The key word to hide
     */
    private final String keyWord;
    
    /**
     * The conversation to be hidden
     */
    private final Conversation conversation;

    /**
     * Returns a hider that hides the given key word 
     * @param conversation contains the messages you wish to filter
     * @param keyWord the key word to filter
     */
    public HideKeyWord(Conversation conversation, String keyWord) {
        this.conversation = conversation;
        this.keyWord = keyWord;
    }

	@Override
    public Conversation hide() {
        // TODO Auto-generated method stub
        return null;
    }
    
}