package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.Model.Conversation;

import picocli.CommandLine.ParseResult;
import picocli.CommandLine.Model.OptionSpec;

public class ConversationArgumentExecution implements IConversationArgumentExecution {

    /**
     * Called to process the handed conversation with respects to the ParseResult
     * parameter.
     * @param conversation The conversation for the command options to work on/with.
     * @param pr The parseResult containing the options and their values.
     * @throws Exception
     */
    @Override
    public Conversation executue(Conversation conversation, ParseResult pr) throws Exception {
        try {
            Conversation convo = conversation;
            
            for(OptionSpec option : pr.matchedOptions()) {
                convo = processOption(convo, option);
            }

            return convo;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error in processing matched options.");
        }
    }
    

    /** 
     * This handles each matched option individually, peforming their respective tasks.
     * @param conversation The convosation that the command option is called on.
     * @param option The option that has been called.
     * @return The {@link Conversation} that is freshly constructed from the filter/modifications to the original. 
     */
    protected Conversation processOption(Conversation conversation, OptionSpec option) throws Exception {
        
        Conversation convo = conversation;
        
        switch (option.longestName()){
            case "--filterByUser": return convo.filterConvoByUser(option.getValue());
            case "--filterByKeyword": return convo.filterConvoByKeyword(option.getValue());
            case "--blacklist": return convo.censorConvo(option.getValue());
            case "--inputFilePath": return convo;
            case "--outputFilePath": return convo;
            default: 
                throw new Exception("Error: " + option.longestName() + " has not been implemented in either processOption method or its overrides");
        }
    }
}
