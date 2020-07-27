/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindlinksoft.recruitment.mychat;

/**
 * Terminates the loop that runs the system when executed.
 *
 * @author esteban
 */
public class ExitCommand implements Command {

    @Override
    public void execute(Model model) {
        model.setDone(true);
    }

}
