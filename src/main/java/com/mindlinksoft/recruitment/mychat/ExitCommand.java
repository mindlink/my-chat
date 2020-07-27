/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindlinksoft.recruitment.mychat;

/**
 *
 * @author esteban
 */
public class ExitCommand implements Command {

    @Override
    public void execute(Model model) {
        model.setDone(true);
    }

}
