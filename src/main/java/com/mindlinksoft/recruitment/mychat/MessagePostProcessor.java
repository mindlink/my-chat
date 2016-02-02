package mychat;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessagePostProcessor implements IMessageProcessor {
    PostProcessOptions userMessagePostProcessingOptions;

    public MessagePostProcessor(PostProcessOptions processOptions) {

        this.userMessagePostProcessingOptions = processOptions;
    };

    public void processMessage(Message message) {

        if (userMessagePostProcessingOptions.getHideWords() != null) {
            message = hideSpecificWords(message, userMessagePostProcessingOptions.getHideWords());
        if (userMessagePostProcessingOptions.isHideCreditCard()) {
           message = hideCreditCardDetails(message);
        }
        if (userMessagePostProcessingOptions.isObfuscate()) {
            message = obfuscateUserIds(message, userMessagePostProcessingOptions.getUserIdToBeObfuscated(), userMessagePostProcessingOptions.getUserIdToBeObfuscatedWith());
        }
        }
    }


    private Message hideSpecificWords(Message message, ArrayList<String> listOfWords) {
        for (String word : listOfWords) {
            message.setContent(message.getContent().replace(word, "*redacted*"));
        }
        return message;
    }


    private Message hideCreditCardDetails(Message message) {

        final Pattern PATTERNCARD =
                Pattern.compile("\\b([0-9]{4})[0-9]{0,9}([0-9]{4})\\b");
        Matcher matcher = PATTERNCARD.matcher(message.getContent());
        if (matcher.find()) {
            message.setContent(matcher.replaceAll("*redacted*"));
        }
        return message;
    }


    private Message obfuscateUserIds(Message message, String userIdToBeObfuscated, String userIdToBeObfuscatedWith) {
        if (message.getSenderId().equals(userIdToBeObfuscated)) {
            message.setSenderId( userIdToBeObfuscatedWith);
        }
    return message;
    }
}
