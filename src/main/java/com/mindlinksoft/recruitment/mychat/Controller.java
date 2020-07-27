/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindlinksoft.recruitment.mychat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author esteban
 */
public class Controller {

    private CLFormatter helper;
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
            helper = new CLFormatter();
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
                System.out.println("Command is not recognised, try again and select one of the commands available.");
            } else {
                command.execute(model);
            }
        }
    }

}
