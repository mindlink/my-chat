package com.mindlinksoft.recruitment.mychat;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Redaction {

    static List<Message> redact(Collection<Message> messages, Map<String, String> map){
        if (map != null){

            // Blacklist word flag
            if (map.containsKey("-b")){
                messages = redactBlacklistWords(messages, map.get("-b"));
            }

            // Redact phone and credit card number flag
            if (map.containsKey("-r")){

                String redactArg = map.get("-r");

                if (redactArg.contains("ph")){
                    messages = redactPhoneNumbersUK(messages);
                }

                if (redactArg.contains("cc")){
                    messages = redactCreditCardNumbers(messages);
                }
            }
        }

        return (List<Message>) messages;
    }

    /**
     * Redacts blacklisted words
     * @param messages
     * @param blacklistedWord
     * @return
     */
    private static Collection<Message> redactBlacklistWords(Collection<Message> messages, String blacklistedWord) {

        String finalBlacklistedWord = blacklistedWord.toLowerCase();
        return messages.stream()
                .map(message -> {
                    if ( message.getContent().toLowerCase().contains(finalBlacklistedWord)){
                        message.setContent(message.getContent().replace(finalBlacklistedWord, "*redacted*"));
                    }
                    return message;
                })
                .collect(Collectors.toList());
    }


    /**
     * Replaces credit card numbers with "*redacted*"
     * @param messages
     * @return
     */
    private static Collection<Message> redactCreditCardNumbers(Collection<Message> messages){

        String regex = "((?:(?:\\d{4}[- ]){3}\\d{4}|\\d{16}))(?![\\d])";
        return messages.stream()
                .map(message -> {
                    message.setContent(message.getContent().replaceAll(regex, "*redacted*"));
                    return message;
                })
                .collect(Collectors.toList());
    }

    /**
     * Replaces UK phone numbers with "*redacted*"
     * @param messages
     * @return
     */
    private static Collection<Message> redactPhoneNumbersUK(Collection<Message> messages){

        String regex = "(((\\+44\\s?\\d{4}|" +
                "\\(?0\\d{4}\\)?)\\s?\\d{3}\\s?\\d{3})|" +
                "((\\+44\\s?\\d{3}|" +
                "\\(?0\\d{3}\\)?)\\s?\\d{3}\\s?\\d{4})|" +
                "((\\+44\\s?\\d{2}|" +
                "\\(?0\\d{2}\\)?)\\s?\\d{4}\\s?\\d{4}))(\\s?\\#(\\d{4}" +
                "|\\d{3}))?$";

        String regex2 = "(?:(?:\\(?(?:0(?:0|11)\\)?[\\s-]?\\(?|\\+)44\\)?[\\s-]?(?:\\(?0\\)?[\\s-]?)?)|(?:\\(?0))(?:(?:\\d{5}\\)?[\\s-]?\\d{4,5})|(?:\\d{4}\\)?[\\s-]?(?:\\d{5}|\\d{3}[\\s-]?\\d{3}))|(?:\\d{3}\\)?[\\s-]?\\d{3}[\\s-]?\\d{3,4})|(?:\\d{2}\\)?[\\s-]?\\d{4}[\\s-]?\\d{4}))(?:[\\s-]?(?:x|ext\\.?|\\#)\\d{3,4})?$";

        return messages.stream()
                .map(message -> {
//                    if (message.getContent().matches(regex)){
//                        message.setContent(message.getContent().replaceAll(regex, "*redacted*"));
//                    }

//                    if (message.getContent().matches(regex2)){
//                        message.setContent(message.getContent().replaceAll(regex2, "*redacted*"));
//                    }
                    message.setContent(message.getContent().replaceAll(regex2, "*redacted*"));
                    message.setContent(message.getContent().replaceAll(regex, "*redacted*"));

                    return message;
                })
                .collect(Collectors.toList());
    }
}

