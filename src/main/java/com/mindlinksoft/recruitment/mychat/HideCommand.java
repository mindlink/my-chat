package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HideCommand implements Command {

    String[] argument;

    public HideCommand(String[] argument) {
        this.argument = argument;
    }

    @Override
    public void execute(Model model) {

        try {

            Conversation conversation = model.readConversation(model.getInputFile());
            Conversation filtConv = hideKeyw(conversation, argument[0]);

            model.writeConversation(filtConv, model.getOutputFile());
            System.out.println("Conversation exported from '" + model.getInputFile() + "' to '" + model.getOutputFile() + " and hiding the keyword: " + argument[0]);

        } catch (Exception e) {
            System.out.println("Invalid or empty argument, please try again");
        }

    }

    public Conversation hideKeyw(Conversation conversation, String argument) {

        List<Message> messages = new ArrayList<Message>(conversation.getMessages());

        List<Message> key = conversationHiddenKey(messages, argument);

        return new Conversation(conversation.getName(), key);
    }
    
    private static List<Message> conversationHiddenKey(List<Message> messages, String keyword) {
        List<Message> result = new ArrayList<>();

        for (Message temp : messages) {
            String filtered = temp.getContent().replaceAll(keyword, "*redacted*");
            result.add(new Message(temp.getTimestamp(), temp.getSenderId(), filtered));
        }
        return result;
    }
}
