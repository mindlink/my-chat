package com.mindlinksoft.recruitment.mychat;

import com.google.gson.*;
import org.junit.Assert;

import java.io.FileInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/*  07.10.2020
    CURRENT TESTED FUNCTIONALITY: As specified below in each test set.
 */

/**
 * Tests for the {@link ConversationExporter}.
  */

public class ConversationExporterTests extends Utilities{

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
     *                beginning and end of messages or the conversation as a whole
     *              - Input contains all special characters existing on the keyboard
     *
     * Note: This test revealed a bug. When there are blank spaces before the first or after the last character
     * of a message, String.split(" ") will ignore them, since it delimits at " ".
     * This has been corrected by preprocessing the input file to trim its start and end from white spaces and newlines.
     *
     * Consequently, this test does not demonstrate any more end-to-end robustness. Why?
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
        ObjectToBeExported exp = g.fromJson(new InputStreamReader(new FileInputStream(oFilePath)), ObjectToBeExported.class);
        Conversation c = exp.getConversationToExport();

        String convStr = new String();
        convStr = convStr.concat(c.name + "\n");

        for(Message msg:c.messages){
            convStr = convStr.concat(msg.timestamp.getEpochSecond() + " " + msg.senderId + " " + msg.content + "\n");
        }

        writeToFile(convStr, oFilePath);

        File f1 = new File(preprocessInputFile(iFilePath));
        File f2 = new File(oFilePath);
        boolean areSame = FileUtils.contentEqualsIgnoreEOL(f1,f2,"utf-8");
        Assert.assertTrue(areSame);

        System.out.println("One-to-one function property-based test passed successfully!");
    }


    //Second set of tests (unit tests)

    /**
     * {@code generateFeatureConfigs}
     *
     * Helper function generating arguments for parameterized testing of the function {@code unitTestFeaturesOfExporter}
     * The arguments are configurations that contain the paths of input and output files with different conversations in them
     * as well as filtering and report configurations
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
     * UNIT-TESTS: Supervised testing.
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
}
