package com.mindlinksoft.recruitment.mychat.message;

import java.util.Map;

import org.apache.commons.lang3.Validate;

/**
 * Message formatter the replaces user IDs with aliases from
 * the provided alias map.
 *
 */
public class UserAliasMessageFormatter implements IMessageFormatter {

	private Map<String, String> userAliasesMap;

	public UserAliasMessageFormatter(Map<String, String> userAliasesMap) {
		this.userAliasesMap = Validate.notNull(userAliasesMap);
	}
	
	@Override
	public IMessage format(IMessage message) {
		if (userAliasesMap.containsKey(message.getSenderId())) {
			String alias = userAliasesMap.get(message.getSenderId());
			message.setSenderId(alias);
		}
		return message;
	}

}
