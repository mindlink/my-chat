package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.filters.BlacklistFilter;
import com.mindlinksoft.recruitment.mychat.filters.KeywordFilter;
import com.mindlinksoft.recruitment.mychat.filters.UserFilter;

public class ArgumentAdapter {

    private Conversation conversation;
    private String argument;
    private final String FILTER_BY_USER = "--filterByUser=";
    private final String FILTER_BY_KEYWORD = "--filterByKeyword=";
    private final String BLACKLIST_ARG = "--blacklist=";
    private final String REPORT = "--report";


    public ArgumentAdapter(Conversation conversation, String argument) {
        this.conversation = conversation;
        this.argument = argument;
    }

    //Filters argument and directs flow to the correct filter class
    public Conversation directArgument(){

        //Pad argument for substring check
        String tempArgument = argument += "                           ";

        if (tempArgument.substring(0,15).equals(FILTER_BY_USER)) {
            UserFilter userFilter = new UserFilter(conversation, argument);
            conversation = userFilter.filterConversation();
        }
        else if (tempArgument.substring(0,18).equals(FILTER_BY_KEYWORD)){
            KeywordFilter keywordFilter = new KeywordFilter(conversation, argument);
            conversation = keywordFilter.filterConversation();
        }
        else if (tempArgument.substring(0,12).equals(BLACKLIST_ARG)){
            BlacklistFilter blacklistFilter = new BlacklistFilter(conversation, argument);
            conversation = blacklistFilter.filterConversation();
        }
        else if (tempArgument.substring(0,7).equals(REPORT)){

        }
        else{
            System.out.println("Argument function not recognised.");
        }

        return conversation;

    }
}
