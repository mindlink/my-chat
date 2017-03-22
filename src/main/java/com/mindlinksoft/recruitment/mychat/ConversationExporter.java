package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import com.sun.org.apache.xml.internal.security.encryption.EncryptionMethod;
import org.apache.commons.cli.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Represents a conversation exporter that can read a conversation and write it out in JSON.
 */
public class ConversationExporter {


    private static final Logger logger = Logger
            .getAnonymousLogger();

    private static final boolean hideNumbers = false;
    private static final boolean obfuscate = false;

    /**
     * The application entry point.
     * @param args The command line arguments.
     * @throws Exception Thrown when something bad happens.
     */
    public static void main(String[] args) throws IOException {
        ConversationExporter exporter = new ConversationExporter();
        // ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        // Deprecating this. Using apache commons-cli to parse the cli args
        CLIExtractor CLIExtractor = new CLIExtractor(args).invoke();
        if (CLIExtractor.noValidArgs()) return;
        CommandLine cmd = CLIExtractor.getCmd();

        String inputFilePath = cmd.getOptionValue("input");
        String outputFilePath = cmd.getOptionValue("output");
        String keyword = cmd.getOptionValue("keyword");
        String user = cmd.getOptionValue("user");
        String[] blacklist = cmd.getOptionValues("blacklist");

        exporter.exportConversation(inputFilePath, outputFilePath, keyword, user, blacklist);
    }

    /**
     * Exports the conversation at {@code inputFilePath} as JSON to {@code outputFilePath}.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     * @param keyword
     *@param user
     * @param blacklist @throws Exception Thrown when something bad happens.
     */
    public void exportConversation(String inputFilePath, String outputFilePath, String keyword, String user, String[] blacklist) throws IOException {
        Conversation conversation;
        try {
            conversation = this.readConversation(inputFilePath, keyword, user, blacklist);
        } catch (IOException e) {
            throw new IOException("Something went wrong while reading the input file...");
        }

        String messageLog = "Trying to export " + conversation.name + " from '" + inputFilePath + "' to '" + outputFilePath + "'\nHere is the input:\n";

        for (Message message : this.readConversation(inputFilePath, null, null, null).messages) {
            messageLog += message.senderId + " " + message.content + "\n";
        }
        logger.info(messageLog);

        this.writeConversation(conversation, outputFilePath);
    }

    /**
     * Helper method to write the given {@code conversation} as JSON to the given {@code outputFilePath}.
     * @param conversation The conversation to write.
     * @param outputFilePath The file path where the conversation should be written.
     * @throws Exception Thrown when something bad happens.
     */
    public void writeConversation(Conversation conversation, String outputFilePath) {
        // The PrintStream will automatically close when done.
        try (PrintWriter out = new PrintWriter( outputFilePath )) {
            String json = getJsonConversation(conversation);
            logger.info("Writing Json output:\n" + json);
            out.println(json);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Please provide a correct output file path in the command line arguments");
        }
    }

    private String getJsonConversation(Conversation conversation) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
        Map<String, Long> usersMap =
                conversation.messages.stream().collect(Collectors.groupingBy(e -> e.senderId, Collectors.counting()));
        Map<String, Long> sortedUsersMap =
                usersMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        Gson g = gsonBuilder.setPrettyPrinting().create(); // Use pretty print to make json output a bit readable
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("Conversation", g.toJsonTree(conversation));
        jsonObject.add("Activity Report", g.toJsonTree(sortedUsersMap));
        String json = g.toJson(jsonObject);
        return json;
    }

    /**
     * Represents a helper to read a conversation from the given {@code inputFilePath}.
     * @param inputFilePath The path to the input file.
     * @param keyword
     *@param user
     * @param blacklist @return The {@link Conversation} representing by the input file.
     * @throws Exception Thrown when something bad happens.
     */
    public Conversation readConversation(String inputFilePath, String keyword, String user, String[] blacklist) throws IOException {
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<Message>();

            String conversationName = r.readLine();
            String line;

            while ((line = r.readLine()) != null) {
                String[] split = line.split(" ",3); // We limit the split array to 3 strings in order to get the whole message.
                                                    // There was a bug and only the first word of the message was exported.
                String message = split[2];
                if (blacklist != null && blacklist.length > 0) {
                    for (String badWord : blacklist) {
                        message = message.replaceAll("(?i)" + "\\b" + badWord + "\\b","******");
                    }
                }

              if (keyword != null)
                    if (!Pattern.compile("\\b" + Pattern.quote(keyword) + "\\b", Pattern.CASE_INSENSITIVE).matcher(message).find()) continue;
                // Here we assume that we do not output messages that contain words in the blacklist
                // and defined as keywords. These words are already ******

                if (user !=null)
                    if (!user.contentEquals(split[1])) continue;


                if (hideNumbers) {
                    String[] words = message.split("\\s+");
                    for (int i = 0 ; i < words.length ; i++) {
                        if (words[i].matches("\\d+") && Luhn.checkValidCC(words[i]) || checkPhoneNo(words[i])) {
                            message = message.replace(words[i] , "**********");
                        }
                    } // Hide Credit Card and Phone numbers
                }

                if (obfuscate) {
                    String userOb = "";
                    for (char c : split[1].toCharArray())
                        userOb += c + 25; //Simple obfuscation method
                    split[1] = userOb;
                }

                messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], message));
            }

            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Please provide a correct input file path in the command line arguments");
        } catch (IOException e) {
            throw new IOException("Something went wrong while reading the input file...");
        }
    }

    private boolean checkPhoneNo (String phoneNo) {
        Pattern pattern = Pattern.compile("\\d{3}-\\d{7}"); // checks for the XXX-XXXXXXX phone number format, e.g. 210-1234567
        Matcher matcher = pattern.matcher(phoneNo);

        if (matcher.matches())
            return true;
        return false;
    }

    class InstantSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }

    static class CLIExtractor {
        private boolean myResult;
        private String[] args;
        private CommandLine cmd;

        public CLIExtractor(String... args) {
            this.args = args;
        }

        boolean noValidArgs() {
            return myResult;
        }

        public CommandLine getCmd() {
            return cmd;
        }

        public CLIExtractor invoke() {
            Options options = new Options();
            Option input = new Option("i", "input", true, "input file path");
            input.setRequired(true);
            options.addOption(input);

            Option output = new Option("o", "output", true, "output file path");
            output.setRequired(true);
            options.addOption(output);

            Option user = new Option("u", "user", true, "filter by user");
            options.addOption(user);

            Option keyword = new Option("k", "keyword", true, "filter by keyword");
            options.addOption(keyword);

            Option blacklist = new Option("b", "blacklist", true, "hide blacklisted words");
            options.addOption(blacklist);

            CommandLineParser parser = new DefaultParser();
            HelpFormatter formatter = new HelpFormatter();

            try {
                cmd = parser.parse(options, args);
            } catch (ParseException e) {
                System.out.println(e.getMessage());
                formatter.printHelp("Mindlink's Chat", options);

                System.exit(1);
                myResult = true;
                return this;
            }
            myResult = false;
            return this;
        }
    }
}
