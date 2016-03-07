package Filter;

import com.mindlinksoft.recruitment.mychat.Message;
import java.util.ArrayList;

/**
 * Applies username filter. Only returns messages from chat.txt with the
 * specified user in the following format as an ArrayList of String:
 *  <conversation_name><new_line>
    (<unix_timestamp><space><username><space><message><new_line>)*
 */
public final class FilterUser extends Filter
{
    @Override
    public ArrayList<String> Filter(ArrayList<Message> message, String[] filterQuery)
    {
        String username = filterQuery[0];
        ArrayList<String> list = new ArrayList<>();
        
        for (int i = 0; i < message.size(); ++i)
        {
            Message m = message.get(i);
            
            if (m.GetSenderId().equalsIgnoreCase(username))
            {
                String s = m.GetTimeStamp().toString() + " " +
                        m.GetSenderId() + " " +
                        m.GetContent();
                list.add(s);
            }
        }
        
        for (int i = 0; i < list.size(); ++i)
            if (i != list.size() - 1)
                list.set(i, list.get(i) + "\n");
        
        return list;
    }
}
