package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.optionSettings.OptionSetting;
import java.util.ArrayList;

/**
 * Represents the configuration for the exporter.
 */
public final class ConversationExporterConfiguration {
    /**
     * Gets the input file path.
     */
    public String inputFilePath;

    /**
     * Gets the output file path.
     */
    public String outputFilePath;

    /**
     * Gets the options from commandline.
     */
    ArrayList<OptionSetting> options;

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param options The options from commandline.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, ArrayList<OptionSetting> options) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.options = options;
    }

    public String getInputFilePath() {
        return inputFilePath;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }
}
