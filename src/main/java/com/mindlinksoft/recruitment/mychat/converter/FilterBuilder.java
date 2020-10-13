package com.mindlinksoft.recruitment.mychat.converter;

import java.util.HashMap;

import com.mindlinksoft.recruitment.mychat.config.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.filters.*;
import com.mindlinksoft.recruitment.mychat.models.*;

import picocli.CommandLine.ParseResult;

/**
 * Class which determines which filters to use based on a ParsedResult and Configuration object
 */
public class FilterBuilder {
    
    private ParseResult parseResult;
    private ConversationExporterConfiguration config;
    private ActivityFilter af = new ActivityFilter();

    public HashMap<String, Filter> optionMap = new HashMap<String, Filter>();

    /**
     * This is the main function which maps options to their associated filters.
     * New filters added should be included in this build function
     */
    private void buildMap() {
        optionMap.put("filterByUser", new FilterByUser(config.userFilter));
        optionMap.put("filterByKeyword", new FilterByKeyword(config.wordFilter));
        optionMap.put("blacklist", new FilterByBlacklist(config.blacklistWords));
    }

    /**
     * Two part function using the HashMap created on initialization.
     * It will perform all necessary options with their associated filters based on the configuration and mappings and should not need changing.
     * The second part checks the report flag as this is a separate concern from the filtering aspect
     * @param conversation The conversation object which will be converted in place
     * @return This returns a string based on the operations the builder performed
     */
    public String filterConversation(Conversation conversation) {
        String result = "";
        for (String key : optionMap.keySet()) {
            if (parseResult.hasMatchedOption(key)) {
                optionMap.get(key).runFilter(conversation);
                result += "Running filter: " + key + '\n';
            }
        }
        if (parseResult.hasMatchedOption("report")) {
            af.produceReport(conversation);
            result += "Producing report";
        }
        return result;
    }


    public FilterBuilder(ParseResult parseResult, ConversationExporterConfiguration config) {
        this.parseResult = parseResult;
        this.config = config;
        buildMap();
    }

}
