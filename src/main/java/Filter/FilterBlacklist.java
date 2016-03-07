package Filter;

import com.mindlinksoft.recruitment.mychat.Message;
import java.util.ArrayList;

/**
 * Applies blacklist filter. Returns chat.txt content with the specified blacklist word(s)
 * replaced with "*redacted*" in the following format as an ArrayList of String:
 *  <conversation_name><new_line>
    (<unix_timestamp><space><username><space><message><new_line>)*
 */
public final class FilterBlacklist extends Filter
{
    @Override
    public ArrayList<String> Filter(ArrayList<Message> messages, String[] filterQuery)
    {
        ArrayList<String> list = new ArrayList<>();
        
        for (int i = 0; i < messages.size(); ++i)
        {
            Message line = messages.get(i);
            list.add(line.GetTimeStamp().toString() + " " + line.GetSenderId() + " ");
            
            String[] words = line.GetContent().split(" ");

            for (int n = 0; n < words.length; ++n)
            {
                String word = words[n];
                
                for (int x = 0; x < filterQuery.length; ++x)
                {                    
                    if (word.equalsIgnoreCase(filterQuery[x]))
                    {
                        word = "\"*redacted*\"";
                        break;
                    }
                }
                
                
                if (n != words.length - 1)
                    word += " ";
                else
                {
                    if (i != messages.size() - 1)
                        word += "\n";
                }
                    
                list.add(word);
            }
        }
        
        return list;
    }
}
