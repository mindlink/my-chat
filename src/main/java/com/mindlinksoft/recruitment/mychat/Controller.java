package com.mindlinksoft.recruitment.mychat;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Controller {

    private FormatCL helper;
    private CommandLineArgumentParser parser;
    private BufferedReader reader;
    private Model model;

    public Controller(CommandLineArgumentParser parser, Model model) {

        this.reader = null;
        this.parser = parser;
        this.model = model;
    }

    public void run() throws IOException {

        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            helper = new FormatCL();
            model.exportConversation(model.getInputFile(), model.getOutputFile());
            System.out.print(helper.formatExplanation());
            readCommand();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            reader.close();
        }
    }

    public void readCommand() throws IOException {

        for (model.getDone(); !model.getDone();) {

            parser.printCommands();

            parser.readInput(reader);
            Command command = parser.getCommand();
            if (command == null) {
                System.out.println("Command is unrecognised, try again and choose one of the commands available.");
            } else {
                command.execute(model);
            }
        }
    }

}
