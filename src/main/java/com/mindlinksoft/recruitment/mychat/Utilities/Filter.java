package com.mindlinksoft.recruitment.mychat.Utilities;

import com.mindlinksoft.recruitment.mychat.Objects.Conversation;
import com.mindlinksoft.recruitment.mychat.Objects.Message;

import java.util.ArrayList;
import java.util.List;

public class Filter {
    public static Conversation filterName(Conversation conversation, String value) {
        List<Message> messages = new ArrayList<Message>();
        Conversation filteredCon = new Conversation(conversation.name, messages);

        for (Message message : conversation.messages){
            if (message.senderId.equals(value)){
                filteredCon.messages.add(message);
            }
        }
        return filteredCon;
    }
}
