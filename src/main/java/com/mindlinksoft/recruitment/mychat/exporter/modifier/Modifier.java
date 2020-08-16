package com.mindlinksoft.recruitment.mychat.exporter.modifier;

/**
 * The types of modification that will be applied to a conversation
 */
public enum Modifier {
    FILTER_USER,
    FILTER_KEYWORD,
    HIDE_KEYWORD,
    HIDE_CREDIT_CARD_AND_PHONE_NUMBERS,
    OBFUSCATE_USERS,
    REPORT_ACTIVE_USERS
}