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
 * This class acts as a controller assembling the Model and Parser view, it
 * coordinates operations between them. This controller contains the run method
 * and main loop.
 *
 * @author esteban
 */
public class Controller {

    private CLFormatter helper;
    private CommandLineArgumentParser parser;
    private BufferedReader reader;
    private Model model;

    /**
     * Controller constructor binds the model and Parser view to this
     * controller.
     *
     * @param model the model
     * @param parser the view
     */
    public Controller(CommandLineArgumentParser parser, Model model) {

        this.reader = null;
        this.parser = parser;
        this.model = model;
    }

    /**
     * This method gets called by the main(String[] args) in
     * ConversationExporter. print the first welcome message with explanation of
     * commands. then starts the main loop.
     *
     * @throws IOException
     */
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

    /**
     * Main loop method prints the commands, reads the user input and parses
     * them as a command. if it doesn't recognise a command it will return an
     * error message.
     *
     * @throws IOException
     */
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
