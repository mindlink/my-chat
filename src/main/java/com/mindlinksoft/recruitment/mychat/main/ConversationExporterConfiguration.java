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
    private final String modifierArgument;

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.modifier = null;
        this.modifierArgument = null;
    }

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param modifier The type of filtering, hiding or obfuscating.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, Modifier modifier) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.modifier = modifier;
        this.modifierArgument = null;
    }

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param modifier The type of filtering, hiding or obfuscating.
     * @param modifierArgument The string that will be modified.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, Modifier modifier, String modifierArugment) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.modifier = modifier;
        this.modifierArgument = modifierArugment;
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

    public String getModifierArgument() {
        return modifierArgument;
    }
}
