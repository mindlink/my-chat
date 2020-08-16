package com.mindlinksoft.recruitment.mychat.main;

import com.mindlinksoft.recruitment.mychat.exporter.modifier.Modifier;

import java.util.*;

/**
 * Represents the configuration for the exporter.
 */
public final class ConversationExporterConfiguration {
    /**
     * Gets the input file path.
     */
    private final String inputFilePath;

    /**
     * Gets the output file path.
     */
    private final String outputFilePath;

    /**
     * The type of filtering, hiding or obfuscating.
     */
    private final Set<Modifier> modifiers;

    /**
     * The string that will be modified
     */
    private final Map<Modifier, List<String>> modifierArguments;

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     *
     * @param inputFilePath     The input file path.
     * @param outputFilePath    The output file path.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.modifiers = new HashSet<>();
        this.modifierArguments = new HashMap<>();
    }

    public String getInputFilePath() {
        return inputFilePath;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public Set<Modifier> getModifiers() {
        return modifiers;
    }

    public Map<Modifier, List<String>> getModifierArguments() {
        return modifierArguments;
    }
}
