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

    /**
     * Constructor for the class. Instantiates the filterMethod type as designated by the user input.
     * @param filterMethod Matches to the enum of type FilterMethod
     */
    public Filter(FilterMethod filterMethod) {
        this.filterMethod = filterMethod;
    }

    /**
     * Method is used in the simplest use case - where the user simply wants to get the json of all
     * the messages from the conversation. No filtering is necessary.
     * @throws Exception
     */
    public void noFilter() throws Exception{

        ConversationExporter conversationExporter = new ConversationExporter();
        conversationExporter.writeConversation(conversationExporter.readConversation());
    }

    /**
     * Method which uses user input and compares it against the usernames within the
     * conversation. If there is a match, the specified username's messages are sent
     * to the writeConversation method and presented in json format.
     *
     * @param username
     * @throws Exception
     */
    public void searchUserMessages(String username) throws Exception {
        ConversationExporter c = new ConversationExporter();
        List<Message> messageList = new ArrayList<>();
        String conversationName = c.readConversation().conversation_name;
        try {
            c.readConversation().messages.forEach(s -> {

                if (s.username.equals(username)) {
                    System.out.println("User messages " + s);


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
                Conversation filteredConvo = new Conversation(conversationName, messageList);
                filteredConvo.messages.forEach(b -> {
                    // System.out.println(b);
                });
                c.writeConversation(filteredConvo);
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
    public void searchSpecificWord(String specWord) throws Exception {
        ConversationExporter c = new ConversationExporter();
        List<Message> messageList = new ArrayList<>();
        String conversationName = c.readConversation().conversation_name;


        try {
            c.readConversation().messages.forEach(s -> {

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
                Conversation filteredConvo = new Conversation(conversationName, messageList);
                c.writeConversation(filteredConvo);
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
    public void hideSpecificWord(String specWord) throws Exception {
        ConversationExporter c = new ConversationExporter();
        List<Message> messageList = new ArrayList<>();
        String conversationName = c.readConversation().conversation_name;


        try {
            c.readConversation().messages.forEach(s -> {

                if (s.message.contains(specWord)) {

                    StringBuilder censoredMessages = new StringBuilder();
                    String[] split = s.message.split(" ");
                    for (String singleWord : split) {
                        if (singleWord.contains(specWord)) {
                            singleWord = singleWord.replace(specWord, "*redacted*");

                            censoredMessages.append(singleWord);

                        } else {
                            censoredMessages.append(" " + singleWord + " ");
                        }
                    }
                    s.message = censoredMessages.toString();


                    Message m = new Message((s.unix_timestamp), s.username, s.message);
                    messageList.add(m);

                } else {
                    //System.out.println("Word not found in this sentence.");
                }
            });
            try {
                Conversation filteredConvo = new Conversation(conversationName, messageList);
                c.writeConversation(filteredConvo);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.out.println("Conversation not found.");
        }
    }




}
