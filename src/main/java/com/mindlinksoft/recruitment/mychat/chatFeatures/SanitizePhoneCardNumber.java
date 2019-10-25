package com.mindlinksoft.recruitment.mychat.chatFeatures;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SanitizePhoneCardNumber implements ChatFeature {

    @Override
    public void setArgument(String argument) {
    }

    /**
     * Called for tasks needed just prior to JSON export. Not needed for this feature.
     */
    @Override
    public void duringExport(Conversation conversation) {
    }

    @Override
    public Message beforeExport(Message message) {
        String phoneNumberRegex = "^((\\(?0\\d{4}\\)?\\s?\\d{3}\\s?\\d{3})|(\\(?0\\d{3}\\)?\\s?\\d{3}\\s?\\d{4})|(\\(?0\\d{2}\\)?\\s?\\d{4}\\s?\\d{4}))(\\s?#(\\d{4}|\\d{3}))?$";
        HashMap<Integer,Integer> indexMap = new HashMap<>(getPotentialCardNumberIndexes(message.content));
        String substring;

        for(int start : indexMap.keySet()){
            substring = message.content.substring(start, indexMap.get(start));
            if(LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(substring)){
                message.content = message.content.replace(substring, "*redacted*");
            }
        }

        message.content = message.content.replaceAll(phoneNumberRegex, "*redacted*");

        return message;
    }

    private static HashMap<Integer,Integer> getPotentialCardNumberIndexes(String text) {
        HashMap<Integer,Integer> indexMap = new HashMap<>();

        String cardNumberLengthRegex = "[0-9]{8,19}";
        Pattern pattern = Pattern.compile(cardNumberLengthRegex);
        Matcher matcher = pattern.matcher(text);
        // Check all occurrences
        while (matcher.find()) {
            indexMap.put(matcher.start(), matcher.end());
        }
        return indexMap;
    }
}
