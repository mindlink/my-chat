package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents the filter that operates the filter functions for messages
 *
 * @author Muhammad
 */
public class Filter {

    //The type of filter
    String filterType;

    //The value of the argument
    String argumentValue;

    //Control for hiding sensitive data
    String hideSensitiveDataControl;

    /**
     * Constructor that takes a parameter of filter type and argument value.
     *
     * @param filterType The type of filter e.g filterword, filteruser, hideword
     * (assuming there is a type of filter identified in command-line argument)
     * @param argumentValue The argument's value that is used for filter e.g.
     * filterword pie
     */
    public Filter(String filterType, String argumentValue) {
        this.filterType = filterType;
        this.argumentValue = argumentValue;
    }

    /**
     * Constructor that takes a parameter of filter type and argument value.
     *
     * @param filterType The type of filter e.g filterword, filteruser, hideword
     * (assuming there is a type of filter identified in command-line argument)
     * @param argumentValue The argument's value that is used for filter e.g.
     * filterword pie
     * @param hideSensitive The control name for hiding sensitive data.
     * (Assuming the control name is hidesensitive.)
     */
    public Filter(String filterType, String argumentValue, String hideSensitive) {
        this(filterType, argumentValue);
        this.hideSensitiveDataControl = hideSensitive;
    }

    /**
     * Method that filters a conversation by a specific user and return filtered
     * conversation.
     *
     * @param conversation The conversation to be filtered.
     * @param specificUser Specific user to be used as a filter's parameter.
     * @return Filtered Conversation.
     */
    public static Conversation filterByUser(Conversation conversation, String specificUser) {
        List<Message> messageList = new ArrayList<>();
        //Filter by used id
        for (Message message : conversation.messages) {
            if (message.senderId.equals(specificUser)) {
                messageList.add(message);
                Conversation filteredConversation = new Conversation(conversation.name, messageList);
                conversation = filteredConversation;
            }
        }
        return conversation;
    }

    /**
     * Method that filters a conversation that contains a specific keyword and
     * returns filtered conversation.
     *
     * @param conversation The conversation to be filtered.
     * @param specificWord Specific word to be used as a filter's parameter.
     * @return Filtered Conversation.
     */
    public static Conversation filterByWord(Conversation conversation, String specificWord) {
        List<Message> messageList = new ArrayList<>();
        //Filter by keyword
        for (Message message : conversation.messages) {
            if (message.content.contains(specificWord)) {
                messageList.add(message);
                Conversation filteredConversation = new Conversation(conversation.name, messageList);
                conversation = filteredConversation;
            }
        }
        return conversation;
    }

    /**
     * Method that hides a word in a conversation by a specific word
     *
     * @param conversation The conversation to be filtered.
     * @param specificWord Word to be hidden as a filter's parameter.
     * @return Conversation with hidden words.
     */
    public static Conversation hideWord(Conversation conversation, String specificWord) {
        List<Message> messageList = new ArrayList<>();
        //Filter by used id
        for (Message message : conversation.messages) {
            if (message.content.contains(specificWord)) {
                message.content = message.content.replaceAll(specificWord, "*redacted*");
                messageList.add(message);
                Conversation filteredConversation = new Conversation(conversation.name, messageList);
                conversation = filteredConversation;
            }
        }
        return conversation;
    }

    /**
     * Method that hides credit card and phone numbers in a conversation.
     *
     * @param conversation The conversation to be filtered.
     * @return Conversation with hidden credit card and phone numbers.
     */
    public static Conversation hideCreditCardAndPhoneNumbers(Conversation conversation) {
        List<Message> messageList = new ArrayList<>();

        //Filter by used id
        for (Message message : conversation.messages) {

            message.content = filterSensitiveData(message.content);
            messageList.add(message);
            Conversation filteredConversation = new Conversation(conversation.name, messageList);
            conversation = filteredConversation;

        }
        return conversation;
    }

    /**
     * Scans the message content for creditcard or phone number.
     *
     * @param messageContent The message content.
     * @return A string with *redacted* word replaced.
     */
    public static String filterSensitiveData(String messageContent) {

        String creditCardAndPhoneNumbersRegex = "\\b\\d{13,16}\\b" //Credit card numbers without spaces. Example format XXXXXXXXXXXXXXXX
                + "|\\b\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}\\b" //Credit card numbers with spaces. Example format XXXX XXXX XXXX XXXX
                + "|\\b\\d{11,}\\b" //Phone number format 02045677896
                + "|\\b\\d{3}\\s\\d{4}\\s\\d{4}\\b"; //Phone number format 020 4567 7896

        Pattern pattern = Pattern.compile(creditCardAndPhoneNumbersRegex);
        Matcher matcher = pattern.matcher(messageContent);

        if (matcher.find()) {
            messageContent = matcher.replaceAll("*redacted*");
        }

        return messageContent;
    }

    /**
     * Method that hides sensitive data if assumed "hidesensitive" exist in the
     * argument else default.
     *
     * @param hideSensitiveData The control name for "hidesensitive"
     * @param conversation The conversation to be filtered.
     * @return A filtered conversation.
     */
    public static Conversation hideSensitiveData(String hideSensitiveData, Conversation conversation) {
        if (hideSensitiveData.equals("hidesensitive")) {
            conversation = Filter.hideCreditCardAndPhoneNumbers(conversation);
            return conversation;
        } else {
            return conversation;
        }

    }

}
