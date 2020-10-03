package com.mindlinksoft.recruitment.mychat.converter;

import com.mindlinksoft.recruitment.mychat.models.*;
import com.mindlinksoft.recruitment.mychat.filters.*;
import com.mindlinksoft.recruitment.mychat.config.*;


public class ConversationConverter {

    public ConversationExporterConfiguration config;

    private String output = "";

    private ConversationFilter cf = new ConversationFilter();

    private ActivityFilter af = new ActivityFilter();


    private void addOnOutput(String str) {
        output += "\t" + str + "\n";
    }

    private String readOutput() {
        if (!output.equals("")) {
            output = "Options:\n" + output;
        }
        return output;
    }

    public String convertAll(Conversation convo) {
        if (config.userFilter != null && !config.userFilter.equals(" ")) {
            cf.filterByUser(convo, config.userFilter);
            addOnOutput("Filtered by user");
        }

        if (config.wordFilter != null && !config.wordFilter.equals(" ")) {
            cf.filterByKeyword(convo, config.wordFilter);
            addOnOutput("Filtered messages by keyword successfully");
        }

        if (config.blacklistWords != null) {
            cf.removeBlacklist(convo, config.blacklistWords);
            addOnOutput("Removed all occurrences of blacklisted words");
        }

        if (config.report) {
            af.produceReport(convo);
            addOnOutput("Producing report");
        }

        return readOutput();
    }


    public ConversationConverter(ConversationExporterConfiguration config) {
        this.config = config;
    }

}
