package com.mindlinksoft.recruitment.mychat;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.mindlinksoft.recruitment.mychat.models.Conversation;
import com.mindlinksoft.recruitment.mychat.models.User;
import com.mindlinksoft.recruitment.mychat.options.Report;

import org.junit.Test;

public class ReportTests {
        /**
         * Integration test for the report generation.
         * 
         * @throws FileNotFoundException Thrown when the the input is illegal
         * @throws IOException           Thrown when the writting to the output file
         *                               fails
         */
        @Test
        public void testReportGeneration() throws FileNotFoundException, IOException {
                ConversationExporter exporter = new ConversationExporter();

                // fake configuration
                ConversationExporterConfiguration configuration = new ConversationExporterConfiguration();
                configuration.inputFilePath = "chat.txt";
                configuration.outputFilePath = "testChat.json";
                configuration.report = true;

                // fake conversation
                Conversation conversation = new OptionsTests().generateFakeConversation();
                // run through the rest of the conversation exporter
                exporter.applyOptions(conversation, configuration);
                exporter.writeConversation(conversation, configuration.outputFilePath);

                List<User> report = conversation.getActivity();

                assertEquals(3, report.size());

                assertEquals("Ralof", report.get(0).getSender());
                assertEquals(Integer.valueOf(2), report.get(0).getCount());

                assertEquals("Imperial Soldier", report.get(1).getSender());
                assertEquals(Integer.valueOf(1), report.get(1).getCount());

                assertEquals("Lokir", report.get(2).getSender());
                assertEquals(Integer.valueOf(1), report.get(2).getCount());
        }

        /**
         * First Unit test for the filter by user option.
         * 
         * @throws FileNotFoundException Thrown when the the input is illegal
         * @throws IOException           Thrown when the writting to the output file
         *                               fails
         */
        @Test
        public void firstUnitTestReport() throws FileNotFoundException, IOException {
                Conversation conversation = new OptionsTests().generateFakeConversation();
                Conversation conversationWithReport = new Report(conversation).process();

                List<User> report = conversationWithReport.getActivity();

                assertEquals(3, report.size());

                assertEquals("Ralof", report.get(0).getSender());
                assertEquals(Integer.valueOf(2), report.get(0).getCount());

                assertEquals("Imperial Soldier", report.get(1).getSender());
                assertEquals(Integer.valueOf(1), report.get(1).getCount());

                assertEquals("Lokir", report.get(2).getSender());
                assertEquals(Integer.valueOf(1), report.get(2).getCount());
        }

}
