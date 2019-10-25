package com.mindlinksoft.recruitment.mychat;

import com.beust.jcommander.*;
import com.mindlinksoft.recruitment.mychat.chatFeatures.*;

import java.util.ArrayList;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
    @Parameter(names="-i", description = "Input file path.", required=true, order=0)
    private String inputFilePath = "chat.txt";

    @Parameter(names="-o", description = "Output file path", required=true, order = 1)
    private String outputFilePath = "chat.json";

    @Parameter(names="-fk", description = "Filter results by keyword.")
    private String filterKeyword = "";

    @Parameter(names="-fu", description = "Filter results by userID.")
    private String filterUserID = "";

    @Parameter(names="-sbl", description = "Sanitize results to redact words in blacklist.")
    private String sanitizeBlacklist = "";

    @Parameter(names="-spc", description = "Sanitize results by redacting phone or bank card numbers.")
    private Boolean sanitizePhoneCardNumber = false;

    @Parameter(names="-suid", description = "Sanitize results by obfuscating userIDs.")
    private Boolean sanitizeUserID = false;

    @Parameter(names="-uar", description = "Appends a report of user activity to the exported chat log.")
    private Boolean userActivityReport = false;

    /**
     * Parses the given {@code arguments} into the exporter configuration.
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line arguments.
     */
    public CommandLineArgumentParser parseCommandLineArguments(String[] arguments) throws ParameterException {

        JCommander jCommander = new JCommander(this);
        jCommander.setProgramName("mychat");
        try {
            jCommander.parse(arguments);
        } catch (ParameterException e){
            //System.out.println(e.getMessage());
            jCommander.usage();
            throw new ParameterException(e);
        }

        return this;
    }

    public String getInputFilePath(){
        return inputFilePath;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public ArrayList<ChatFeature> getArguments(){
        ArrayList<ChatFeature> featureList = new ArrayList<>();

        if(!filterKeyword.isEmpty()){
            FilterKeyword filterKeywordFeature = new FilterKeyword();
            filterKeywordFeature.setArgument(filterKeyword);
            featureList.add(filterKeywordFeature);
        }

        if(!filterUserID.isEmpty()){
            FilterUser filterUser = new FilterUser();
            filterUser.setArgument(filterUserID);
            featureList.add(filterUser);
        }

        if(!sanitizeBlacklist.isEmpty()){
            SanitizeBlacklist sanitizeBlacklistFeature = new SanitizeBlacklist();
            sanitizeBlacklistFeature.setArgument(sanitizeBlacklist);
            featureList.add(sanitizeBlacklistFeature);
        }

        if(sanitizePhoneCardNumber){SanitizePhoneCardNumber sanitizePhoneCardNumber = new SanitizePhoneCardNumber(); featureList.add(sanitizePhoneCardNumber);}
        if(sanitizeUserID){SanitizeUserID sanitizeUserID = new SanitizeUserID(); featureList.add(sanitizeUserID);}
        if(userActivityReport){UserActivityReport userActivityReport = new UserActivityReport(); featureList.add(userActivityReport);}
        return featureList;
    }
}
