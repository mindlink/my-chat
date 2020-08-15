package com.mindlinksoft.recruitment.mychat.main;

import com.mindlinksoft.recruitment.mychat.exporter.modifier.Modifier;

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
    private final Modifier modifier;

    /**
     * The string that will be modified
     */
    private final String[] modifierArguments;

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     *
     * @param inputFilePath  The input file path.
     * @param outputFilePath The output file path.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.modifier = null;
        this.modifierArguments = null;
    }

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     *
     * @param inputFilePath  The input file path.
     * @param outputFilePath The output file path.
     * @param modifier       The type of filtering, hiding or obfuscating.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, Modifier modifier) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.modifier = modifier;
        this.modifierArguments = null;
    }

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     *
     * @param inputFilePath     The input file path.
     * @param outputFilePath    The output file path.
     * @param modifier          The type of filtering, hiding or obfuscating.
     * @param modifierArguments The string that will be modified.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, Modifier modifier, String[] modifierArguments) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.modifier = modifier;
        this.modifierArguments = modifierArguments;
    }

    public String getInputFilePath() {
        return inputFilePath;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public Modifier getModifier() {
        return modifier;
    }

    public String[] getModifierArguments() {
        return modifierArguments;
    }
}
