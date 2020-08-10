package com.mindlinksoft.recruitment.mychat.exporter;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.exporter.reader.ConversationReader;
import com.mindlinksoft.recruitment.mychat.exporter.reader.ConversationReaderService;
import com.mindlinksoft.recruitment.mychat.exporter.writer.ConversationWriter;
import com.mindlinksoft.recruitment.mychat.exporter.writer.ConversationWriterService;
import com.mindlinksoft.recruitment.mychat.main.ConversationExporterConfiguration;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter implements ConversationExporterService {

    private final ConversationExporterConfiguration configuration;

    public ConversationExporter(ConversationExporterConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * Starts the exporter, which in turn starts the relevant
     * reader, modifier and writer services. If successful, the file
     * will be output at the given location and the program will then terminate
     */
    public void export() {
        Conversation conversation = buildReader(configuration.inputFilePath);
        // TODO: write modifier, if applicable
        buildWriter(configuration.outputFilePath, conversation);
    }

    /**
     * Starts the reader which will read the input file and construct
     * a Conversation model based on that file.
     * 
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing the input file.
     */
    public Conversation buildReader(String inputFilePath) {
        ConversationReaderService reader = new ConversationReader(inputFilePath);
        return reader.read();
    }

    /**
     * Method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * 
     * @param outputFilePath The path to the output file.
     * @return The {@link Conversation} representing the input file.
     */
    public void buildWriter(String outputFilePath, Conversation conversation) {
        ConversationWriterService writer = new ConversationWriter(outputFilePath, conversation);
        writer.write();
    }

    public ConversationExporterConfiguration getConfiguration() {
        return configuration;
    }
}
