package com.mindlinksoft.recruitment.mychat.filter.secondary;

import com.mindlinksoft.recruitment.mychat.conversation.Conversation;
import com.mindlinksoft.recruitment.mychat.filter.Filter;
import com.mindlinksoft.recruitment.mychat.message.Message;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * Filters the message contents based on a list of blacklisted words. It
 * replaces all matches in the text. It uses regex for case-insensitivity.
 * (Would be more elegant to tokenize the text first so only whole matches are
 * found.)
 *
 * Secondary filters should be applied after any primary filters.
 *
 * @author Gabor
 */
public class BlackListFilter implements Filter {

    final private String CENSORED = "*redacted*";
    final private String[] blackList;

    public BlackListFilter(String commaSeparatedBlackList) {
        this.blackList = StringUtils.split(commaSeparatedBlackList, ",");
    }

    @Override
    public void apply(Conversation conversation) {
        String content, newContent;
        for (Message message : conversation.getMessages()) {
            content = message.getContent();
            for (String blackListItem : blackList) {
                String pattern = "(?i)" + StringUtils.trim(blackListItem);
                newContent = content.replaceAll(pattern, CENSORED);
                content = newContent;
            }
            message.setContent(content);
        }
    }

}
