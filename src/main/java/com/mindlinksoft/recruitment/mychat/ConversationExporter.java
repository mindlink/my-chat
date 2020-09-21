package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ConversationExporter {

    public static final int FILTER_USER_SELECTION = 1;
    public static final int FILTER_BY_KEYWORD_SELECTION = 2;
    public static final int HIDE_WORDS_SELECTION = 3;
    public static final int HIDE_CC_PHONE_SELECTION = 4;
    public static final int HIDE_SENDER_ID_SELECTION = 5;
    public static final int ACTIVE_USER_REPORT_SELECTION = 6;
    public static final int IMPORT_ANOTHER_CONVO_SELECTION = 7;
    public static final int END_PROGRAM_SELECTION = 8;
    public static final int MESSAGE_FORMAT_LENGTH = 3;

    public static final int READ_WITH_NO_FLAG = 0;
    public static final int WRITE_WITH_NO_FLAG = 0;
    public static final int WRITE_ACTIVE_USER_REPORT = 1;
    public static final int REJECTED_MESSAGE = 0;

    public static final List<String> READ_WITH_NO_ARGUMENTS = new ArrayList<>();
    public static final List<String> EXPORT_WITH_NO_ARGUMENTS = new ArrayList<>();

    public static final boolean IS_CONVERSATION_FILE = true;
    public static final boolean DIRECTORYFILE = false;
    public static final boolean OVERWRITE_FILE = false;

    public static final String JSONEXTENTION = ".json";
    public static final String SENDER = "Sender";

    public static void main(String[] args) throws Exception {

        ConversationExporter exporter = new ConversationExporter();
        String sourceFileLocation = "";
        String destinationConversationLocation = "";
        Conversation conversation;

        boolean endProgram = false;
        while (!endProgram) {

            try {
                sourceFileLocation = getUsersSourceConversationFile();
                conversation = exporter.readConversation(sourceFileLocation, READ_WITH_NO_FLAG, READ_WITH_NO_ARGUMENTS);

                destinationConversationLocation = getUsersDestinationFileLocationFile();
                exporter.writeConversation(conversation, destinationConversationLocation, WRITE_WITH_NO_FLAG);
                System.out.println("Conversation exported from '" + sourceFileLocation + "' to '" + destinationConversationLocation);

            } catch (InvalidConversationFormatException | IllegalArgumentException e) {
                System.out.println("ERROR READING CONVERSATION. FORMAT OF FILE SHOULD BE :");
                System.out.println(e.getMessage());
                continue;
            }
            boolean goBackToStartOfProgram = false;

            while (!goBackToStartOfProgram) {

                presentProgramOptions();

                int userSelection = getUserSelection();
                if (userSelection == IMPORT_ANOTHER_CONVO_SELECTION) {
                    goBackToStartOfProgram = true;
                    continue;
                } else if (userSelection == END_PROGRAM_SELECTION) {
                    endProgram = true;
                    System.out.println("ENDING PROGRAM...........");
                    break;
                }
                
                String[] inputs = {sourceFileLocation, destinationConversationLocation};
                ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(inputs);
                proccessUserSelection(userSelection, configuration);

            }

        }
    }

    private static void proccessUserSelection(int selection, ConversationExporterConfiguration configuration) {

        try {

            List<String> arguments = new ArrayList<>();

            if (selection == FILTER_USER_SELECTION) {

                boolean validName = false;
                while (!validName) {
                    System.out.println("ENTER NAME OF USER YOU WANT TO HIGHLIGHT");
                    Scanner scanner = new Scanner(System.in);
                    String nameSelected = scanner.nextLine().toLowerCase();
                    if (nameSelected.equals("")) {
                        System.out.println("INVALID INPUT");
                        continue;
                    }
                    validName = true;
                    arguments.add(nameSelected);
                }

                ConversationExporter exporter = new ConversationExporter();
                exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, FILTER_USER_SELECTION, WRITE_WITH_NO_FLAG, arguments);

            } else if (selection == FILTER_BY_KEYWORD_SELECTION) {
                boolean validInputs = false;
                while (!validInputs) {
                    System.out.println("ENTER KEYWORD THAT YOU WANT TO HIGHLIGHT.");
                    Scanner scanner = new Scanner(System.in);
                    String wordFilter = scanner.nextLine().toLowerCase();

                    if (wordFilter.equals("")) {
                        System.out.println("INVALID INPUT");
                        continue;
                    }
                    validInputs = true;
                    arguments.add(wordFilter);
                }

                ConversationExporter exporter = new ConversationExporter();
                exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, FILTER_BY_KEYWORD_SELECTION, WRITE_WITH_NO_FLAG, arguments);

            } else if (selection == HIDE_WORDS_SELECTION) {
                arguments = getBlackListedWords();
                ConversationExporter exporter = new ConversationExporter();
                exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, HIDE_WORDS_SELECTION, WRITE_WITH_NO_FLAG, arguments);

            } else if (selection == HIDE_CC_PHONE_SELECTION) {
                ConversationExporter exporter = new ConversationExporter();
                exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, HIDE_CC_PHONE_SELECTION, WRITE_WITH_NO_FLAG, EXPORT_WITH_NO_ARGUMENTS);
            } else if (selection == HIDE_SENDER_ID_SELECTION) {
                ConversationExporter exporter = new ConversationExporter();
                exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, HIDE_SENDER_ID_SELECTION, WRITE_WITH_NO_FLAG, EXPORT_WITH_NO_ARGUMENTS);
            } else if (selection == ACTIVE_USER_REPORT_SELECTION) {
                ConversationExporter exporter = new ConversationExporter();
                exporter.exportConversation(configuration.inputFilePath, configuration.outputFilePath, READ_WITH_NO_FLAG, WRITE_ACTIVE_USER_REPORT, EXPORT_WITH_NO_ARGUMENTS);
            }

        } catch (InvalidConversationFormatException exception) {
            System.out.println("ERROR READING CONVERSATION FILE");
            System.out.println("PLEASE ENSURE THE CONVERSATION FORMAT MATCHES BELOW");
            System.out.println(exception.toString());
        } catch (IOException exception) {
            System.out.println("ERROR WRITING CONVERSATION FILE");
            System.out.println(exception.toString());
        } catch (Exception exception) {
            System.out.println(exception.toString());
        }

    }

    private static int getUserSelection() {

        boolean valid = false;
        int validSelection = 0;

        while (!valid) {

            try {
                Scanner scanner = new Scanner(System.in);
                String selection = scanner.nextLine();
                if (selection.equals("") | !validUserSelection(Integer.valueOf(selection))) {
                    printInvalidSelectionMessage();
                    presentProgramOptions();
                    continue;
                }
                validSelection = Integer.valueOf(selection);
                valid = true;

            } catch (NumberFormatException exception) {
                System.out.println("Only numbers allowed!");
                presentProgramOptions();
            }

        }
        return validSelection;

    }

    private static String getUsersDestinationFileLocationFile() {
        boolean validInput = false;
        String validFile = "";

        while (!validInput) {

            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter the path of your destination directory");
            System.out.println("Example - /Users/james/myfolder");
            String userInput = scanner.nextLine();

            File userDestinationPath = new File(userInput);

            if (validateSourceConversationFile(userDestinationPath, DIRECTORYFILE)) {

                String nameOfFile = getUsersNameOfFile();
                validFile = userInput + "/" + nameOfFile + JSONEXTENTION;
                validInput = true;
            }

        }

        return validFile;

    }

    public static String getUsersSourceConversationFile() {

        boolean validInput = false;
        File userConversationFilePath;
        String validFile = "";
        String userInputfile;

        while (!validInput) {

            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Please enter the file path of your converastion ");
                userInputfile = scanner.nextLine();  // Read user input
                userConversationFilePath = new File(userInputfile);

                if (validateSourceConversationFile(userConversationFilePath, IS_CONVERSATION_FILE)) {
                    validInput = true;
                    validFile = userInputfile;
                }

            } catch (NullPointerException error) { 
                System.out.println("INVALID FILE");
                continue;

            }

        }

        return validFile;
    }

    private static String getUsersNameOfFile() {

        boolean validInput = false;
        String validName = "";

        while (!validInput) {

            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter the name of your file. DO NOT INCLUDE EXTENTION");
            String userInput = scanner.nextLine();
            if (!userInput.isBlank()) {
                validName = userInput;
                validInput = true;
            }
        }
        return validName;
    }

    private static void printInvalidSelectionMessage() {
        System.out.println("SELECTION DOES NOT EXIST");
    }

    private static boolean validateSourceConversationFile(File userConversationFilePath, boolean isFileOrDirectory) {

        if (!userConversationFilePath.exists()) {

            System.err.println("PATH ENTERED DOES NOT EXIST ");
            return false;
        } else if (isFileOrDirectory == true && userConversationFilePath.isDirectory()) {
            System.err.println("PATH ENTERED IS A DIRECTORY. PLEASE SELECT A VALID FILE");
            return false;
        } else if (isFileOrDirectory == true && !userConversationFilePath.isFile()) {
            System.err.println("PATH ENTERED IS NOT A FILE ");
            return false;
        } else if (isFileOrDirectory == false && userConversationFilePath.isFile()) {
            System.err.println("YOU ENTERED A FILE. PLEASE ENTER A DIRECTORY ");
            return false;
        } else if (isFileOrDirectory == false && !userConversationFilePath.isDirectory()) {
            System.err.println("PATH ENTERED IS NOT A DIRECTORY!");
            return false;
        } else {
            System.out.println("Validating complete ");
            return true;
        }
    }

    public void exportConversation(String inputFilePath, String outputFilePath, int filterFlags, int reportFlags, List<String> arguments) throws InvalidConversationFormatException, IllegalArgumentException, IOException, Exception {

        Conversation conversation = this.readConversation(inputFilePath, filterFlags, arguments);
        this.writeConversation(conversation, outputFilePath, reportFlags);

        System.out.println("Conversation exported from '" + inputFilePath + "' to '" + outputFilePath);
    }

    public void writeConversation(Conversation conversation, String outputFilePath, int reportFlags) throws IllegalArgumentException, IOException {
        try ( OutputStream os = new FileOutputStream(outputFilePath, OVERWRITE_FILE);  BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
            Gson g = gsonBuilder.create();

            if (reportFlags == WRITE_ACTIVE_USER_REPORT) {
                Map<String, Integer> usersActivityOrder = calculateConversationActivity(conversation);
                String jsonActivity = g.toJson(usersActivityOrder);
                JsonElement jsonActivityElement = new JsonParser().parse(jsonActivity);

                String jsonConversation = g.toJson(conversation);
                JsonParser jsonParser = new JsonParser();
                JsonObject JsonConversationObject = jsonParser.parse(jsonConversation).getAsJsonObject();

                JsonConversationObject.add("Activity", jsonActivityElement);

                bw.write(g.toJson(JsonConversationObject));
                bw.close();
                return;

            }

            bw.write(g.toJson(conversation));
            bw.close();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The destination file was not found!");
        } catch (IOException e) {
            throw new IOException("Something went wrong");
        }
    }

    private Map<String, Integer> calculateConversationActivity(Conversation conversation) {

        HashMap<String, Integer> scoreTracker = new HashMap<>();

        for (Message message : conversation.messages) {

            String senderId = message.senderId.toLowerCase();

            if (!scoreTracker.containsKey(senderId)) {
                scoreTracker.put(senderId, 1);
            } else {
                int newUserScore = scoreTracker.get(senderId) + 1;
                scoreTracker.put(senderId, newUserScore);
            }
        }

        List<Map.Entry<String, Integer>> list = new LinkedList<>(scoreTracker.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {

                return o2.getValue().compareTo(o1.getValue());
            }
        });

        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {

            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;

    }

    public Conversation readConversation(String inputFilePath, int filterFlags, List<String> arguments) throws InvalidConversationFormatException, IllegalArgumentException {

        try ( InputStream is = new FileInputStream(inputFilePath);  BufferedReader r = new BufferedReader(new InputStreamReader(is))) {

            List<Message> messages = new ArrayList<>();

            String conversationName = r.readLine();
            String line;

            if (filterFlags == READ_WITH_NO_FLAG) {
                while ((line = r.readLine()) != null) {
                    
                    
                    String[] split = line.split(" ", MESSAGE_FORMAT_LENGTH);

                    String timestamp = split[0];
                    String name = split[1];
                    String message = split[2];
                    messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(split[0])), split[1], split[2]));
                }
            } else if (filterFlags == HIDE_SENDER_ID_SELECTION) {

                HashMap<String, String> nameStore = new HashMap<>();

                while ((line = r.readLine()) != null) {
                    String[] split = line.split(" ", MESSAGE_FORMAT_LENGTH);

                    String timestamp = split[0];
                    String name = split[1].toLowerCase();
                    String message = split[2];

                    if (!nameStore.containsKey(name)) {
                        nameStore.put(name, SENDER + String.valueOf(nameStore.size() + 1));

                        String alteredSenderId = nameStore.get(name);
                        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(timestamp)), alteredSenderId, message));
                    } else {
                        String retrievedSenderId = nameStore.get(name);
                        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(timestamp)), retrievedSenderId, message));
                    }

                }
            } else {

                while ((line = r.readLine()) != null) {
                    String[] split = line.split(" ", MESSAGE_FORMAT_LENGTH);

                    String timestamp = split[0];
                    String name = split[1];
                    String message = split[2];

                    List<String> processedMessage = filterConversation(timestamp, name, message, filterFlags, arguments);

                    if (processedMessage.size() != REJECTED_MESSAGE) {
                        messages.add(new Message(Instant.ofEpochSecond(Long.parseUnsignedLong(processedMessage.get(0))), processedMessage.get(1), processedMessage.get(2)));
                    }
                }

            }
            return new Conversation(conversationName, messages);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The file read was not found.");
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            throw new InvalidConversationFormatException();
        }
    }

    List<String> filterConversation(String timestamp, String name, String message, int filterFlags, List<String> arguments) {

        List<String> returningMessage = new ArrayList<>();

        if (filterFlags == FILTER_USER_SELECTION) {
            String filteredNameSelection = arguments.get(0).toLowerCase();
            if (name.toLowerCase().equals(filteredNameSelection)) {
                returningMessage.add(timestamp);
                returningMessage.add(name);
                returningMessage.add(message);
                return returningMessage;
            }
        } else if (filterFlags == FILTER_BY_KEYWORD_SELECTION) {
            String filterKeyword = arguments.get(0).toLowerCase();
            if (message.toLowerCase().contains(filterKeyword)) {
                returningMessage.add(timestamp);
                returningMessage.add(name);
                returningMessage.add(message);
                return returningMessage;
            }
        } else if (filterFlags == HIDE_WORDS_SELECTION) {

            String newMessage = message;

            for (String blackListWord : arguments) {
                if (message.toLowerCase().contains(blackListWord)) {
                    newMessage = newMessage.replaceAll(blackListWord, "**redacted**");
                }
            }

            returningMessage.add(timestamp);
            returningMessage.add(name);
            returningMessage.add(newMessage);

            return returningMessage;

        } else if (filterFlags == HIDE_CC_PHONE_SELECTION) {
            String proccessedMessage = filterCreditAndPhoneNumbers(message);
            returningMessage.add(timestamp);
            returningMessage.add(name);
            returningMessage.add(proccessedMessage);
            return returningMessage;

        }

        return returningMessage;
    }

    private String filterCreditAndPhoneNumbers(String conversationMessage) {

        // +447222555555
        // +44 7222 555 555
        // (0722) 5555555
        String phoneNumRegEx = "^(((\\+44\\s?\\d{4}|\\(?0\\d{4}\\)?)\\"
                + "s?\\d{3}\\s?\\d{3})|((\\+44\\s?\\d{3}|\\(?0\\d{3}\\)?)\\s?\\d{3}\\s?\\d{4})|"
                + "((\\+44\\s?\\d{2}|\\(?0\\d{2}\\)?)\\s?\\d{4}\\s?\\d{4}))(\\s?\\#(\\d{4}|\\d{3}))?$|"
                + "\\d{16}|\\d{12}";

        Pattern PATTERN = Pattern.compile(phoneNumRegEx);

        String strippedMessage = conversationMessage;

        if (conversationMessage.contains("-")) {
            strippedMessage = conversationMessage.replaceAll("-", "");
        }

        Matcher matcher = PATTERN.matcher(strippedMessage);
        StringBuilder stringBuilder = new StringBuilder();
        String replacementMessaeg = "**redacted**";

        boolean found = false;

        while (matcher.find()) {
            found = true;
            matcher.appendReplacement(stringBuilder, replacementMessaeg);
        }

        matcher.appendTail(stringBuilder);

        if (found) {
            return stringBuilder.toString();

        }

        return conversationMessage;

    }

    private static ArrayList<String> getBlackListedWords() {

        ArrayList<String> blacklistedWords = new ArrayList<>();
        boolean finished = false;

        while (!finished) {

            System.out.println("ENTER WORD THAT YOU WANT TO FILTER OUTTT.");
            System.out.println("PRESS ENTER WHEN YOUR FINISHED");

            Scanner scanner = new Scanner(System.in);
            String wordFilter = scanner.nextLine();

            if (wordFilter.equals("") & blacklistedWords.isEmpty()) {
                continue;
            }

            if (wordFilter.equals("") & !blacklistedWords.isEmpty()) {
                break;
            }

            blacklistedWords.add(wordFilter.toLowerCase());
        }

        return blacklistedWords;
    }

    private static boolean validUserSelection(int userSelection) {

        boolean valid = false;

        switch (userSelection) {
            case FILTER_USER_SELECTION:
                valid = true;
                break;
            case FILTER_BY_KEYWORD_SELECTION:
                valid = true;
                break;
            case HIDE_WORDS_SELECTION:
                valid = true;
                break;
            case HIDE_CC_PHONE_SELECTION:
                valid = true;
                break;
            case HIDE_SENDER_ID_SELECTION:
                valid = true;
                break;
            case ACTIVE_USER_REPORT_SELECTION:
                valid = true;
                break;
            case IMPORT_ANOTHER_CONVO_SELECTION:
                valid = true;
                break;
            case END_PROGRAM_SELECTION:
                valid = true;
                break;

            default:
                break;
        }

        return valid;

    }

    private static void presentProgramOptions() {
        System.out.println("Please select an option below");
        System.out.println("1---------------- FILTER BY USER                   ----------------");
        System.out.println("2---------------- FILTER BY KEYWORD                ----------------");
        System.out.println("3---------------- HIDE SPECIFIC WORDS              ----------------");
        System.out.println("4---------------- HIDE CREDIT CARD & PHONE NUMBERS ----------------");
        System.out.println("5---------------- HIDE USER ID'S                   ----------------");
        System.out.println("6---------------- REPORT FOR THE MOST ACTIVE USERS ----------------");
        System.out.println("7---------------- IMPORT ANOTHER CONVERATION       ----------------");
        System.out.println("8---------------- END PROGRAM                      ----------------");
    }

    class InstantSerializer implements JsonSerializer<Instant> {

        @Override
        public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(instant.getEpochSecond());
        }
    }
}
