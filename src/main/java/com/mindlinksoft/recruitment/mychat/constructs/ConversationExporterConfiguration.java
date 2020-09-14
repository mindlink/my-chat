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
    // Regex for identifying phone numbers.
    private final String PHONE_NUM_REGEX;
    // Regex for identifying credit cards numbers.
    private final String CC_REGEX;
    // Regex for specifying any non-letter and non-whitespace characters.
    private final String LETTERS_AND_SPACES;
    // The index in a message split where the content starts.
    private final int CONTENT_START_INDEX;
    // A directory specifying a list of obfuscated users.
    private final String OBF_FILE_PATH;
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
        initialiseUser(user);
        initialiseKeyword(keyword);
        initialiseWordsToHide(wordsToHide);
        this.hideCCPN = hideCCPN;
        this.obf = obf;
        this.report = report;
        REDACT = "*redacted*";
        SEP_REGEX = "\\s+";
        SEP_JOIN = " ";
        PHONE_NUM_REGEX = "(\\(?\\+44\\)?\\s?([12378])\\d{3}|\\(?(01|02|03|07|08)\\d{3}\\)?)\\s?\\d{3}\\s?\\d{3}|(\\(?\\+44\\)?\\s?([123578])\\d{2}|\\(?(01|02|03|05|07|08)\\d{2}\\)?)\\s?\\d{3}\\s?\\d{4}|(\\(?\\+44\\)?\\s?([59])\\d{2}|\\(?(05|09)\\d{2}\\)?)\\s?\\d{3}\\s?\\d{3}";
        CC_REGEX = "\\b((\\d{4})-? ?(\\d{4})-? ?(\\d{4})-? ?(\\d{4}))\\b";
        LETTERS_AND_SPACES = "[^a-zA-Z ]";
        CONTENT_START_INDEX = 2;
        OBF_FILE_PATH = "obfUsers.txt";
    }

    private void initialiseUser(String user)
    {
        if (user.equals("")) {
            this.user = null;
        } else {
            this.user = user;
        }
    }

    private void initialiseKeyword(String keyword)
    {
        if (keyword.equals("")) {
            this.keyword = null;
        } else {
            this.keyword = keyword;
        }
    }

    private void initialiseWordsToHide(String[] wordsToHide)
    {
        if (wordsToHide.length == 0) {
            this.wordsToHide = null;
        } else {
            this.wordsToHide = wordsToHide;
        }
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

    public String getPHONE_NUM_REGEX()
    {
        return PHONE_NUM_REGEX;
    }

    public String getCC_REGEX()
    {
        return CC_REGEX;
    }

    public String getLETTERS_AND_SPACES()
    {
        return LETTERS_AND_SPACES;
    }

    public int getCONTENT_START_INDEX()
    {
        return CONTENT_START_INDEX;
    }

    public String getOBF_FILE_PATH()
    {
        return OBF_FILE_PATH;
    }
}
