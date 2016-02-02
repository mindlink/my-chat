package mychat;

public final class ConversationExporterConfiguration {

    public String getInputFilePath() {
        return inputFilePath;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public CommandOptions getUserCommandOptions() {
        return userCommandOptions;
    }

    public void setInputFilePath(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    public void setOutputFilePath(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    public void setUserCommandOptions(CommandOptions userCommandOptions) {
        this.userCommandOptions = userCommandOptions;
    }

    private String inputFilePath;

    private String outputFilePath;

    private CommandOptions userCommandOptions;


    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, CommandOptions commandOptions) {
    this.inputFilePath = inputFilePath;
    this.outputFilePath = outputFilePath;
    this.userCommandOptions = commandOptions;

    }

    
}
