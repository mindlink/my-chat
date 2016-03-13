package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.conversation.ConversationExporter;
import com.mindlinksoft.recruitment.mychat.filter.secondary.BlackListFilter;
import com.mindlinksoft.recruitment.mychat.filter.Filter;
import com.mindlinksoft.recruitment.mychat.filter.primary.KeywordFilter;
import com.mindlinksoft.recruitment.mychat.filter.primary.SenderFilter;
import com.mindlinksoft.recruitment.mychat.filter.secondary.ObfuscateFilter;
import com.mindlinksoft.recruitment.mychat.filter.secondary.SensitiveInfoFilter;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.wymiwyg.commons.util.arguments.ArgumentHandler;

/**
 * Main class of the application.
 *
 * @author Gabor
 */
public class Main {

    /**
     * The application entry point. Handles the input arguments and sets up the
     * filters.
     *
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception {
        Arguments arguments = ArgumentHandler.readArguments(Arguments.class, args);

        if (arguments != null) {
            ConversationExporter exporter = new ConversationExporter();

            // input file path cannot be empty
            if (StringUtils.isEmpty(arguments.getInputFilePath())) {
                throw new IllegalArgumentException("Input file path cannot be empty!");
            }

            // output file path cannot be empty
            if (StringUtils.isEmpty(arguments.getOutputFilePath())) {
                throw new IllegalArgumentException("Output file path cannot be empty!");
            }

            List<Filter> filters = new ArrayList<>();

            ///////////////////////
            // PRIMARY FILTERS
            ///////////////////////
            // add sender filter
            if (StringUtils.isNotEmpty(arguments.getSenderFilter())) {
                filters.add(new SenderFilter(arguments.getSenderFilter()));
            }

            // add keyword filter
            if (StringUtils.isNotEmpty(arguments.getKeywordFilter())) {
                filters.add(new KeywordFilter(arguments.getKeywordFilter()));
            }

            ///////////////////////
            // SECONDARY FILTERS
            ///////////////////////
            // add blacklist filter
            if (StringUtils.isNotEmpty(arguments.getBlacklistFilter())) {
                filters.add(new BlackListFilter(arguments.getBlacklistFilter()));
            }

            // add obfuscate filter
            if (arguments.isObfuscateSender()) {
                filters.add(new ObfuscateFilter());
            }

            // add sensitive info filter
            if (arguments.isSensitiveInfoHidden()) {
                filters.add(new SensitiveInfoFilter());
            }

            exporter.exportConversation(arguments.getInputFilePath(), arguments.getOutputFilePath(), filters);
        }
    }
}
