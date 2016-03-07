package Filter;

import com.mindlinksoft.recruitment.mychat.Message;
import java.util.ArrayList;

/**
 * Applies no filter. Returns chat.txt content in the following format as an ArrayList of String:
 *  <conversation_name><new_line>
    (<unix_timestamp><space><username><space><message><new_line>)*
 */
public final class FilterNone extends Filter
{
    @Override
    public ArrayList<String> Filter(ArrayList<Message> message, String[] filterQuery)
    {
        ArrayList<String> list = new ArrayList<>();
        
        for (int i = 0; i < message.size(); ++i)
        {
            Message m = message.get(i);
            String s = m.GetTimeStamp().toString() + " " +
                        m.GetSenderId() + " " +
                        m.GetContent();
            if (i != message.size() - 1)
                s += "\n";
            
            list.add(s);
        }
        
        return list;
    }
}
