package com.mindlinksoft.recruitment.mychat;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {

    public BufferedReader reader;
    private Command command;
    private CLFormatter helper;
    private String cmd;
    private String[] argument;

    public void readInput(BufferedReader reader) throws IOException {
        String raw = reader.readLine();
        if (raw == null) {
            throw new IOException("Input stream closed while reading.");
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
            case "keyword":
                return command = new FilterKeyword(argument);
            case "hide":
                return command = new HideCommand(argument);
            case "exit":
                return command = new ExitCommand();
            case "hideUsers":
                return command = new HideUsers();
            default:
                System.out.println("You can't use this command right now..");
                return command;
        }
    }

    public String printCommands() {

        System.out.print(helper.formatMainMenuPrompt());
        return helper.formatMainMenuPrompt();
    }

    /**
     * Parses the given {@code arguments} into the exporter configuration.
     *
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line
     * arguments.
     */
    public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) {
        return new ConversationExporterConfiguration(arguments[0], arguments[1]);
    }
}
