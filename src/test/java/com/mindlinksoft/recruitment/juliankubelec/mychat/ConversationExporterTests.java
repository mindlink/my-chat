package com.mindlinksoft.recruitment.juliankubelec.mychat;

import com.google.gson.*;
import com.mindlinksoft.recruitment.juliankubelec.mychat.exceptions.EmptyTextFileException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link ConversationExporter}.
 */
public class ConversationExporterTests {


    String filepathIn;
    String filepathOut;
    String filepathInEmptyTxt;
    @Before
    public void initialiseFilenames() {
        filepathIn = "chat.txt";
        filepathOut = "chat.json";
        filepathInEmptyTxt = "empty.txt";
    }

    /**
     * Tests that exporting a conversation will export the conversation correctly.
     * @throws Exception When something bad happens.
     */
    @Test
    public void testExportingConversationExportsConversation() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        exporter.exportConversation(filepathIn, filepathOut);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        Gson g = builder.create();
        Conversation c = g.fromJson(new InputStreamReader(new FileInputStream(filepathOut)), Conversation.class);

        assertEquals("My Conversation", c.getName());

        assertEquals(7, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(Instant.ofEpochSecond(1448470901), ms[0].getTimestamp());
        assertEquals("bob", ms[0].getSenderId());
        assertEquals("Hello there!", ms[0].getContent());

        assertEquals(Instant.ofEpochSecond(1448470905), ms[1].getTimestamp());
        assertEquals("mike", ms[1].getSenderId());
        assertEquals("how are you?", ms[1].getContent());

        assertEquals(Instant.ofEpochSecond(1448470906), ms[2].getTimestamp());
        assertEquals("bob", ms[2].getSenderId());
        assertEquals("I'm good thanks, do you like pie?", ms[2].getContent());

        assertEquals(Instant.ofEpochSecond(1448470910), ms[3].getTimestamp());
        assertEquals("mike", ms[3].getSenderId());
        assertEquals("no, let me ask Angus...", ms[3].getContent());

        assertEquals(Instant.ofEpochSecond(1448470912), ms[4].getTimestamp());
        assertEquals("angus", ms[4].getSenderId());
        assertEquals("Hell yes! Are we buying some pie?", ms[4].getContent());

        assertEquals(Instant.ofEpochSecond(1448470914), ms[5].getTimestamp());
        assertEquals("bob", ms[5].getSenderId());
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[5].getContent());

        assertEquals(Instant.ofEpochSecond(1448470915), ms[6].getTimestamp());
        assertEquals("angus", ms[6].getSenderId());
        assertEquals("YES! I'm the head pie eater there...", ms[6].getContent());
    }

    /**
     * Tests if filterByUser produces the correct JSON
     * filterByUser
     */
    @Test
    public void testFilterByUser() throws Exception
    {
        ConversationExporter exporter = new ConversationExporter();

        String user = "bob";
        exporter.setFilterUserId(user);
        exporter.exportConversation(filepathIn, filepathOut);
        Conversation c = exporter.getConversation();
        assertEquals(3, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(Instant.ofEpochSecond(1448470901), ms[0].getTimestamp());
        assertEquals("bob", ms[0].getSenderId());
        assertEquals("Hello there!", ms[0].getContent());

        assertEquals(Instant.ofEpochSecond(1448470906), ms[1].getTimestamp());
        assertEquals("bob", ms[1].getSenderId());
        assertEquals("I'm good thanks, do you like pie?", ms[1].getContent());

        assertEquals(Instant.ofEpochSecond(1448470914), ms[2].getTimestamp());
        assertEquals("bob", ms[2].getSenderId());
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[2].getContent());

    }

    /**
     * Tests if filterByKeyword produces the correct JSON
     * filterByKeyword
     */
    @Test
    public void testFilterByKeyword() throws Exception
    {
        ConversationExporter exporter = new ConversationExporter();

        String keyword = "pie";
        exporter.setFilterKeyword(keyword);
        exporter.exportConversation(filepathIn, filepathOut);
        Conversation c = exporter.getConversation();
        assertEquals(4, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(Instant.ofEpochSecond(1448470906), ms[0].getTimestamp());
        assertEquals("bob", ms[0].getSenderId());
        assertEquals("I'm good thanks, do you like pie?", ms[0].getContent());

        assertEquals(Instant.ofEpochSecond(1448470912), ms[1].getTimestamp());
        assertEquals("angus", ms[1].getSenderId());
        assertEquals("Hell yes! Are we buying some pie?", ms[1].getContent());

        assertEquals(Instant.ofEpochSecond(1448470914), ms[2].getTimestamp());
        assertEquals("bob", ms[2].getSenderId());
        assertEquals("No, just want to know if there's anybody else in the pie society...", ms[2].getContent());

        assertEquals(Instant.ofEpochSecond(1448470915), ms[3].getTimestamp());
        assertEquals("angus", ms[3].getSenderId());
        assertEquals("YES! I'm the head pie eater there...", ms[3].getContent());
    }

    /**
     * Tests if blacklist produces the correct JSON
     * filterByKeyword
     */
    @Test
    public void testBlacklist() throws Exception
    {
        ConversationExporter exporter = new ConversationExporter();

        String[] word = {"pie", "no"};
        List<String> blacklist = Arrays.asList(word);

        exporter.setBlacklist(blacklist);
        exporter.exportConversation(filepathIn, filepathOut);
        Conversation c = exporter.getConversation();

        assertEquals(7, c.getMessages().size());

        Message[] ms = new Message[c.getMessages().size()];
        c.getMessages().toArray(ms);

        assertEquals(Instant.ofEpochSecond(1448470901), ms[0].getTimestamp());
        assertEquals("bob", ms[0].getSenderId());
        assertEquals("Hello there!", ms[0].getContent());

        assertEquals(Instant.ofEpochSecond(1448470905), ms[1].getTimestamp());
        assertEquals("mike", ms[1].getSenderId());
        assertEquals("how are you?", ms[1].getContent());

        assertEquals(Instant.ofEpochSecond(1448470906), ms[2].getTimestamp());
        assertEquals("bob", ms[2].getSenderId());
        assertEquals("I'm good thanks, do you like *redacted*?", ms[2].getContent());

        assertEquals(Instant.ofEpochSecond(1448470910), ms[3].getTimestamp());
        assertEquals("mike", ms[3].getSenderId());
        assertEquals("*redacted*, let me ask Angus...", ms[3].getContent());

        assertEquals(Instant.ofEpochSecond(1448470912), ms[4].getTimestamp());
        assertEquals("angus", ms[4].getSenderId());
        assertEquals("Hell yes! Are we buying some *redacted*?", ms[4].getContent());

        assertEquals(Instant.ofEpochSecond(1448470914), ms[5].getTimestamp());
        assertEquals("bob", ms[5].getSenderId());
        assertEquals("*redacted*, just want to know if there's anybody else in the *redacted* society...", ms[5].getContent());

        assertEquals(Instant.ofEpochSecond(1448470915), ms[6].getTimestamp());
        assertEquals("angus", ms[6].getSenderId());
        assertEquals("YES! I'm the head *redacted* eater there...", ms[6].getContent());
    }

    /**
     * Tests if the report option property Activity to the JSON
     */
    @Test
    public void testReport() throws Exception {
        ConversationExporter exporter = new ConversationExporter();
        exporter.setIncludeReport(true);
        exporter.exportConversation(filepathIn, filepathOut);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Instant.class, new InstantDeserializer());
        Gson g = builder.create();
        JsonElement json = exporter.getExportedJson();
        Report[] reportList = g.fromJson(json.getAsJsonObject().get("activity"),Report[].class);
        assertEquals(3, reportList.length);

        assertEquals("bob", reportList[0].sender);
        assertEquals(3, reportList[0].count);

        assertEquals("angus", reportList[1].sender);
        assertEquals(2, reportList[1].count);

        assertEquals("mike", reportList[2].sender);
        assertEquals(2, reportList[2].count);
    }


    /**
     * Tests if the input is an empty text file
     */
    @Test(expected = EmptyTextFileException.class)
    public void testEmptyInputFile() throws Exception{
        File emptyFile;
        try{
            emptyFile = new File(filepathInEmptyTxt);
            boolean isFile = emptyFile.isFile();
            if(!isFile) {
                boolean canCreate = emptyFile.createNewFile();
                if(!canCreate)
                {
                    throw new IOException("Could not create empty file");
                }
            }
            emptyFile.deleteOnExit();
            ConversationExporter exporter = new ConversationExporter();
            exporter.exportConversation(filepathInEmptyTxt, "empty.json");
            if(!new File(filepathInEmptyTxt).delete()) {
                throw new IOException("Could not delete empty file");
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();

        }

    }


    /**
     * Tests if a non-existent input filepath throws IllegalArgumentException
     *
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInputFilepath() throws Exception{
        String invalidPath = "Non-existentTxtFile.txt";
        ConversationExporter exporter = new ConversationExporter();
        exporter.exportConversation(invalidPath, "empty.json");

    }

    static class InstantDeserializer implements JsonDeserializer<Instant> {

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
