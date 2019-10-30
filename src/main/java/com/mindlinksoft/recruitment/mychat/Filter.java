package com.mindlinksoft.recruitment.mychat;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the configuration for the exporter.
 */
public class Filter {
    public FilterMethod filterMethod;

    public enum FilterMethod {
        USERNAME, SPECIFIC_WORD, REMOVE_WORDS, NO_FILTER;
    }

    public Conversation conversation_name;

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
     */
    public void noFilter(String whetherToHideCardAndPhoneNumbers, String whetherToObfuscateUserIds) throws Exception {

        ConversationExporter conversationExporter = new ConversationExporter();
        conversationExporter.writeConversation(conversationExporter.readConversation(whetherToObfuscateUserIds), whetherToHideCardAndPhoneNumbers);
    }

    /**
     * Method which uses user input and compares it against the usernames within the
     * conversation. If there is a match, the specified username's messages are sent
     * to the writeConversation method and presented in json format.
     *
     * @param username
     * @throws Exception
     */
    public void searchUserMessages(String username, String whetherToHideCardAndPhoneNumbers, String whetherToObfuscateUserIds) throws Exception {
        ConversationExporter c = new ConversationExporter();
        List<Message> messageList = new ArrayList<>();


        try {
            c.readConversation(whetherToObfuscateUserIds).messages.forEach(s -> {

                if (s.username.equals(username)) {
                    StringBuilder str = new StringBuilder();
                    str.append(s);
                    Message m = new Message((s.unix_timestamp), s.username, s.message);
                    messageList.add(m);

                    System.out.println("m " + m);
                } else {
                    //System.out.println("Username not found.");
                }
            });
            try {

                Conversation filteredConvo = new Conversation(c.conversation_name, messageList);


                c.writeConversation(filteredConvo, whetherToHideCardAndPhoneNumbers);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.out.println("Conversation not found.");
        }
    }

    /**
     * Method which uses user input and compares it against the words within the
     * conversation. If there is a match, the specified messages that include the
     * to the writeConversation method and presented in json format.  @param specWord
     */
    public void searchSpecificWord(String specWord, String whetherToHideCardAndPhoneNumbers, String whetherToObfuscateUserIds) throws Exception {
        ConversationExporter c = new ConversationExporter();
        List<Message> messageList = new ArrayList<>();


        try {
            c.readConversation(whetherToObfuscateUserIds).messages.forEach(s -> {

                if (s.message.contains(specWord)) {
                    System.out.println("User messages which contain specword " + s);
                    Message m = new Message((s.unix_timestamp), s.username, s.message);
                    messageList.add(m);
                    System.out.println("m " + m);
                } else {
                    //System.out.println("Word not found in this sentence.");
                }
            });
            try {
                Conversation filteredConvo = new Conversation(c.conversation_name, messageList);
                c.writeConversation(filteredConvo, whetherToHideCardAndPhoneNumbers);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.out.println("Conversation not found.");
        }
    }

    /**
     * Method which uses user input and compares it against the words within the
     * conversation. If there is a match, the messages that include the specified word
     * are removed and replaced by '*redacted*' in the output.
     *
     * @param specWord
     */
    public void hideSpecificWord(String[] stringToFilterBy, String whetherToHideCardAndPhoneNumbers, String whetherToObfuscateUserIds) throws Exception {
        ConversationExporter c = new ConversationExporter();
        List<Message> messageList = new ArrayList<>();

        try {
            for (Message s : c.readConversation(whetherToObfuscateUserIds).messages) {
                for (String wordToCensor : stringToFilterBy) {

                    if (s.message.toLowerCase().contains(wordToCensor.toLowerCase())) {
                        s.message = s.message.replaceAll(wordToCensor, "*redacted*");
                    }
                }
                Message m = new Message((s.unix_timestamp), s.username, s.message);
                messageList.add(m);
            }
            try {


                Conversation filteredConvo = new Conversation(c.conversation_name, messageList);
                c.writeConversation(filteredConvo, whetherToHideCardAndPhoneNumbers);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.out.println("Conversation not found.");
        }
    }


}
