package com.mindlinksoft.recruitment.mychat.model;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.mindlinksoft.recruitment.mychat.bean.Message;

/**
 * Contains helper methods to parse and filter messages
 */
public class MessageParser {

	private static final String REDACTED = "*redacted*";
	private static final String KEYWORD_PATTERN_MATCH = "(?iu).*\\b(%s)\\b.*";
	private static final String KEYWORD_PATTERN_FIND_BOUNDARY = "(?iu)\\b(%s)\\b";
	private static final String PHONE = "(\\+?\\b\\d{1,3}\\b( |-))?((\\b\\d{2,6}\\b( |-)\\b\\d{2,6}\\b(( |-)\\b\\d{2,6}\\b)?)|(\\b\\d{8,12}\\b))";

	// taken from: http://www.regular-expressions.info/creditcard.html
	private static final String CREDIT_CARD = "\\b(?:4[0-9]{12}(?:[0-9]{3})?" + // Visa
			"|(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12}" + // MasterCard
			"|3[47][0-9]{13}" + // American Express
			"|3(?:0[0-5]|[68][0-9])[0-9]{11}" + // Diners Club
			"|6(?:011|5[0-9]{2})[0-9]{12}" + // Discover
			"|(?:2131|1800|35\\d{3})\\d{11})\\b"; // JCB

	// (<unix_timestamp><space><username><space><message><new_line>)*
	private static final String REGEX = "^(\\d+) ([^ ]+) (.*)$";

	public Message parse(String input) {
		if (input != null) {
			Matcher m = Pattern.compile(REGEX).matcher(input);
			if (m.matches()) {
				Instant instant = Instant.ofEpochSecond(Long.parseUnsignedLong(m.group(1)));
				return new Message(instant, m.group(2), m.group(3));
			}
		}
		throw new InvalidMessageException(input);
	}

	public boolean filterByKeyword(ConversationExporterConfiguration config, Message m) {
		if (StringUtils.isBlank(config.getFilterByKeyword()))
			return true;
		return containsKeyword(m, config.getFilterByKeyword());
	}

	public Message filterBlacklistedWords(ConversationExporterConfiguration config, Message m) {
		if (StringUtils.isBlank(config.getBlacklist()))
			return m;
		return hideKeywords(m, config.getBlacklist());
	}

	public boolean filterByUser(ConversationExporterConfiguration config, Message m) {
		if (config.getFilterByUser() == null)
			return true;
		return m.getSenderId().equals(config.getFilterByUser());
	}

	protected boolean containsKeyword(Message m, String keyword) {
		if (m.getContent() == null)
			return false;
		if (StringUtils.isBlank(keyword))
			return false;
		return m.getContent().matches(String.format(KEYWORD_PATTERN_MATCH, Pattern.quote(keyword)));
	}

	protected Message hideKeywords(Message m, String keywordList) {
		if (m.getContent() == null)
			return m;
		if (StringUtils.isBlank(keywordList))
			return m;

		String toReplace = StringUtils.join(keywordList.split(" "), "|");
		return hideKeywordRegex(m, String.format(KEYWORD_PATTERN_FIND_BOUNDARY, toReplace));
	}

	public Message filterCreditCards(ConversationExporterConfiguration config, Message m) {
		if (!config.isHideCreditCardAndPhone())
			return m;
		if (m.getContent() == null)
			return m;
		return hideKeywordRegex(m, CREDIT_CARD);
	}

	public Message filterPhoneNumbers(ConversationExporterConfiguration config, Message m) {
		if (!config.isHideCreditCardAndPhone())
			return m;
		if (m.getContent() == null)
			return m;
		return hideKeywordRegex(m, PHONE);
	}

	private Message hideKeywordRegex(Message message, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(message.getContent());
		StringBuffer sb = new StringBuffer();
		while (m.find())
			m.appendReplacement(sb, Matcher.quoteReplacement(REDACTED));
		m.appendTail(sb);
		return new Message(message.getTimestamp(), message.getSenderId(), sb.toString());
	}

	protected String getKeywordsAsRegexOr(String keywordList) {
		List<String> quoted = Arrays.stream(keywordList.split(" ")).map(Pattern::quote).collect(Collectors.toList());
		return String.join("|", quoted);
	}

}
