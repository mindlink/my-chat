package com.mindlinksoft.recruitment.mychat;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Represents the model of a conversation.
 */
public final class Conversation {
    /**
     * The name of the conversation.
     */
    public String name;

    /**
     * The messages in the conversation.
     */
    public Collection<Message> messages;

    /**
     * Initializes a new instance of the {@link Conversation} class.
     * @param name The name of the conversation.
     * @param messages The messages in the conversation.
     */
    public Conversation(String name, Collection<Message> messages) {
        this.name = name;
        this.messages = messages;
    }

    /**
     * Filter messages by senderId
     * @param username User's name to filter by (case-sensitive)
     */
    public void applyUserFilter(String username) {
        ArrayList<Message> newMessages = new ArrayList<>();

        for (Message message : messages) {
            if (message.senderId.equals(username)) {
                newMessages.add(message);
            }
        }

        messages = newMessages;
    }

    /**
     * Filter message contents by keyword
     * @param keyword Keyword to filter (case-insensitive)
     */
    public void applyKeywordFilter(String keyword) {
        ArrayList<Message> newMessages = new ArrayList<>();
        Pattern pattern = Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE);

        for (Message message : messages) {
            if (pattern.matcher(message.content).find()) {
                newMessages.add(message);
            }
        }

        messages = newMessages;
    }

    /**
     * Censor words contained in the blacklist
     * @param path Path to blacklist file
     */
    public void applyBlacklist(String path) throws IllegalArgumentException, IOException {
        Collection<String> blacklist = new ArrayList<>();

        // Load blacklist from file
        try(InputStream is = new FileInputStream(path);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            String line;
            while ((line = r.readLine()) != null) {
                // Trim whitespace and make sure word is one or more characters long
                String word = line.trim();
                if (word.length() > 0) {
                    blacklist.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(Resources.BLACKLIST_NOT_FOUND);
        }

        // Remove blacklisted words from messages
        for (String bannedWord : blacklist) {
            Pattern pattern = Pattern.compile(Pattern.quote(bannedWord), Pattern.CASE_INSENSITIVE);

            for (Message message : messages) {
                message.content = pattern.matcher(message.content).replaceAll(Resources.REDACTED);
            }
        }
    }

    /**
     * Replace user's names with anonymous names
     */
    public void obfuscateUserNames() {
        HashMap<String, String> anonNames = new HashMap<>();
        int currentID = 1;

        // Replace message senderIds with names from anonNames
        for (Message message : messages) {
            // If this user hasn't been assigned an anonymous name, create one
            if (!anonNames.containsKey(message.senderId)) {
                anonNames.put(message.senderId, Resources.OBFUSCATE_PREFIX + currentID);
                currentID++;
            }

            message.senderId = anonNames.get(message.senderId);
        }
    }

    /**
     * Hide phone numbers and credit cars numbers
     */
    public void hidePersonalInformation() {
        // Match large numbers split by dashes or spaces as they are most likely a phone/credit card number
        Pattern pattern = Pattern.compile(Resources.PERSONAL_REGEX);

        for (Message message : messages) {
            message.content = pattern.matcher(message.content).replaceAll(Resources.REDACTED);
        }
    }
}
