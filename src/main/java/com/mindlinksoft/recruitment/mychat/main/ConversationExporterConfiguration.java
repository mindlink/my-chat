package com.mindlinksoft.recruitment.mychat.main;

import com.mindlinksoft.recruitment.mychat.exporter.modifier.Modifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final List<Modifier> modifier;

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
        this.modifier = new ArrayList<>();
        this.modifierArguments = new HashMap<>();
    }

    public String getInputFilePath() {
        return inputFilePath;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public List<Modifier> getModifier() {
        return modifier;
    }

    public Map<Modifier, List<String>> getModifierArguments() {
        return modifierArguments;
    }
}
