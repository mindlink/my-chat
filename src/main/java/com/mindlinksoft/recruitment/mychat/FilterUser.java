/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindlinksoft.recruitment.mychat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete command FilterUser, filters a conversation by a specific users
 *
 * @author esteban
 */
public class FilterUser implements Command {

    String[] argument;

    public FilterUser(String[] argument) {
        this.argument = argument;
    }

    @Override
    public void execute(Model model) {
        try {

            Conversation conversation = model.filterConversationByUser(model.getInputFile(), argument[0]);

            model.writeConversation(conversation, model.getOutputFile());
        } catch (Exception e) {
            System.out.println("Invalid or empty argument, please try again");
        }
    }

}
