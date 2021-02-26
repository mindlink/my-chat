package com.mindlinksoft.recruitment.mychat;

public class Main {

    /**
     * The application entry point.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        ConversationExporter exporter = new ConversationExporter();
        exporter.run(args);
    }
}
