package com.mindlinksoft.recruitment.mychat.exporter.reader;

import java.io.IOException;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;

public interface ConversationReaderService {
    Conversation read() throws IOException;
}