package com.mindlinksoft.recruitment.mychat.exporter;

/**
 * Represents a conversation exporter that can read a conversation, modify it
 * and then write it out in JSON.
 */
public interface ConversationExporterService {
    /**
     * Services that implement this interface must provide
     * an export method, which will begin the reader, modifier
     * and writer services, in that order.
     * <p>
     * If successful, the new file will be output at the given location,
     * before exiting the program.
     */
    void export();
}