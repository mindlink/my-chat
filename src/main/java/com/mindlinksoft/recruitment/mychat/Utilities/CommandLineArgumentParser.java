package com.mindlinksoft.recruitment.mychat.Utilities;

import com.mindlinksoft.recruitment.mychat.Objects.ConversationExporterConfiguration;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
    /**
     * Parses the given {@code arguments} into the exporter configuration.
     *
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line arguments.
     */
    public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) {
        return new ConversationExporterConfiguration(arguments[0], arguments[1], arguments[2], arguments[3], arguments[4], arguments[5], arguments[6]);
    }
    //
    /**
     *      new parser, any flags before last two arguments...
     *      only 4 actual options allowed
     *      followed by 3 flags
     *
     *      1. default, exports into json
     *      2. -name filter
     *      3. -keyword filter
     *      4. -hide filter
     *
     *     flags
     *     -details
     *     -obf
     *     -report
     *
     *     example:
     *
     *     D:\Desktop\my-chat\chat.txt D:\Desktop\my-chat\json -hide pie,hello,there -obf
     */
}
