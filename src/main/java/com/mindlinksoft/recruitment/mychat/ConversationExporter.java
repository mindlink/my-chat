package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.*;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {


    /**
     * The application entry point.
     *
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, configuration.additionalArgs);
    }

    public List<String> readInBlacklist(String blacklistFilePath) throws readException {

        List<String> blacklisted = new ArrayList<String>();

        try {
            InputStream is = new FileInputStream(blacklistFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is));

            String line;

            while ((line = r.readLine()) != null) {
                blacklisted.add(line);
            }

        } catch (FileNotFoundException e) {
            throw new readException("could not find blacklist file:" + blacklistFilePath);

        } catch (IOException e) {
            throw new readException("buffered reader encountered a problem when reading file" + blacklistFilePath);
        }

        return blacklisted;

    }


    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     *
     * @param inputFilePath  The input file path.
     * @param outputFilePath The output file path.
     * @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(String inputFilePath, String outputFilePath, Map<String, String> additionaloptions) throws exportException {
        Conversation conversation = null;
        try {
            conversation = this.readConversation(inputFilePath);
        } catch (readException e) {
            throw new exportException("encountered an issue when reading input file:" + inputFilePath);
        }

        if (additionaloptions.containsKey("user")) {
            try {
                conversation.messages = filter_by_usr(conversation, additionaloptions.get("user"));
            } catch (StringNotFoundException e) {
                throw new exportException("could not find any items by user:" + additionaloptions.get("user"));
            }
        }

        if (additionaloptions.containsKey("keyword")) {
            try {
                conversation.messages = filter_by_keywrd(conversation, additionaloptions.get("keyword"));
            } catch (StringNotFoundException e) {
                throw new exportException("could not find any items by keyword:" + additionaloptions.get("keyword"));
            }
        }

        if (additionaloptions.containsKey("blacklist")) {
            try {
                List<String> blacklist = readInBlacklist(additionaloptions.get("blacklist"));
                conversation.messages = apply_blackList(conversation, blacklist);
            } catch (readException e) {
                throw new exportException("error when trying to read in blacklist");
            }

        }

        if (additionaloptions.containsKey("hidesensitiveinfo")) {
            if (additionaloptions.get("hidesensitiveinfo").equals("true")) {
                conversation.messages = hide_card_details(conversation);
                conversation.messages = hide_phone_numbers(conversation);
            } else {
                if (additionaloptions.get("hidesensitiveinfo").equals("false")) {
                    throw new exportException("hidesensitiveinfo flag must be true or false");
                }
            }

        }

        if (additionaloptions.containsKey("sorted")) {

            if (additionaloptions.get("sorted").equals("true")) {
                conversation.messages = sort_messages_by_most_active(conversation);
            } else {
                if (!additionaloptions.get("sorted").equals("false")) {
                    throw new exportException("sorted flag must be true or false");
                }
            }
        }


        try {
            this.writeConversation(conversation, outputFilePath);
        } catch (writeException e) {
            throw new exportException("could not write to json file");
        }

        // TODO: Add more logging...
        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
    }

    public String[] getWords(String message_content) {
        message_content = message_content.replaceAll("[^A-Za-z0-9]", " ");
        String[] words = message_content.split(" ");
        return words;
    }


    public Collection<Message> apply_blackList(Conversation c, List<String> blackListed) {
        for (int i = 0; i < blackListed.size(); i++) {
            String word_to_replace = blackListed.get(i);
            for (Message m : c.messages) {
                m.content = m.content.replace(word_to_replace, "*redacted*");
            }
        }

        return c.messages;
    }

    public Collection<Message> hide_card_details(Conversation c) {

        String visa = "4[0-9]{15}";
        String mastercard = "5[1-5][0-9]{14}";
        String amex = "3[47][0-9]{13}";

        for (Message m : c.messages) {
            m.content = m.content.replaceAll(visa, "*redacted*");
            m.content = m.content.replaceAll(mastercard, "*redacted");
            m.content = m.content.replaceAll(amex, "*redacted*");
        }
        return c.messages;

    }

    public Collection<Message> hide_phone_numbers(Conversation c) {
        String ukphonenumbers = "0[0-9]{7,10}";
        for (Message m : c.messages) {
            m.content = m.content.replaceAll(ukphonenumbers, "*redacted*");
        }
        return c.messages;
    }


    public Collection<Message> filter_by_usr(Conversation c, String userID) throws StringNotFoundException {

        List<Message> newMessages = new ArrayList<Message>();

        for (Message m : c.messages) {
            if (m.senderId.equals(userID)) {
                newMessages.add(m);
            }
        }
        if (newMessages.size() == 0) {
            throw new StringNotFoundException();
        }
        return newMessages;

    }

    public Collection<Message> filter_by_keywrd(Conversation c, String keyword) throws StringNotFoundException {
        List<Message> newMessages = new ArrayList<Message>();
        for (Message m : c.messages) {
            String[] words = getWords(m.content);

            boolean found = false;
            int i = 0;
            while (!found && i < words.length) {
                String currentword = words[i];
                if (currentword.equals(keyword)) {
                    found = true;
                    newMessages.add(m);
                }
                i++;

            }
        }

        if (newMessages.size() == 0) {
            throw new StringNotFoundException();
        }
        return newMessages;
    }

    public Collection<Message> sort_messages_by_most_active(Conversation c) {
        List<Message> sortedMessages = new ArrayList<Message>();

        for (int i = 0; i < c.mostactiveUsers.size(); i++) {
            String currentMostActiveID = c.mostactiveUsers.get(i).get(0);
            try {
                sortedMessages.addAll(filter_by_usr(c, currentMostActiveID));
            } catch (StringNotFoundException e) {
                e.printStackTrace();
            }


        }
        return sortedMessages;
    }


    private List<List<String>> sort_highest_to_lowest(HashMap<String, Integer> map) {
        List<List<String>> mostactive = new ArrayList<List<String>>();
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o2)).getValue())
                        .compareTo(((Map.Entry) (o1)).getValue());
            }
        });

        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            List<String> newEntry = new ArrayList<String>();
            newEntry.add(entry.getKey().toString());
            newEntry.add(entry.getValue().toString());
            mostactive.add(newEntry);
        }
        return mostactive;
    }

    public List<List<String>> getMostActiveUsers(List<Message> messages) {
        HashMap<String, Integer> userStatistics = new HashMap<>();
        for (Message m : messages) {
            if (userStatistics.containsKey(m.senderId)) {
                int updatedcount = userStatistics.get(m.senderId) + 1;
                userStatistics.put(m.senderId, updatedcount);
            } else {
                userStatistics.put(m.senderId, 1);
            }
        }
        return sort_highest_to_lowest(userStatistics);
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     *
     * @param conversation   The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */

    public void writeConversation(Conversation conversation, String outputFilePath) throws writeException {
        // TODO: Do we need both to be resources, or will buffered writer close the stream?
        try (OutputStream os = new FileOutputStream(outputFilePath, true);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

            // TODO: Maybe reuse this? Make it more testable...
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());

            Gson g = gsonBuilder.create();

            bw.write(g.toJson(conversation));
        } catch (FileNotFoundException e) {
            // TODO: Maybe include more information?
            throw new writeException("could not find json file:" + outputFilePath);
        } catch (IOException e) {
            // TODO: Should probably throw different exception to be more meaningful :/
            throw new writeException("buffered writeer error when writing to json file" + outputFilePath + " occured");
        }
    }

    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     *
     * @param inputFilePath The path to the input file.
     * @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation readConversation(String inputFilePath) throws readException {
        try (InputStream is = new FileInputStream(inputFilePath);
             BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ", 3);
                try {
                    messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
                } catch (NumberFormatException e) {
                    throw new readException("timestamp in message not of correct format:" + split[0]);
                }
            }
            return new Conversation(conversationName, messages, getMostActiveUsers(messages));

        } catch (FileNotFoundException e) {
            throw new readException("could not find json file:" + inputFilePath);
        } catch (IOException e) {
            throw new readException("buffered reader encountered a problem when reading from file:" + inputFilePath);
        }
    }

    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
