package com.mindlinksoft.recruitment.mychat;

/**
 * Command interface to be extended by all the concrete commands.
 *
 * @author esteban
 */
public interface Command {

    void execute(Model model);

}
