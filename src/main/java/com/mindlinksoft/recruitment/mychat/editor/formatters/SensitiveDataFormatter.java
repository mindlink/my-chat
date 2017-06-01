package com.mindlinksoft.recruitment.mychat.editor.formatters;

import com.google.i18n.phonenumbers.PhoneNumberMatch;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.mindlinksoft.recruitment.mychat.message.MessageInterface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Formatter that hides sensitive data (credit cards and phone numbers)
 */
public class SensitiveDataFormatter implements MessageFormatterInterface{

    /**
     * credit card regex taken from:
     * http://www.richardsramblings.com/regex/credit-card-numbers/
     * multiple card types section
     */
    private String multipleCardTypesRegEx =
            "\\b(?:3[47]\\d{2}([\\ \\-]?)\\d{6}\\1\\d|(?:(?:4\\d|5[1-5]|65)\\d{2}|6011)([\\ \\-]?)\\d{4}\\2\\d{4}\\2)\\d{4}\\b";

    /**
     * method that formats messages by replacing credit card numbers and phone numbers with *redacted*
     * uses google's libphonenumber, currently works for international phone numbers only -> +44 7575 757575
     * @param message object that implements {@link MessageInterface}
     * @return a message object that implements {@link MessageInterface}
     */
    @Override
    public MessageInterface formatMessage(MessageInterface message) {

        String messageContent = message.getContent();
        //replace credit card numbers
        messageContent = messageContent.replaceAll( this.multipleCardTypesRegEx , "*redacted*");

        //replace phone numbers
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        Iterable<PhoneNumberMatch> existsPhone = phoneUtil.findNumbers(messageContent, "UK");
        for (PhoneNumberMatch match :
                existsPhone) {
            messageContent = messageContent.replaceAll( "\\"+match.rawString() , "*redacted*" );
        }

        message.setContent(messageContent);
        return message;
    }
}

