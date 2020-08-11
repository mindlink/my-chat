package com.mindlinksoft.recruitment.mychat.exporter.modifier.hide;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.exporter.modifier.ModifierBase;

public class HideCreditCardPhoneNumbers extends ModifierBase implements Hide {

    /**
     * Returns a modifier that hides phone numbers and credit card numbers in messages
     * @param conversation contains the messages you wish to hide numbers from
     */
    public HideCreditCardPhoneNumbers(Conversation conversation) {
        super(conversation);
    }

    @Override
    protected Conversation modify() {
        return hide();
    }

    @Override
    public Conversation hide() {
        // TODO Auto-generated method stub
        return null;
    }
    
}