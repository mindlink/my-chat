package com.mindlinksoft.recruitment.mychat;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Represents a helper which takes care of mutating a conversation
 * according to command line arguments.
 */
class ConversationEditor {

    public Collection<ConversationMutator> mutators;

    /**
     * Constructor which populates the list of mutators to be applied when editing.
     * @param config The command line arguments specifying desired editing behaviour.
     */
    public ConversationEditor(ConversationExporterConfiguration config) {
        List<ConversationMutator> mutators = new ArrayList<ConversationMutator>();

        /* Censoring should happen first, otherwise there is information leak
         * e.g. I would be able to filter by "pie" and then redact "pie", and I
         * would know what the redacted words were
         */
        if (config.blacklist != null) {
            mutators.add(new BlacklistMutator(config.blacklist));
        }
        if (config.filterUser != null) {
            mutators.add(new UserFilterMutator(config.filterUser));
        }
        if (config.keyword != null) {
            mutators.add(new KeywordFilterMutator(config.keyword));
        }
        if (config.report) {
            mutators.add(new ReportMutator());
        }

        this.mutators = mutators;
    }

    /**
     * A helper which edits a conversation by applying each of this object's mutators.
     * @param c The conversation to be edited
     */
    public void editConversation(Conversation c) {
        for (ConversationMutator m : this.mutators) {
            m.mutateConversation(c);
        }
    }
}
