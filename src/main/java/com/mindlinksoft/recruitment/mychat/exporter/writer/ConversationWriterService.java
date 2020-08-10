package com.mindlinksoft.recruitment.mychat.exporter.writer;

import java.io.IOException;

/**
 * Represents the writing service which will output a Json file
 */
public interface ConversationWriterService {

    /**
     * Starts the writer service, which will create the output Json
     * file from the supplied conversation object in its constructor.
     */
    void write() throws IOException;
    
}