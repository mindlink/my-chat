package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.mindlinksoft.recruitment.mychat.models.Conversation;

import org.junit.Test;

public class IOTests {
    /**
     * Test for illegal input
     * 
     * @throws FileNotFoundException Thrown when the the input is illegal
     * @throws IOException           Thrown when the writting to the output file
     *                               fails
     */
    @Test(expected = FileNotFoundException.class)
    public void testIfErrorOnInputFiles() throws FileNotFoundException, IOException {
        ConversationExporter exporter = new ConversationExporter();

        // fake configuration
        ConversationExporterConfiguration configuration = new ConversationExporterConfiguration();
        configuration.inputFilePath = "ImaginaryFile.txt";
        configuration.outputFilePath = "out.json";

        exporter.readConversation(configuration.inputFilePath);
    }
}
