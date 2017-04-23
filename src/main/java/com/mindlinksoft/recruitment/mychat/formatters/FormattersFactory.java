package com.mindlinksoft.recruitment.mychat.formatters;

import java.util.ArrayList;

import org.hamcrest.Factory;

import com.mindlinksoft.recruitment.mychat.conversation.ConversationExporterConfiguration;
import com.mindlinksoft.recruitment.mychat.formatters.BlacklistedWordsFormatter;
import com.mindlinksoft.recruitment.mychat.formatters.CCandPhoneNoFormatter;
import com.mindlinksoft.recruitment.mychat.formatters.ObfuscateUserIDFormatter;
/**
 * A {@link Factory} that generates Message Content Formatters based on the Conversation Exporter Configuration
 * @author Omar
 *
 */
public class FormattersFactory {
	public static ArrayList<IFormatter> getFormatters(ConversationExporterConfiguration configuration)
	{
    	ArrayList<IFormatter> formatters = new ArrayList<IFormatter>();
    	if(configuration.isHideCCandPhoneno())
    	{
    		formatters.add(new CCandPhoneNoFormatter());
    	}
    	if(configuration.isObfuscateUserID())
    	{
    		formatters.add(new ObfuscateUserIDFormatter());
    	}
    	if(configuration.getBlacklistedWords()!=null && configuration.getBlacklistedWords().length>0)
    	{
    		formatters.add(new BlacklistedWordsFormatter(configuration.getBlacklistedWords()));
    	}
		return formatters;
	}
}
