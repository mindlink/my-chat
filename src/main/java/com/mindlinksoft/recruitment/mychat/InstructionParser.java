package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InstructionParser {
    public String command;

    public List<String> arguments;

    public InstructionParser(String instruction) {
        // We are given an instruction in the form "command:argument1,argument2,..."

        String[] split = instruction.split(":");
        if(split.length<2) throw new IllegalArgumentException("Invalid instruction - instruction must be in the " +
                "form 'command:argument1,argument2...'");

        this.command   = split[0];
        this.arguments = Arrays.asList(split[1].split(","));
    }
}
