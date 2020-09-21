package com.mindlinksoft.recruitment.mychat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilterUser implements Command {

    String[] argument;

    public FilterUser(String[] argument) {
        this.argument = argument;
    }

    @Override
    public void execute(Model model) {
        try {

            Conversation conversation = model.readConversation(model.getInputFile());
            Conversation newConversation = filterByUser(conversation, argument[0]);

            if (!newConversation.messages.isEmpty()) {
                model.writeConversation(newConversation, model.getOutputFile());
                System.out.println("message filtered by user " + argument[0]);
            } else {
                System.out.println("\nconversation not exported, user does not exist.");
                System.out.println("try again with a different user.");
            }
        } catch (Exception e) {
            System.out.println("Invalid or empty argument, please try again");
        }
    }

    public Conversation filterByUser(Conversation conversation, String argument) {

        List<Message> messages = new ArrayList<Message>(conversation.getMessages());
        List<Message> key = new ArrayList<Message>();
        key = messages.stream().filter(user -> user.getSenderId().equals(argument)).collect(Collectors.toList());
        return new Conversation(conversation.getName(), key);
    }
}
