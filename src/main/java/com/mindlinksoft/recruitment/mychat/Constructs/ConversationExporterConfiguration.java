package com.mindlinksoft.recruitment.mychat.Constructs;

public final class ConversationExporterConfiguration {

    public String inputFilePath;
    public String outputFilePath;
    public String argument_1;
    public String argument_2;
    public String argument_3;
    public String argument_4;
    public String argument_5;

    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, String argument_1, String argument_2, String argument_3, String argument_4, String argument_5) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.argument_1 = argument_1;
        this.argument_2 = argument_2;
        this.argument_3 = argument_3;
        this.argument_4 = argument_4;
        this.argument_5 = argument_5;
    }
}
