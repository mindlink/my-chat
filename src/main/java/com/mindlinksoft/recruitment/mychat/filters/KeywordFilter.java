package com.mindlinksoft.recruitment.mychat.filters;
import org.hamcrest.Factory;

import com.mindlinksoft.recruitment.mychat.messages.Message;
/**
 * A {@link Filter} that Filters messages based on a keyword
 * @author Omar
 *
 */
public class KeywordFilter implements IFilter  {
	private final String keyword;
	
	public KeywordFilter(String keyword)
	{
		this.keyword=keyword;
	}
	@Override
	public Boolean apply(Message message) {

		return message.getContent().toUpperCase().contains(keyword.toUpperCase());
	}

}
