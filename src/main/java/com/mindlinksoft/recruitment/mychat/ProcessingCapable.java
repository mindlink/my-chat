package com.mindlinksoft.recruitment.mychat;

/**
 * Interface shared by the feature processors (the modules that implement the features)
 */
public interface ProcessingCapable {

    /**
     * Function where the processing (for example filtering) happens
     */
    void process();

    /**
     * Function that fetches the processed conversation
     * The processed conversation is stored in a private member variable of the processors
     */
    Conversation getProcessedConversation();

}
