package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.stream.Stream;

import static com.mindlinksoft.recruitment.mychat.ConversationExporter.exportConversation;
import static org.junit.Assert.*;

/*
 * 15.10.2020
 * CURRENT TESTED FUNCTIONALITY: As specified below in each test.
 * LIMITATIONS: As specified in beginning of each section
 *
 */

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests {

    //                                          -----UNIT TESTS-----


/**
 *      LIMITATIONS OF UNIT TESTS:
 *      1) {@testUserFilterer} checks if no other user exists in the filtered conversation but the one we
 *          filter by. However, it does not check if all the messages of the user we are filtering by
 *          have been added to the filtered conversation.
 *      2) {@testKeywordFilterer} checks if all messages in the filtered conversation contain
 *          the keyword we are filtering by. However, it does not check yet if all the messages
 *          with the keyword in them have been added to the filtered conversation.
 *      3) {@testWordRedactor} checks that the words of the blackList are nowhere to be found in the processed
 *          conversation. The way it checks is using the String.contains(str) method which checks if the string
 *          contains the str given. The problem with this is that some words are contained in others. To give an
 *          example... the article 'a' is also the letter 'a' and hence is contained in many other words. The String
 *          method .contains() will match all 'a' in the string instead of just the ones that are surrounded by
 *          white spaces or special characters and not alphanumericals.
 *      4) {@testWordRedactor } Checks if redacted words have been removed.
 *          However, it does not check if the rest of the text has remained the same.
 *      5) Case SenSitIviTy has not been investigated thoroughly in any function.
 *
 * Note: Most of the above limitations are fortunately easy to solve. For instance, for nubmer 3 we just have to
 *       find the correct regular expression and match it. However, due to time constraints I left it for future work.
 *
 */


    /**
        The following 2 fields are resources for random generation of conversations
     */
    private static ArrayList<String> sendersList = new ArrayList<String>() {
        {
            add("bob");
            add("mike");
            add("angus");
            add("eggs");
            add("spam");
            add("incognito");
            add("samantha");
            add("mikey");
            add("maria");
            add("scorsese");
            add("boris");
            add("maicl");
    }
};

    private static ArrayList<String> messageList = new ArrayList<String>(){
        {
            add("Hello there!");
            add( "how are you?");
            add("I'm good thanks, do you like pie?");
            add( "no, let me ask Angus...");
            add( "Hell yes! Are we buying some pie?");
            add("No, just want to know if there's anybody else in the pie society...");
            add( "YES! I'm the head pie eater there...  ");
            add("Hey guys, we might need to make this chat a bit more interesting.");
            add("Like, how?");
            add("You know, get some special characters and emojis going on");
            add("Oh, I know what this guy means :) :P");
            add( "Yeap, I'm on it !!!??? !\\\"£$%^&*()(*&^%$£\\\"");
            add( "Guys, please `````");
            add( "Anonymity is bliss");
            add("Can the admin please kick him out?");
            add( "How can I do that?");
            add( "Just google it dude");
            add( "Guys do you like my new haircut?");
            add( "Nice one!");
            add( "Okay, found it. I kicked him out...");
            add("He he he, I'm back");
            add("Nevermind, he is a hacker :(");
            add("I am leaving the chat now!");

        }
    };

    /**
     * Words used for random generation of blackLists and words to be redacted
     */
    private static ArrayList<String> wordList = new ArrayList<String>(){
        {
            add("pie");
            add("doesntexist");
            add("yes");
            add("hello");
            add("I'm");
            add("club");
            add("thanks");
            add("know");
        }
    };

    /**
     * Function that generates random conversations using the resources in the {@code sendersList} and {@code messageList}
     */
    private static Conversation randomConversationGenerator(Integer conversationLength){

        Conversation conversation = new Conversation(  "Random Conversation for testing");

        Integer n1 = sendersList.size();
        Integer n2 = messageList.size();

        Random ran1 = new Random();
        int x1 = ran1.nextInt(n1);
        Random ran2 = new Random();
        int x2 = ran2.nextInt(n2);

        for (int i = 0; i < conversationLength; i++){
            Message tmpMessage = new Message(sendersList.get(x1),messageList.get(x2));
            conversation.messages.add(tmpMessage);
            x1 = ran1.nextInt(n1);
            x2 = ran2.nextInt(n2);
        }
        return conversation;
    }



    //TEST 1: user-filtering test

    /**
     * Function: Random Generation of the stream of arguments for feeding to the test function
     *
     * Argument format: Conversation, String
     *
     * @return Stream of arguments
     */
    private static Stream<Arguments> generateUserFiltererTestInputs() {

        int numberOfTestInputs = 20;

        //Random conversation lengths
        Random randomLength = new Random();
        int length = randomLength.nextInt(20);

        //Random user name chosen
        Random randomUser = new Random();
        int sendersListLength = getSendersListLength();
        String userName = sendersList.get(randomUser.nextInt(sendersListLength));


        ArrayList <Arguments> args = new ArrayList<Arguments>();

        for (int i = 0; i < numberOfTestInputs; i++){
            Conversation conv = randomConversationGenerator(length);
            args.add(Arguments.arguments(conv, userName));
            //Get next random length and userName
            length = randomLength.nextInt(10);
            userName = sendersList.get(randomUser.nextInt(sendersListLength));
        }

        return args.stream();
    };

    /** TEST 1: Property-based test
     * Property checked:
     *          Absence of other user's messages other than the filtered user's messages
     * Input:
     *          Stream of randomly generated conversations and users.
     */
    @ParameterizedTest
    @MethodSource("generateUserFiltererTestInputs")
    public void testUserFilterer(Conversation conversation, String userName) throws Exception{

        UserFilterer userFilterer = new UserFilterer(conversation, userName);
        userFilterer.process();
        Conversation filteredConversation = userFilterer.getProcessedConversation();

        //search for other users in conversation
        Boolean otherUsersExist = false;
        Collection<Message> filteredMessages = filteredConversation.messages;

        for(Message msg : filteredMessages){
            if(msg.senderId != userName){
                otherUsersExist = true;
                break;
            }
        }

        assertFalse(otherUsersExist);

        Conversation userFilterComplement = new Conversation(filteredConversation.name);

    }



    //TEST 2: keyword-filtering test

    /**
     * Function: Random Generation of the stream of arguments for feeding to the test function
     *
     * Argument format: Conversation, String
     *
     * @return Stream of arguments
     */
    private static Stream<Arguments> generateKeywordFiltererTestInputs() {

        int numberOfTestInputs = 100;

        //Random conversation lengths
        Random randomLength = new Random();
        int length = randomLength.nextInt(20);


        //Random keyword chosen
        Random randomKeyword = new Random();
        int wordListLength = getWordListLength();
        String keyword = wordList.get(randomKeyword.nextInt(wordListLength));


        ArrayList <Arguments> args = new ArrayList<Arguments>();

        for (int i = 0; i < numberOfTestInputs; i++){
            Conversation conversation = randomConversationGenerator(length);
            args.add(Arguments.arguments(conversation, keyword));
            length = randomLength.nextInt(10);
            keyword = wordList.get(randomKeyword.nextInt(wordListLength));
        }

        return args.stream();
    };

    /** TEST 2: Property-based test
     * Property checked:
     *          Existence of the keyword given in all messages of the filtered conversation
     *
     * Input:
     *          Stream of randomly generated conversations and keywords.
     */
    @ParameterizedTest
    @MethodSource("generateKeywordFiltererTestInputs")
    public void testKeywordFilterer(Conversation conversation, String keyword) throws Exception{

        KeywordFilterer keywordFilterer = new KeywordFilterer(conversation,keyword);
        keywordFilterer.process();
        Conversation filteredConversation = keywordFilterer.getProcessedConversation();

        //search for other users in conversation
        Boolean keywordExists = true;
        Collection<Message> filteredMessages = filteredConversation.messages;

        for(Message msg : filteredMessages){
            if(!msg.content.contains(keyword)){
                keywordExists = false;
                break;
            }
        }
        if(!keywordExists){
            String foo;
        }
        assertTrue(keywordExists);
    }


    //TEST 3: word-redacting test

    /**
     * Function: Random Generation of the stream of arguments for feeding to the test function
     *
     * Argument format: Conversation, String[]
     *
     * @return Stream of arguments
     */

    private static Stream<Arguments> generateWordRedactorTestInputs() {

        int numberOfTestInputs = 50;

        //Random conversation lengths
        Random randomLength = new Random();
        int length = randomLength.nextInt(20);

        //Random word chosen
        Random randomWord = new Random();
        int wordListLength = getWordListLength();
        //3 words in blacklist for test purposes
        String[] blackList = new String[] {
                wordList.get(randomWord.nextInt(wordListLength)),
                wordList.get(randomWord.nextInt(wordListLength)),
                wordList.get(randomWord.nextInt(wordListLength))
        };

        ArrayList <Arguments> args = new ArrayList<Arguments>();

        for (int i = 0; i < numberOfTestInputs; i++){
            Conversation conversation = randomConversationGenerator(length);
            args.add(Arguments.arguments(conversation, blackList));
            length = randomLength.nextInt(10);
            blackList = new String[] {
                    wordList.get(randomWord.nextInt(wordListLength)),
                    wordList.get(randomWord.nextInt(wordListLength)),
                    wordList.get(randomWord.nextInt(wordListLength))
            };
        }


        return args.stream();
    };


    /** TEST 3: Property-based test
     * Property checked:
     *          Absence of the words in given blacklist in all messages of the filtered conversation
     *
     * Input:
     *          Stream of randomly generated conversations and blacklists.
     */

    @ParameterizedTest
    @MethodSource("generateWordRedactorTestInputs")
    public void testWordRedactor(Conversation conversation, String[] blackList) throws Exception{

        WordRedactor wordRedactor = new WordRedactor(conversation,blackList);
        wordRedactor.process();
        Conversation filteredConversation = wordRedactor.getProcessedConversation();

        //search for other users in conversation
        Boolean wordExists = false;
        Collection<Message> filteredMessages = filteredConversation.messages;


        for (Message msg : filteredMessages) {
            for(String word : blackList) {
                Boolean inString = Arrays.asList(msg.content.split(" ")).contains(word);
                if (msg.content.contains(word)) {
                    wordExists = true;
                    break;
                }
            }
        }
        assertFalse(wordExists);
    }


    //TEST 3: word-redacting test

    /**
     * Function: Random Generation of the stream of arguments for feeding to the test function
     *
     * Argument format: Conversation
     *
     * @return Stream of arguments
     */

    private static Stream<Arguments> generateReporterTestInputs() {

        int numberOfTestInputs = 25;

        //Random conversation lengths
        Random randomLength = new Random();
        int length = randomLength.nextInt(20);

        ArrayList <Arguments> args = new ArrayList<Arguments>();

        for (int i = 0; i < numberOfTestInputs; i++){
            Conversation conversation = randomConversationGenerator(length);
            args.add(Arguments.arguments(conversation));
            length = randomLength.nextInt(10);
        }


        return args.stream();
    };

    /** TEST 4: Property-based test
     * Property checked:
     *          Sum of frequencies of all users in report is equal to length of the initial conversation
     *          In other words: All messages have been counted and reported.
     *
     *
     * Input:
     *          Stream of randomly generated conversations
     */
    @ParameterizedTest
    @MethodSource("generateReporterTestInputs")
    public void testReporter(Conversation conversation) throws Exception{

        Reporter reporter = new Reporter(conversation);
        reporter.generateReport();
        Report report = reporter.getReport();

        Integer sum = 0;
        for (UserActivity metric : report.getUserActivityMetric()){
            sum += metric.getCount();
        }

        Integer len = conversation.messages.size();
        Assert.assertEquals(sum,len);
    }




    /**                                 ----INTEGRATION TESTS FOLLOWING----
     */


    //First set of tests (property-based)

    /**
     * {@code generateSimpleExportConfigs}
     *
     * Helper function generating arguments for parameterized testing of the function {@code testOneToOnePropertyOfExporter}
     * The arguments are configurations that contain the paths of input and output files with different conversations in them
     * Hence, the program will be tested against different input files
     *
     * @return a stream of arguments to pass to the testing function
     */
    private static Stream<Arguments> generateSimpleExportConfigs() {
        //First test argument
        String iFilePath = "simpleExportTestIn1.txt";
        ConversationExporterConfiguration simpleExport1 = new ConversationExporterConfiguration();
        simpleExport1.setIO(iFilePath, "simpleExportTestOut1.json");

        //Second test argument
        iFilePath = "simpleExportTestIn2.txt";
        ConversationExporterConfiguration simpleExport2 = new ConversationExporterConfiguration();
        simpleExport2.setIO(iFilePath, "simpleExportTestOut2.json");

        //Third test argument
        iFilePath = "simpleExportTestIn3.txt";
        ConversationExporterConfiguration simpleExport3 = new ConversationExporterConfiguration();
        simpleExport3.setIO(iFilePath, "simpleExportTestOut3.json");

        return Stream.of(
                Arguments.arguments(simpleExport1),
                Arguments.arguments(simpleExport2),
                Arguments.arguments(simpleExport3)
        );
    };

    /**
     * PROPERTY-BASED-TEST: Apply business, then apply inverse. Check if you get what you started with.
     * This function tests ONLY the basic exporting functionality (no filtering or report)
     *
     * CASES TESTED:
     *              - Normal Input conversation as given in chat.txt
     *              - Input containing white spaces and new lines at the
     *                beginning, the end of messages or the conversation as a whole
     *              - Input contains all special characters existing on the keyboard
     *
     * Consequently, this test does not demonstrate end-to-end robustness.
     * The program follows this path: input-> preprocessed input -> business-> output
     * The test follows this path: input -> preprocessed input -> business -> output -> inverse business -> preprocessed input
     * Then it compares if the two preprocessed inputs match in the sequence.
     *
     * Hence, It never reaches the original input. Hence, any error in the preprocessor function
     * would not show up, since we never use its inverse to prove unity.
     *
     * Conclusion: This test tests end-to-end validity of: preprocessed input -> business -> output
     *
     * Future work: Test the preprocessed input function to complete end-to-end robustness.
     *
     *
     * @param configs The stream of arguments to test against
     * @throws Exception
     */

    @ParameterizedTest
    @MethodSource("generateSimpleExportConfigs")
    public void testOneToOnePropertyOfExporter(ConversationExporterConfiguration configs) throws Exception {

        exportConversation(configs);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        Gson g = builder.create();


        String iFilePath = configs.getInputFilePath();
        String oFilePath = configs.getOutputFilePath();
        ObjectToExport exp = g.fromJson(new InputStreamReader(new FileInputStream(oFilePath)), ObjectToExport.class);
        Conversation c = exp.getConversationToExport();

        String convStr = new String();
        convStr = convStr.concat(c.name + "\n");

        for(Message msg:c.messages){
            convStr = convStr.concat(msg.timestamp.getEpochSecond() + " " + msg.senderId + " " + msg.content + "\n");
        }

        ConversationExporterConfiguration tempConfig = new ConversationExporterConfiguration();
        tempConfig.setOutputFilePath(oFilePath);
        IO writer = new IO (tempConfig);
        writer.writeToFile(convStr);

        File f1 = new File(preprocessInputFile(iFilePath));
        File f2 = new File(oFilePath);
        boolean areSame = FileUtils.contentEqualsIgnoreEOL(f1,f2,"utf-8");
        Assert.assertTrue(areSame);
    }



    /**
     * {@code generateFeatureConfigs}
     *
     * Helper function generating arguments for parameterized testing of the function {@code unitTestFeaturesOfExporter}
     *
     * Arguments: Configurations that contain the paths of input and output files with different conversations in them
     *            as well as filtering and report configurations
     *
     * Hence, the program will be tested against different input files and filtering/report configurations
     *
     * @return a stream of arguments to pass to the testing function
     */

    private static Stream<Arguments> generateFeatureConfigs() {
        //Single existing user filtering
        String iFilePath = "featureTestIn1.txt";
        ConversationExporterConfiguration simpleExport1 = new ConversationExporterConfiguration();
        simpleExport1.setIO(iFilePath, "featureTestOut1.json");
        simpleExport1.setUser("bob");

        //Single non-existing user filtering
        iFilePath = "featureTestIn2.txt";
        ConversationExporterConfiguration simpleExport2 = new ConversationExporterConfiguration();
        simpleExport2.setIO(iFilePath, "featureTestOut2.json");
        simpleExport2.setUser("me");


        //Single existing keyword
        iFilePath = "featureTestIn3.txt";
        ConversationExporterConfiguration simpleExport3 = new ConversationExporterConfiguration();
        simpleExport3.setIO(iFilePath, "featureTestOut3.json");
        simpleExport3.setKeyword("pie");

        //Single non-existing keyword
        iFilePath = "featureTestIn4.txt";
        ConversationExporterConfiguration simpleExport4 = new ConversationExporterConfiguration();
        simpleExport4.setIO(iFilePath, "featureTestOut4.json");
        simpleExport4.setKeyword("ExpectoPatronum");

        //Fifth test argument
        iFilePath = "featureTestIn5.txt";
        ConversationExporterConfiguration simpleExport5 = new ConversationExporterConfiguration();
        simpleExport5.setIO(iFilePath, "featureTestOut5.json");
        simpleExport5.setFilters(null, null, new String[]{"pie"},null);

        //Blacklist of more than one items, all existing in conversation
        iFilePath = "featureTestIn6.txt";
        ConversationExporterConfiguration simpleExport6 = new ConversationExporterConfiguration();
        simpleExport6.setIO(iFilePath, "featureTestOut6.json");
        simpleExport6.setFilters(null, null, new String[]{"pie", "Hello"},null);

        //Combination of: single existing user, single existing keyword and blacklist of one item
        iFilePath = "featureTestIn7.txt";
        ConversationExporterConfiguration simpleExport7 = new ConversationExporterConfiguration();
        simpleExport7.setIO(iFilePath, "featureTestOut7.json");
        simpleExport7.setFilters("bob", "pie", new String[]{"pie"},null);

        //Report requested, no filtering
        iFilePath = "featureTestIn8.txt";
        ConversationExporterConfiguration simpleExport8 = new ConversationExporterConfiguration();
        simpleExport8.setIO(iFilePath, "featureTestOut8.json");
        simpleExport8.setFilters(null, null, null,true);

        //Combination of: single existing user, single existing keyword and blacklist of one item
        //Report requested
        iFilePath = "featureTestIn9.txt";
        ConversationExporterConfiguration simpleExport9 = new ConversationExporterConfiguration();
        simpleExport9.setIO(iFilePath, "featureTestOut9.json");
        simpleExport9.setFilters("bob", "pie", new String[]{"pie"},true);


        return Stream.of(
                Arguments.arguments(simpleExport1),
                Arguments.arguments(simpleExport2),
                Arguments.arguments(simpleExport3),
                Arguments.arguments(simpleExport4),
                Arguments.arguments(simpleExport5),
                Arguments.arguments(simpleExport6),
                Arguments.arguments(simpleExport7),
                Arguments.arguments(simpleExport8),
                Arguments.arguments(simpleExport9)
        );
    };

    /**
     * {@code unitTestFeaturesOfExporter}
     *
     * SUPERVISED-TESTS:
     *             Give different inputs to program.
     *             Author generates expected solutions.
     *             Check if same.
     *
     * CASES TESTED:
     *              No report requested:
     *              - Single existing user filtering
     *              - Single non-existing user filtering
     *              - Single existing keyword
     *              - Single non-existing keyword
     *              - Blacklist of more than one items, all existing in conversation
     *              - Combination of: single existing user, single existing keyword and blacklist of one item
     *              Report requested:
     *              - no filtering, original conversation
     *              - Combination of: single existing user, single existing keyword and blacklist of one item
     *
     * @param configs The stream of arguments to test against.
     *
     */

    @ParameterizedTest
    @MethodSource("generateFeatureConfigs")
    public void unitTestFeaturesOfExporter(ConversationExporterConfiguration configs) throws Exception {

        exportConversation(configs);


        String oFilePath = configs.getOutputFilePath();
        String expectedOutputFilePath = "Expected_" + oFilePath;


        File f1 = new File(oFilePath);
        File f2 = new File(expectedOutputFilePath);
        boolean areSame = FileUtils.contentEqualsIgnoreEOL(f1,f2,"utf-8");
        Assert.assertTrue(areSame);

        System.out.println("Feature unit test passed successfully!");
    }


    /**                                 ///     MISCELLANEOUS   ///
     */

    /**
     * Represents a helper to preprocess the given input file from {@code inputFilePath}.
     * It trims white spaces and new lines from start and end of each line and removes lines without any characters
     *
     * @param inputFilePath The path to the input file.
     * @throws Exception Thrown when IO processes fail.
     * @return trimmed file path
     */
    protected static String preprocessInputFile(String inputFilePath) throws Exception {

        /*Take line from input file, trim it, write it in output file
         * input file: inputFilePath.txt
         * output file: preprocessedinputFilePath.txt (i.e. an intermediary file)
         */
        String preprocessedFilePath = "preprocessed" + inputFilePath + ".txt";
        try(InputStream is = new FileInputStream(inputFilePath);
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            OutputStream os = new FileOutputStream(preprocessedFilePath, false);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {

            String line = new String();
            while (((line = r.readLine()) != null)) {

                //Remove white spaces from start and end of string
                line = StringUtils.strip(line);
                if(!line.equals("")) {
                    bw.write(line + "\n");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("The input file was not found.");
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("The reading/writing operation failed on the found file.");
        }

        return preprocessedFilePath;
    }
    class InstantDeserializer implements JsonDeserializer<Instant> {

        @Override
        public Instant deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (!jsonElement.isJsonPrimitive()) {
                throw new JsonParseException("Expected instant represented as JSON number, but no primitive found.");
            }

            JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();

            if (!jsonPrimitive.isNumber()) {
                throw new JsonParseException("Expected instant represented as JSON number, but different primitive found.");
            }

            return Instant.ofEpochSecond(jsonPrimitive.getAsLong());
        }
    }


    public static Integer getSendersListLength() {
        return sendersList.size();
    }

    public static Integer getWordListLength() {
        return wordList.size();
    }

}