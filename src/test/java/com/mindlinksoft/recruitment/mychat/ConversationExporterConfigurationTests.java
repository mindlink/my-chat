package com.mindlinksoft.recruitment.mychat;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;


public class ConversationExporterConfigurationTests {


    @Test(expected = IllegalArgumentException.class)
    public void testFileArgumentsMissing() throws Exception{
        ConversationExporter exporter = new ConversationExporter();
        String[] args = new String[]{"ArgumentMissing.txt"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyStringArgument() throws Exception{
        ConversationExporter exporter = new ConversationExporter();
        String[] args = new String[]{"ArgumentMissing.txt",""};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentNotInSpec() throws Exception{
        ConversationExporter exporter = new ConversationExporter();
        String[] args = new String[]{"chat.txt","chat.json","args=fail"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalUserFromArguments() throws Exception{
        ConversationExporter exporter = new ConversationExporter();
        String[] args = new String[]{"chat.txt","chat.json","bob"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
    }

    @Test
    public void testUserFromArguments() throws Exception{
        ConversationExporter exporter = new ConversationExporter();
        String[] args = new String[]{"chat.txt","chat.json","user=bob"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        assertEquals(configuration.user,"bob");
    }

    @Test
    public void testUserFromArgumentsWithSpaces() throws Exception{
        ConversationExporter exporter = new ConversationExporter();
        String[] args = new String[]{"chat.txt","chat.json","user=      bob "};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        assertEquals(configuration.user,"bob");
    }

    @Test
    public void testKeywordFromArguments() throws Exception{
        ConversationExporter exporter = new ConversationExporter();
        String[] args = new String[]{"chat.txt","chat.json","keyword=test"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        assertEquals(configuration.keyword,"test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalKeywordFromArguments() throws Exception{
        ConversationExporter exporter = new ConversationExporter();
        String[] args = new String[]{"chat.txt","chat.json","keywor=test"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
    }

    @Test
    public void testBlacklistWithListArguments() throws Exception{
        ConversationExporter exporter = new ConversationExporter();
        String[] args = new String[]{"chat.txt","chat.json","blacklist=[hello,bye,this,there]"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        String[] blacklist = new String[]{"hello","bye","this","there"};
        assertArrayEquals(configuration.blacklist, blacklist);
    }
    @Test
    public void testBlacklistWithSpaces() throws Exception{
        ConversationExporter exporter = new ConversationExporter();
        String[] args = new String[]{"chat.txt","chat.json","blacklist=[     hello ,  bye, this,   there]"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        String[] blacklist = new String[]{"hello","bye","this","there"};
        assertArrayEquals(configuration.blacklist, blacklist);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testIllegalBlackListMalformedBrackets() throws Exception{
        ConversationExporter exporter = new ConversationExporter();
        String[] args = new String[]{"chat.txt","chat.json","blacklist=[test,score"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
    }


    @Test
    public void testAllArguments() throws Exception{
        ConversationExporter exporter = new ConversationExporter();
        String[] args = new String[]{"chat.txt","chat.json","user=mike","keyword=hate","blacklist=[bad,like,night]"};
        ConversationExporterConfiguration configuration = new CommandLineArgumentParser().parseCommandLineArguments(args);
        assertEquals(configuration.inputFilePath,"chat.txt");
        assertEquals(configuration.outputFilePath,"chat.json");
        assertEquals(configuration.keyword,"hate");
        assertEquals(configuration.user,"mike");
        String[] blacklist = new String[]{"bad","like","night"};
        assertArrayEquals(blacklist,configuration.blacklist);
    }




}
