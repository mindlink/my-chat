package com.mindlinksoft.recruitment.mychat.conversation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.cli.Option;

import com.mindlinksoft.recruitment.mychat.CommandLineOptions;
import com.mindlinksoft.recruitment.mychat.PropertiesUtil;
import com.mindlinksoft.recruitment.mychat.message.KeywordMessageFilter;
import com.mindlinksoft.recruitment.mychat.message.RegexRedactingMessageFormatter;
import com.mindlinksoft.recruitment.mychat.message.UserAliasMessageFormatter;
import com.mindlinksoft.recruitment.mychat.message.UserIdMessageFilter;

/**
 * Factory for creating {@link IConversationFormatter}
 *
 */
public class ConversationFormatterFactory {

	private static final String CARDS_REGEX_PROP = "blacklist.regex.cards";
	private static final String PHONE_NUMBERS_REGEX_PROP = "blacklist.regex.phone.numbers";
	private static final String USER_ALIASES_PROP = "user.aliases";
	
	/**
	 * Creates a {@link IConversationFormatter}
	 * @param options
	 * @param properties
	 * @return {@link IConversationFormatter}
	 */
	public static IConversationFormatter createConversationFormatter(Option[] options, Properties properties) {
		ConversationFormatter formatter = new ConversationFormatter();
		for (int i = 0; i < options.length; i++) {
			Option option = options[i];
			switch (option.getOpt()) {
				case CommandLineOptions.USER_FILTER:
					System.out.println("Creating User ID message filter.");
					formatter.addMessageFilter(new UserIdMessageFilter(option.getValue()));
					break;
				case CommandLineOptions.KEYWORD_FILTER:
					System.out.println("Creating keyword message filter.");
					formatter.addMessageFilter(new KeywordMessageFilter(option.getValue()));
					break;
				case CommandLineOptions.BLACKLIST_WORDS:
					System.out.println("Creating blacklist formatter.");
					formatter.addMessageFormatter(new RegexRedactingMessageFormatter(option.getValuesList()));
					break;
				case CommandLineOptions.BLACKLIST_NUMBERS:
					System.out.println("Creating credit card and phone number formatter.");
					Collection<String> regexs = new ArrayList<String>();
					regexs.addAll(PropertiesUtil.getListProperty(CARDS_REGEX_PROP, properties));
					regexs.addAll(PropertiesUtil.getListProperty(PHONE_NUMBERS_REGEX_PROP, properties));
					formatter.addMessageFormatter(new RegexRedactingMessageFormatter(regexs));
					break;
				case CommandLineOptions.USE_ALIASES:
					System.out.println("Creating User Id alias formatter.");
					Map<String, String> aliases = PropertiesUtil.getMapProperty(USER_ALIASES_PROP, properties);
					formatter.addMessageFormatter(new UserAliasMessageFormatter(aliases));
				default:
					//Ignore other options.
					break;
			}
		}
		return formatter;
	}
	
}
