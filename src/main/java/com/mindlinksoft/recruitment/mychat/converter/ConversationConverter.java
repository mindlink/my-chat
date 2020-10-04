package com.mindlinksoft.recruitment.mychat.converter;

import com.mindlinksoft.recruitment.mychat.models.*;
import com.mindlinksoft.recruitment.mychat.filters.*;
import com.mindlinksoft.recruitment.mychat.config.*;

/**
 * Class which controls all of the conversions within conversations
 */
public class ConversationConverter {

    public ConversationExporterConfiguration config;

    private String output = "";

    /**
     * Filters to alter the conversation object
     */
    private ConversationFilter cf = new ConversationFilter();
    private ActivityFilter af = new ActivityFilter();


    /**
     * Helper methods which alter the return string based on the configuration
     * @param str String to add onto our return
     */
    private void addOnOutput(String str) {
        output += "\t" + str + "\n";
    }

    /**
     * Helper return method
     * @return returns the output and prepends options if they were any alterations
     */
    private String readOutput() {
        if (!output.equals("")) {
            output = "Options:\n" + output;
        }
        return output;
    }


    /**
     * Method which will read the configuration and determine what changes need to be made
     * @param conversation Conversation object which will be altered in place
     * @return returns string that details changes
     */
    public String convertAll(Conversation conversation) {
        if (config.userFilter != null && !config.userFilter.equals(" ")) {
            cf.filterByUser(conversation, config.userFilter);
            addOnOutput("Filtered by user");
        }

        if (config.wordFilter != null && !config.wordFilter.equals(" ")) {
            cf.filterByKeyword(conversation, config.wordFilter);
            addOnOutput("Filtered messages by keyword successfully");
        }

        if (config.blacklistWords != null) {
            cf.removeBlacklist(conversation, config.blacklistWords);
            addOnOutput("Removed all occurrences of blacklisted words");
        }

        if (config.report) {
            af.produceReport(conversation);
            addOnOutput("Producing report");
        }

        return readOutput();
    }

    // Constructor
    public ConversationConverter(ConversationExporterConfiguration config) {
        this.config = config;
    }

}
