package Filter;

import com.mindlinksoft.recruitment.mychat.Message;
import java.util.ArrayList;

/**
 * Base class for filtering conversation messages.
 */
public abstract class Filter
{
    /**
     * Goes through all messages and apply filterQuery.
     * @param messages - all messages in a conversation to filter through.
     * @param filterQuery - the things to filter in messages.
     * @return Filtered conversation messages.
     */
    public abstract ArrayList<String> Filter(ArrayList<Message> messages, String[] filterQuery);
}
