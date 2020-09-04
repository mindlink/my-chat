package com.mindlinksoft.recruitment.mychat;

import com.mindlinksoft.recruitment.mychat.Tools.ConversationExporter;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CLI {
    public static void main(String[] args) throws Exception {
        System.out.print("---------------- Welcome ----------------\n type and enter help for more information\n");
        while (true){
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            String input = br.readLine();

            if (input.equals("help")){
                System.out.println("'quit' to quit\n" +
                        "'ce -d' to run ConversationExporter with default parameters\n" +
                        "'ce filter <name>' to export messages from one user only");
            }
            if (input.equals("ce -d")){
                String[] arguments = new String[]{"D:\\Desktop\\my-chat\\chat.txt", "D:\\Desktop\\my-chat\\chat.json", "", ""};
                ConversationExporter.main(arguments);
            }
            if (input.startsWith("ce filter")){
                String[] splitStrings = input.split(" ");
                String name = splitStrings[2];
                String[] arguments = new String[]{"D:\\Desktop\\my-chat\\chat.txt", "D:\\Desktop\\my-chat\\chat.json", "-f", name};
                ConversationExporter.main(arguments);

            }
            if (input.equals("quit")) {
                break;
            }
        }

    }
}

