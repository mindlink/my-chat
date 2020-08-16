package com.mindlinksoft.recruitment.mychat.exporter;

import com.mindlinksoft.recruitment.mychat.exporter.datastructure.Conversation;
import com.mindlinksoft.recruitment.mychat.exporter.modifier.ConversationModifier;
import com.mindlinksoft.recruitment.mychat.exporter.modifier.ConversationModifierService;
import com.mindlinksoft.recruitment.mychat.exporter.modifier.Modifier;
import com.mindlinksoft.recruitment.mychat.exporter.reader.ConversationReader;
import com.mindlinksoft.recruitment.mychat.exporter.reader.ConversationReaderService;
import com.mindlinksoft.recruitment.mychat.exporter.writer.ConversationWriter;
import com.mindlinksoft.recruitment.mychat.exporter.writer.ConversationWriterService;
import com.mindlinksoft.recruitment.mychat.main.ConversationExporterConfiguration;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter implements ConversationExporterService {

    private final ConversationExporterConfiguration configuration;
    private static final Logger LOGGER = Logger.getLogger(ConversationExporter.class.getName());

    /**
     * Returns an instance of the Conversation Exporter
     *
     * @param configuration contains the input and output file paths, and any modifiers
     */
    public ConversationExporter(ConversationExporterConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * Starts the exporter, which in turn starts the relevant
     * reader, modifier and writer services. If successful, the file
     * will be output at the given location and the program will then terminate
     */
    public void export() {
        LOGGER.log(Level.INFO, "Starting reader at " + configuration.getInputFilePath() + "...");
        Conversation conversation = buildReader(configuration.getInputFilePath());
        LOGGER.log(Level.INFO, "Reader completed.");

        LOGGER.log(Level.INFO, "Starting modifier...");
        if (configuration.getModifiers() != null) {
            conversation = buildModifier(
                    conversation,
                    configuration.getModifiers(),
                    configuration.getModifierArguments());

            LOGGER.log(Level.INFO, "Modifier completed.");
        } else {
            LOGGER.log(Level.INFO, "Modifier not completed; No modification was specified.");
        }

        LOGGER.log(Level.INFO, "Starting writer at " + configuration.getOutputFilePath() + "...");
        buildWriter(configuration.getOutputFilePath(), conversation);
        LOGGER.log(Level.INFO, "Writer completed.");
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
     * Starts the modifier which will read the conversation
     * and modify it by creating a new Conversation
     *
     * @param modifiers         the type of modification you wish to apply
     * @param modifierArguments the specific key words or senders you wish to modify. Can be null
     * @return The {@link Conversation} representing the modified input file
     */
    public Conversation buildModifier(Conversation conversation, Set<Modifier> modifiers, Map<Modifier, List<String>> modifierArguments) {
        ConversationModifierService modifierService = new ConversationModifier(conversation, modifiers, modifierArguments);
        return modifierService.modify();
    }

    /**
     * Method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     *
     * @param outputFilePath The path to the output file.
     */
    public void buildWriter(String outputFilePath, Conversation conversation) {
        ConversationWriterService writer = new ConversationWriter(outputFilePath, conversation);
        writer.write();
    }

    public ConversationExporterConfiguration getConfiguration() {
        return configuration;
    }
}
