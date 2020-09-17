package com.mindlinksoft.recruitment.mychat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class ConversationGenerateReportTests {

    @Test
    public void testReportGenerated() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        String[] args1 = new String[]{"chat.txt","chat.json"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args1);
        exporter.exportConversation(configuration);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        Gson g = builder.create();
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream("chat.json")), Conversation.class);
        System.out.println("Report"+c.report);
        c.report.get(0).sender_Id.equals("Angus");
        c.report.get(1).sender_Id.equals("Mike");
        c.report.get(2).sender_Id.equals("Bob");


    }
}
