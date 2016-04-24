package com.mindlinksoft.recruitment.mychat;

import org.kohsuke.args4j.Argument;

/**
 * Represents the configuration for the exporter.
 */
public final class ConversationExporterConfiguration {
    /**
     * Gets the input file path.
     */
    @Argument(index = Resources.INPUT_INDEX,
            required = true,
            metaVar = Resources.INPUT_METAVAR,
            usage = Resources.INPUT_USAGE)
    public String inputFilePath;

    /**
     * Gets the output file path.
     */
    @Argument(index = Resources.OUTPUT_INDEX,
            required = true,
            metaVar = Resources.OUTPUT_METAVAR,
            usage = Resources.OUTPUT_USAGE)
    public String outputFilePath;
}
