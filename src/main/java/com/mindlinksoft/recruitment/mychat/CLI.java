package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.Tools.ConversationExporter;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CLI {
    public static void main(String[] args) throws Exception {
        System.out.print("---------------- Welcome ----------------\n type and enter help for more information\n");
        while (true) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            String input = br.readLine();

            if (input.equals("help")) {
                System.out.println("'ce d' to run ConversationExporter with default parameters\n" +
                        "'ce name <name>' to export messages from one user only\n" +
                        "'ce keyword <keyword>' to export messages containing keyword \n" +
                        "'ce hide <words,in,this,format>' to export messages with hidden words as '*redacted*'\n" +
                        "'ce details to export messages with credit card and phone numbers words as '*redacted*'\n" +
                        "'quit' to quit\n");
            }
            if (input.equals("ce d")) {
                String[] arguments = new String[]{"chat.txt", "chat.json", "", ""};
                ConversationExporter.main(arguments);
            }
            if (input.startsWith("ce name")) {
                String[] splitStrings = input.split(" ");
                String name = splitStrings[2];
                String[] arguments = new String[]{"chat.txt", "chat.json", "-name", name};
                ConversationExporter.main(arguments);

            }
            if (input.startsWith("ce keyword")) {
                String[] splitStrings = input.split(" ");
                String name = splitStrings[2];
                String[] arguments = new String[]{"chat.txt", "chat.json", "-keyword", name};
                ConversationExporter.main(arguments);

            }
            if (input.startsWith("ce hide")) {
                String[] splitStrings = input.split(" ");
                String name = splitStrings[2];
                String[] arguments = new String[]{"chat.txt", "chat.json", "-hide", name};
                ConversationExporter.main(arguments);

            }
            if (input.equals("ce details")) {
                String[] arguments = new String[]{"chat.txt", "chat.json", "-details", ""};
                ConversationExporter.main(arguments);
            }
            if (input.equals("quit")) {
                break;
            }
        }

    }

}

