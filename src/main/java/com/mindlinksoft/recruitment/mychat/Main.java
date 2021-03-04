package com.mindlinksoft.recruitment.mychat;

import java.util.Arrays;

public class Main {

    /**s
     * The application entry point.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        ConversationExporter exporter = new ConversationExporter();
        exporter.run(args);
    }
}
