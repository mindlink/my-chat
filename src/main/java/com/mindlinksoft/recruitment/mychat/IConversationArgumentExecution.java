package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.Model.Conversation;

import picocli.CommandLine.ParseResult;

public interface IConversationArgumentExecution {

    public Conversation executue(Conversation convo, ParseResult pr) throws Exception;
    
}
