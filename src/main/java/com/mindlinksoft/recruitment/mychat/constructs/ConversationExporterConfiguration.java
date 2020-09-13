package com.mindlinksoft.recruitment.mychat.constructs;

/**
 * Represents the configuration for the exporter.
 */
public final class ConversationExporterConfiguration
{
    // The redacted string, used in place of a blacklisted word.
    private final String REDACT;
    // The separator used when splitting a message content into words.
    private final String SEP_REGEX;
    // The separator/delimiter used when joining words to make a message content.
    private final String SEP_JOIN;
    // Regex for specifying any non-letter and non-whitespace characters.
    private final String LETTERS_AND_SPACES;
    // The index in a message split where the content starts.
    private final int CONTENT_START_INDEX;
    // The input file path.
    private String inputFilePath;
    // The output file path.
    private String outputFilePath;
    // Filter by a specific user.
    private String user;
    // Filter by a specific keyword.
    private String keyword;
    // Hide specific words.
    private String[] wordsToHide;
    // Whether to hide credit card and phone numbers, that appear in message content.
    private boolean hideCCPN;
    // Whether to obfuscate user IDs.
    private boolean obf;
    // Whether to add a report to the conversation that details the most active users.
    private boolean report;

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     *
     * @param inputFilePath  The input file path.
     * @param outputFilePath The output file path.
     * @param user           Filter messages from this user.
     * @param keyword        Filter messages containing this word.
     * @param wordsToHide    List of blacklisted words which need redacting.
     * @param hideCCPN       If true, hide credit card and phone numbers, that appear in message content.
     * @param obf            If true, obfuscate user IDs.
     * @param report         If true, add a report to the {@link Conversation} that details the most active users.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath,
                                             String user, String keyword, String[] wordsToHide,
                                             boolean hideCCPN, boolean obf, boolean report)
    {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        if (user.equals("")) {
            this.user = null;
        } else {
            this.user = user.toLowerCase();
        }
        if (keyword.equals("")) {
            this.keyword = null;
        } else {
            this.keyword = keyword;
        }
        if (wordsToHide.length == 0) {
            this.wordsToHide = null;
        } else {
            this.wordsToHide = wordsToHide;
        }
        this.hideCCPN = hideCCPN;
        this.obf = obf;
        this.report = report;
        REDACT = "*redacted*";
        SEP_REGEX = "\\s+";
        SEP_JOIN = " ";
        LETTERS_AND_SPACES = "[^a-zA-Z ]";
        CONTENT_START_INDEX = 2;
    }

    public String getInputFilePath()
    {
        return inputFilePath;
    }

    public void setInputFilePath(String inputFilePath)
    {
        this.inputFilePath = inputFilePath;
    }

    public String getOutputFilePath()
    {
        return outputFilePath;
    }

    public void setOutputFilePath(String outputFilePath)
    {
        this.outputFilePath = outputFilePath;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getKeyword()
    {
        return keyword;
    }

    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
    }

    public String[] getWordsToHide()
    {
        return wordsToHide;
    }

    public void setWordsToHide(String[] wordsToHide)
    {
        this.wordsToHide = wordsToHide;
    }

    public boolean isHideCCPN()
    {
        return hideCCPN;
    }

    public void setHideCCPN(boolean hideCCPN)
    {
        this.hideCCPN = hideCCPN;
    }

    public boolean isObf()
    {
        return obf;
    }

    public void setObf(boolean obf)
    {
        this.obf = obf;
    }

    public boolean isReport()
    {
        return report;
    }

    public void setReport(boolean report)
    {
        this.report = report;
    }

    public String getREDACT()
    {
        return REDACT;
    }

    public String getSEP_REGEX()
    {
        return SEP_REGEX;
    }

    public String getSEP_JOIN()
    {
        return SEP_JOIN;
    }

    public String getLETTERS_AND_SPACES()
    {
        return LETTERS_AND_SPACES;
    }

    public int getCONTENT_START_INDEX()
    {
        return CONTENT_START_INDEX;
    }
}
