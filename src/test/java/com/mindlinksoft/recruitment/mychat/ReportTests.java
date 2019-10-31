package com.mindlinksoft.recruitment.mychat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ReportTests {

    public final String inputFilePath = "chat.txt";
    public final String outputFilePath = "chat.json";

    /**
     * Test for most active users with no filter applied to the conversation.
     */
    @Test
    public void testUserActivityNoFilter() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        String[] args = new String[4];
        args[0] = "no_filter";
        args[1] = "nil";
        args[2] = "no";
        args[3] = "no";
        try {
            exporter.exportConversation(inputFilePath, outputFilePath, args);
            Report userActivityReport = new Report();
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

            Gson g = builder.create();
            Conversation c = g.fromJson(new InputStreamReader(new FileInputStream(outputFilePath)), Conversation.class);

            Message[] ms = new Message[c.messages.size()];
            c.messages.toArray(ms);
            List<String> userReport = new ArrayList<>(userActivityReport.makeReport(c));


            assertEquals("bob=4", userReport.get(1));
            assertEquals("mike=2", userReport.get(2));
            assertEquals("angus=2", userReport.get(3));
            assertEquals("dave=1", userReport.get(4));
        } catch (IOException e) {
            System.out.println("There was a problem with exporting the conversation from the input file " +
                    "to the output file.");
        }
    }

    /**
     * Test for most active users with no filter applied to the conversation.
     */
    @Test
    public void testUserActivityHiddenUsernameFilter() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        String[] args = new String[4];
        args[0] = "no_filter";
        args[1] = "nil";
        args[2] = "no";
        args[3] = "yes";
        try {
            exporter.exportConversation(inputFilePath, outputFilePath, args);
            Report userActivityReport = new Report();
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

            Gson g = builder.create();
            Conversation c = g.fromJson(new InputStreamReader(new FileInputStream(outputFilePath)), Conversation.class);

            Message[] ms = new Message[c.messages.size()];
            c.messages.toArray(ms);
            List<String> userReport = new ArrayList<>(userActivityReport.makeReport(c));


            assertEquals("9f9d51bc-70ef-31ca-9c14-f307980a29d8=4", userReport.get(1));
            assertEquals("e7a70020-ac0d-33a2-9b88-0f2003bb4e46=2", userReport.get(2));
            assertEquals("18126e7b-d3f8-3b3f-be4d-f094def5b7de=2", userReport.get(3));
            assertEquals("16108387-43cc-30e3-a4fd-da748282d9b8=1", userReport.get(4));
        } catch (IOException e) {
            System.out.println("There was a problem with exporting the conversation from the input file " +
                    "to the output file.");
        }
    }


    /**
     * Test for most active users with no filter applied to the conversation.
     */
    @Test
    public void testUserActivitySpecificWordFilter() throws Exception {
        ConversationExporter exporter = new ConversationExporter();

        String[] args = new String[4];
        args[0] = "specific_word";
        args[1] = "pie";
        args[2] = "no";
        args[3] = "no";
        try {
            exporter.exportConversation(inputFilePath, outputFilePath, args);
            Report userActivityReport = new Report();
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Instant.class, new InstantDeserializer());

            Gson g = builder.create();
            Conversation c = g.fromJson(new InputStreamReader(new FileInputStream(outputFilePath)), Conversation.class);

            Message[] ms = new Message[c.messages.size()];
            c.messages.toArray(ms);
            List<String> userReport = new ArrayList<>(userActivityReport.makeReport(c));

            assertEquals("bob=3", userReport.get(1));
            assertEquals("angus=2", userReport.get(2));
            assertEquals("dave=1", userReport.get(3));
        } catch (IOException e) {
            System.out.println("There was a problem with exporting the conversation from the input file " +
                    "to the output file.");
        }
    }
}
