package com.mindlinksoft.recruitment.mychat.Utilities.AdditionalFeatures;

import com.mindlinksoft.recruitment.mychat.Constructs.ConversationDefault;

import java.util.HashMap;


public interface AdditionalFeatures {

    Object processAndReturn(ConversationDefault conversationDefault);

    HashMap<String, Integer> generateData(ConversationDefault conversationDefault);

}