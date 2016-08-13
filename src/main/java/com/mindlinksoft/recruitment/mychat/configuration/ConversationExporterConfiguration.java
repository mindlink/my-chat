package main.java.com.mindlinksoft.recruitment.mychat.configuration;

import java.util.ArrayList;
import java.util.List;

import main.java.com.mindlinksoft.recruitment.mychat.message.filters.IMessageFilter;
import main.java.com.mindlinksoft.recruitment.mychat.message.filters.KeywordFilter;
import main.java.com.mindlinksoft.recruitment.mychat.message.filters.UserFilter;
import main.java.com.mindlinksoft.recruitment.mychat.message.processors.MessageContentObfuscator;
import main.java.com.mindlinksoft.recruitment.mychat.message.processors.MessageSenderObfuscator;
import main.java.com.mindlinksoft.recruitment.mychat.message.processors.ProcessorsCollection;
import main.java.com.mindlinksoft.recruitment.mychat.message.processors.UserStatsCollector;

/**
 * Represents the configuration for the exporter.
 */
public final class ConversationExporterConfiguration {
	public static final String USER_STATS_TITLE = "User Statistics";
    
    private final String inputFilePath;

    private final String outputFilePath;
    
	private String userFilter;
	
	private String keywordFilter;
	
	private List<String> blacklist = new ArrayList<String>();
	
	private boolean obfuscateUsers;
	
	private boolean hideSensitiveInfo;
	
	private boolean reportUserStats;

    /**
     * Initializes a new instance of the {@link ConversationExporterConfiguration} class.
     * @param inputFilePath The input file path.
     * @param outputFilePath The output file path.
     */
    public ConversationExporterConfiguration(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
    }
	
	/**
	 * @param userFilter the userFilter to set
	 */
	public void setUserFilter(String userFilter) {
		this.userFilter = userFilter;
	}

	/**
	 * @param keywordFilter the keywordFilter to set
	 */
	public void setKeywordFilter(String keywordFilter) {
		this.keywordFilter = keywordFilter;
	}

	/**
	 * @param blacklist the blacklist to set
	 */
	public void setBlacklist(List<String> blacklist) {
		this.blacklist = blacklist;
	}

	/**
	 * @param obfuscateUsers the obfuscateUsers to set
	 */
	public void setObfuscateUsers(boolean obfuscateUsers) {
		this.obfuscateUsers = obfuscateUsers;
	}

	/**
	 * @param hideSensitiveInfo the hideSensitiveInfo to set
	 */
	public void setHideSensitiveInfo(boolean hideSensitiveInfo) {
		this.hideSensitiveInfo = hideSensitiveInfo;
	}

	/**
	 * @param reportUserStats the reportUserStats to set
	 */
	public void setReportUserStats(boolean reportUserStats) {
		this.reportUserStats = reportUserStats;
	}

	public String getInputFilePath(){ 
		return inputFilePath; 
	}
	
	public String getOutputFilePath(){ 
		return outputFilePath; 
	}
	
	public IMessageFilter getMessageFilter() { 
		if (userFilter != null && !userFilter.isEmpty()) {
			return new UserFilter(userFilter);
		}
		
		if (keywordFilter != null && !keywordFilter.isEmpty()) {
			return new KeywordFilter(keywordFilter);
		}
		
		return null;  
	} 
	
	public ProcessorsCollection getEnabledProcessors() {
		ProcessorsCollection processors = new ProcessorsCollection();
		
		if (hideSensitiveInfo || !blacklist.isEmpty()) 
			processors.addPlainProcessor(
					new MessageContentObfuscator(hideSensitiveInfo, blacklist));
		if (obfuscateUsers) 
			processors.addPlainProcessor(new MessageSenderObfuscator());
		if (reportUserStats)
			processors.addStringToIntCollector(new UserStatsCollector(USER_STATS_TITLE));
		
		return processors;
	}
}
