package com.mindlinksoft.recruitment.mychat;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a helper to parse command line arguments.
 */
public final class CommandLineArgumentParser {
    /**
     * Parses the given {@code arguments} into the exporter configuration.
     * @param arguments The command line arguments.
     * @return The exporter configuration representing the command line arguments.
     */
    public ConversationExporterConfiguration parseCommandLineArguments(String[] arguments) throws Exception {
        if (arguments.length > 0) {
            String joinedArgs = String.join(" ", arguments);

            char[] parameterNames = {'i', 'o', 'u', 'w', 'b'}; //maybe it's better to make it a class member
            String[] parameterValues = new String[parameterNames.length];
            for (int i=0; i<parameterNames.length;i++)
            {
                Pattern p = Pattern.compile("-"+parameterNames[i]+"\\s+['\"]?([[.][^'\"-]]*\\b)['\"]?");
                Matcher m = p.matcher(joinedArgs);
                if (m.find()) {
                    parameterValues[i] = m.group(1);
                    //System.out.println(m.group(1));
                }
            }

            return new ConversationExporterConfiguration(parameterValues[0], parameterValues[1], parameterValues[2], parameterValues[3], parameterValues[4], joinedArgs.contains("-c"), joinedArgs.contains("-p"), joinedArgs.contains("-s"));
        } else {
            /* Arguments are passed in the form -i "in.txt" (quotation marks are optional).
            *   -i      input file
            *   -o      output file
            *   -u      user whose messages should be exported
            *   -w      keyword/phrase for messages to be filtered by to be exported
            *   -b      blacklist file containing a list of regular expressions to be filtered out
            *   -c      enables the hiding of credit card numbers
            *   -p      enables the hiding of phone numbers
            *   -s      obfuscate usernames
            * */
            System.out.println("Argument list is empty. Correct usage: mychat.exe -i 'in.txt' -o out.txt -u bob -w 'pie society' -b blacklist.txt -c -p -s");
            return new ConversationExporterConfiguration("in.txt", "out.txt", "", "", "blacklist.txt", false, false, false);
        }
    }
}
