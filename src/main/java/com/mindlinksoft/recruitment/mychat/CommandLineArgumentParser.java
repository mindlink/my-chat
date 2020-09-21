package com.mindlinksoft.recruitment.mychat;
import java.util.List;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;


public final class CommandLineArgumentParser {

    public BufferedReader reader;
    private Command command;
    private CLFormatter helper;
    private String cmd;
    private String[] argument;


    public void readInput(BufferedReader reader) throws IOException {
        String raw = reader.readLine();
        if (raw == null) {
            throw new IOException("Input stream not open when in read mode.");
        }

        List<String> split = Arrays.stream(raw.trim().split("\\ "))
                .map(x -> x.trim()).collect(Collectors.toList());

        cmd = split.remove(0);
        argument = split.toArray(new String[split.size()]);
    }


    public Command getCommand() {
        switch (cmd) {
            case "user":
                return command = new FilterUser(argument);
            case "word":
                return command = new FilterWord(argument);
            case "conceal":
                return command = new ConcealCommand(argument);
            case "end":
                return command = new ExitCommand();
            case "concealUsers":
                return command = new ConcealUsers();
            default:
                System.out.println("This command can't be used.");
                return command;
        }
    }

    public String printCommands() {

        System.out.print(helper.formatMainMenuPrompt());
        return helper.formatMainMenuPrompt();
    }


    public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) {
        return new ConversationExporterConfiguration(arguments[0], arguments[1]);
    }
}
