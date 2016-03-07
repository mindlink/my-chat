package Filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public final class FilterParameters
{
    /**
     * This method reads a command line input, converts this string into int
     * and returns the int if it's a valid entry.
     * @return An int representing the filter type.
     * @throws IOException 
     */
    public int GetFilterType() throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try
        {
            String filterType = "";
            int type = -1;
            while ((filterType = br.readLine()) != null)
            {
                if (filterType.isEmpty())
                {
                    System.out.println("You entered nothing. Please try again.");
                    continue;
                }
                
                type = Integer.parseInt(filterType);
                
                if (type == 0)
                {
                    br.close();
                    break;
                }
                
                if (type > 0 && type < 4)
                    break;
                else
                    System.out.print("Enter a number between 0 to 3 for the filter type\n" +
                                    "0 - No filter/Default.\n" +
                                    "1 - Filter by USERNAME.\n" +
                                    "2 - Filter by KEYWORD. \n" +
                                    "3 - Filter by BLACKLISTED-WORDS.\n");
            }
            System.out.println("Return type: " + type);
            return type;
        }
        catch (IOException e)
        {
            throw new IOException("Something IO-related went wrong: " + e.getMessage());
        }
    }    
    
    /**
     * 
     * @param filterType. Type of filter to use for the JSON report.
     * @return An array of strings containing one or more filter queries.
     * @throws IOException 
     */
    public String[] GetFilterQueries(int filterType) throws IOException
    {
        if (filterType == 0)
            return new String[0];
        
        String message1 = "Filter messages by entering a USERNAME.\n";
        String message2 = "Filter messages by entering a KEYWORDS.\n";
        String message3 = "Hide specific WORDS from messages by entering one or more WORDS.\n" +
                                    "Put whitespace in between each WORD.\n" +
                                    "Enter \'Q\' when finished.\n";
        
        if (filterType == 1)        System.out.print(message1);
        else if (filterType == 2)   System.out.print(message2);
        else                        System.out.print(message3);
        
        String[] queries = new String[0];
        List<String> list = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));)
        {
            String s = "";
            while ((s = br.readLine()) != null)
            {
                if (s.isEmpty())
                {
                    System.out.println("You entered nothing. Please try again.");
                    continue;
                }
                
                if (s.equalsIgnoreCase("Q"))
                {
                    if (list.size() > 0)
                    {
                        queries = list.toArray(new String[list.size()]);
                        break;
                    }
                    else
                    {
                        if (filterType == 1)
                            System.out.println("Please enter a username.");
                        else if (filterType == 2)
                            System.out.println("Please enter a keyword.");
                        else
                            System.out.println("Please enter one or more hidden words.");
                        continue;
                    }
                }
                
                String[] words = s.split(" ");
                
                if (filterType == 1 || filterType == 2)
                {
                    queries = new String[1];
                    queries[0] = words[0];
                    break;
                }
                else
                {
                    Collections.addAll(list, words);
                    System.out.println("Enter some more words or \'Q\' to finish.");
                }
            }
            return queries;
        }
    }
}
