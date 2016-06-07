package com.mindlinksoft.recruitment.mychat;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.regex.Matcher;

/**
 * Represents the configuration for the exporter.
 */
public final class ConversationExporterConfiguration {
    /**
     * Gets the input file path.
     */
    public final String inputFilePath;

    /**
     * Gets the output file path.
     */
    public final String outputFilePath;

    /**
     * Gets the blacklist file path.
     */
    public final String blacklistFilePath;

    public final String[] blacklistWords;

    /**
     * Gets the username to whose messages will be exported.
     */
    public final String usernameFilter;

    /**
     * Gets the keyword messages containing which will be exported.
     */
    public final String keywordFilter;

    /**
     * Enables/disables credit card number filter.
     */
    public final boolean creditCardFilter;

    /**
     * Enables/disables phone number filter.
     */
    public final boolean phoneNumberFilter;

    /**
     * Enables/disables username obfuscation.
     */
    public final boolean obfuscateNames;

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath, String usernameFilter, String keywordFilter, String blacklistFilePath, boolean creditCardFilter, boolean phoneNumberFilter, boolean obfuscateNames) throws Exception{
        this.inputFilePath = (inputFilePath.length() >= 3)?inputFilePath:/*throw exception*/"in.txt";
        this.outputFilePath = (outputFilePath.length() >= 3)?outputFilePath:/*throw exception*/"out.txt";
        this.blacklistFilePath = blacklistFilePath; //will have to check .length to find out if it is used

        //these are regex
        this.usernameFilter = usernameFilter;
        this.keywordFilter = keywordFilter;

        this.creditCardFilter = creditCardFilter;
        this.phoneNumberFilter = phoneNumberFilter;
        this.obfuscateNames = obfuscateNames;
        ArrayList<String> blacklistWords = new ArrayList<String>();
        try(
                InputStream is = new FileInputStream(blacklistFilePath);
                BufferedReader r = new BufferedReader(new InputStreamReader(is))
        )
        {
            String line;
            while ((line = r.readLine()) != null)
            {
                blacklistWords.add(line);
            }
            this.blacklistWords = blacklistWords.toArray(new String[blacklistWords.size()]);
        }
        catch (FileNotFoundException e)
        {
            throw new IllegalArgumentException("The file was not found.");
        }
        catch (IOException e)
        {
            throw new IOException("Something went wrong");
        }
    }
}
