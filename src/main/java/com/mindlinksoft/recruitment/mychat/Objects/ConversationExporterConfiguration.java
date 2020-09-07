package com.mindlinksoft.recruitment.mychat.Objects;

public final class ConversationExporterConfiguration {

    public String inputFilePath;
    public String outputFilePath;
    public String argument;
    public String value;
    public String flag1;
    public String flag2;
    public String flag3;

    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, String argument, String value, String flag1, String flag2, String flag3) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.argument = argument;
        this.value = value;
        this.flag1 = flag1;
        this.flag2 = flag2;
        this.flag3 = flag3;
    }
}
