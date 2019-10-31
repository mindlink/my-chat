package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents how to filter the conversation: i.e which method of filtering the user chooses based on
 * their command line arguments.
 */
public class Filter {
    public FilterMethod filterMethod;

    public enum FilterMethod {
        USERNAME, SPECIFIC_WORD, REMOVE_WORDS, NO_FILTER;
    }


    /**
     * Constructor for the class. Instantiates the filterMethod type as designated by the user input.
     *
     * @param filterMethod Matches to the enum of type FilterMethod
     */
    public Filter(FilterMethod filterMethod) {
        this.filterMethod = filterMethod;
    }

    /**
     * Method is used in the simplest use case - where the user simply wants to get the json of all
     * the messages from the conversation. No filtering is necessary.
     *
     * @throws Exception
     * @param hideCardAndPhoneNumbers
     * @param obfuscateUserIds
     */
    public void noFilter(Boolean hideCardAndPhoneNumbers, Boolean obfuscateUserIds) throws Exception {

        ConversationExporter conversationExporter = new ConversationExporter();
        conversationExporter.writeConversation(conversationExporter.readConversation(obfuscateUserIds), hideCardAndPhoneNumbers);
    }

    /**
     * Method which uses user input and compares it against the usernames within the
     * conversation. If there is a match, the specified username's messages are sent
     * to the writeConversation method and presented in json format.
     *
     * @param username
     * @param hideCardAndPhoneNumbers
     * @param obfuscateUserIds
     * @throws Exception
     */
    public void searchUserMessages(String username, Boolean hideCardAndPhoneNumbers, Boolean obfuscateUserIds) throws Exception {
        ConversationExporter c = new ConversationExporter();
        List<Message> messageList = new ArrayList<>();
        try {
            c.readConversation(obfuscateUserIds).messages.forEach(s -> {
                if (s.username.equals(username)) {
                    StringBuilder str = new StringBuilder();
                    str.append(s);
                    Message m = new Message((s.unix_timestamp), s.username, s.message);
                    messageList.add(m);
                } else {
                    //System.out.println("Username not found.");
                }
            });

                Conversation filteredConvo = new Conversation(c.conversation_name, messageList);
                c.writeConversation(filteredConvo, hideCardAndPhoneNumbers);

        } catch (Exception e) {
            System.out.println("Conversation not found.");
        }
    }

    /**
     * Method which uses user input and compares it against the words within the
     * conversation. If there is a match, the specified messages that include the
     * to the writeConversation method and presented in json format.  @param specificWord
     */
    public void searchSpecificWord(String specificWord, Boolean hideCardAndPhoneNumbers, Boolean obfuscateUserIds) throws Exception {
        ConversationExporter c = new ConversationExporter();
        List<Message> messageList = new ArrayList<>();
        try {
            c.readConversation(obfuscateUserIds).messages.forEach(s -> {

                if (s.message.contains(specificWord)) {
                    Message m = new Message((s.unix_timestamp), s.username, s.message);
                    messageList.add(m);
                } else {
                    //System.out.println("Word not found in this sentence.");
                }
            });
                Conversation filteredConvo = new Conversation(c.conversation_name, messageList);
                c.writeConversation(filteredConvo, hideCardAndPhoneNumbers);

        } catch (Exception e) {
            System.out.println("Conversation not found.");
        }
    }

    /**
     * Method which uses user input and compares it against the words within the
     * conversation. If there is a match, the messages that include the specified word
     * are removed and replaced by '*redacted*' in the output.
     *
     * @param stringToFilterBy
     * @param hideCardAndPhoneNumbers
     * @param obfuscateUserIds
     */
    public void hideSpecificWord(String[] stringToFilterBy, Boolean hideCardAndPhoneNumbers, Boolean obfuscateUserIds) throws Exception {
        ConversationExporter c = new ConversationExporter();
        List<Message> messageList = new ArrayList<>();

        try {
            for (Message s : c.readConversation(obfuscateUserIds).messages) {
                for (String wordToCensor : stringToFilterBy) {

                    if (s.message.toLowerCase().contains(wordToCensor.toLowerCase())) {
                        s.message = s.message.replaceAll(wordToCensor, "*redacted*");
                    }
                }
                Message m = new Message((s.unix_timestamp), s.username, s.message);
                messageList.add(m);
            }
                Conversation filteredConvo = new Conversation(c.conversation_name, messageList);
                c.writeConversation(filteredConvo, hideCardAndPhoneNumbers);

        } catch (Exception e) {
            System.out.println("Conversation not found.");
        }
    }


}
