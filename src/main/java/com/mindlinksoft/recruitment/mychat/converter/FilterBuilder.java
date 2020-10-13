package com.mindlinksoft.recruitment.mychat.converter;

import java.util.HashMap;

import com.mindlinksoft.recruitment.mychat.config.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.filters.*;
import com.mindlinksoft.recruitment.mychat.models.*;

import picocli.CommandLine.ParseResult;

public class FilterBuilder {
    
    private ParseResult parseResult;
    private ConversationExporterConfiguration config;
    private ActivityFilter af = new ActivityFilter();

    public HashMap<String, Filter> optionMap = new HashMap<String, Filter>();


    private void buildMap() {
        optionMap.put("filterByUser", new FilterByUser(config.userFilter));
        optionMap.put("filterByKeyword", new FilterByKeyword(config.wordFilter));
        optionMap.put("blacklist", new FilterByBlacklist(config.blacklistWords));
    }

    public void filterConversation(Conversation conversation) {
        for (String key : optionMap.keySet()) {
            if (parseResult.hasMatchedOption(key)) {
                optionMap.get(key).runFilter(conversation);
                System.out.println("Running filter: " + key);
            }
        }
        if (parseResult.hasMatchedOption("report")) {
            af.produceReport(conversation);
            System.out.println("Producing report");
        }
    }


    public FilterBuilder(ParseResult parseResult, ConversationExporterConfiguration config) {
        this.parseResult = parseResult;
        this.config = config;
        buildMap();
    }

}
