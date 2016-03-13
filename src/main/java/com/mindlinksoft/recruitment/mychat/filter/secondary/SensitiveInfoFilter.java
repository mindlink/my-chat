package com.mindlinksoft.recruitment.mychat.filter.secondary;

import com.mindlinksoft.recruitment.mychat.conversation.Conversation;
import com.mindlinksoft.recruitment.mychat.filter.Filter;
import com.mindlinksoft.recruitment.mychat.message.Message;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/**
 * Filters the message contents for sensitive information. It replaces all
 * matches in the text. I haven't given much thought to the regexes, as it would
 * compicate thing a lot.
 *
 * Credit card samples: "2342-2342-2342-2234", "2342 2342 2342 2234"
 *
 * Phone number samples: "07 443 959595", "07 443959595", "07443 959595",
 * "07443959595"
 *
 * @author Gabor
 */
public class SensitiveInfoFilter implements Filter {

    final private String CENSORED = "*redacted*";

    final private String CREDIT_CARD_PATTERN = "\\d{4}[\\s|-]\\d{4}[\\s|-]\\d{4}[\\s|-]\\d{4}";
    final private String PHONE_NUMBER_PATTERN = "\\d{2}\\s?\\d{3}\\s?\\d{6}";

    @Override
    public void apply(Conversation conversation) {
        String content, newContent;
        for (Message message : conversation.getMessages()) {
            content = message.getContent();
            // replace creadit card numbers
            newContent = content.replaceAll(CREDIT_CARD_PATTERN, CENSORED);
            content = newContent;
            // replace phone numbers
            newContent = content.replaceAll(PHONE_NUMBER_PATTERN, CENSORED);
            content = newContent;

            message.setContent(content);
        }

    }
}
