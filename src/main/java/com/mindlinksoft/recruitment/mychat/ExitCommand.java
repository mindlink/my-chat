package com.mindlinksoft.recruitment.mychat;
public class ExitCommand implements Command {

    @Override
    public void execute(Model model) {
        model.setDone(true);
    }
}
