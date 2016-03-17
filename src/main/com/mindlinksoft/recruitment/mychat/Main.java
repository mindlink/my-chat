package com.mindlinksoft.recruitment.mychat;

/**
 * Created by alvaro on 16/03/16.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        String [] args1 = new String[]{"-i", "chat.txt", "-o", "output.json", "-u", "bob"};
        String [] args2 = new String[]{"-i", "chat.txt", "-o", "output.json", "-k", "pie"};
        String [] args3 = new String[]{"-i", "chat.txt", "-o", "output.json", "-b", "pie,society,no,yes"};
        String [] args4 = new String[]{"-i", "chat.txt", "-o", "output.json", "-u", "bob", "-b", "pie,society,no,yes"};

        String [] args5 = new String[]{"-i", "chat.txt", "-o", "output.json", "-n"};
        String [] args6 = new String[]{"-i", "chat.txt", "-o", "output.json", "-f"};

        ConversationExporter exporter = new ConversationExporter();
        exporter.main(args2);

    }
}
