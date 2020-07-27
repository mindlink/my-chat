package com.mindlinksoft.recruitment.mychat;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class can be considered as the view class of the system. It deals with
 * formatting of data for presentation to the user, and parsing of user input
 * into commands.
 *
 * @author esteban
 */
public final class CommandLineArgumentParser {

    public BufferedReader reader;
    private Command command;
    private CLFormatter helper;
    private String cmd;
    private String[] argument;

    /**
     * This method will read a line of user input. it will trim the line of
     * leading/trailing white spaces, and split words according to spaces. First
     * word inputted by the user will be the command keyword.
     *
     * @param reader passes the BufferedReader in order to read the user input.
     * @throws IOException if there is any problem reading the input.
     */
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

    /**
     * getCommand method holds the switch which will create the commands
     * depending on user input cmd. default value will notify users that they
     * are not able to use a command. in order to create a new command, add a
     * new switch case and create a new command object. you will also need to
     * create a class of the command and implement the interface command execute
     * method.
     *
     * @return command
     */
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

    /**
     * Method to print the commands to the user.
     *
     * @return main menu
     */
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
