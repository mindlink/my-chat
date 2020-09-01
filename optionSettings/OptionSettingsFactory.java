package com.mindlinksoft.recruitment.mychat.optionSettings;

import com.mindlinksoft.recruitment.mychat.Conversation;
import com.mindlinksoft.recruitment.mychat.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * Return a OptionSetting depending on the flag encountered on the commandline.
 */
public class OptionSettingsFactory implements  OptionSetting {

    /**
     * Stores the OptionSettings their flags.
     */
    public static Map<Character, OptionSetting> options = new HashMap<Character, OptionSetting>() {{
        put('U', new Users());
        put('K', new Keywords());
        put('B', new Blacklist());

        put('N', new Numbers());
        put('O', new Obfuscate());
        put('A', new ActivityReport());
    }};

    /**
     * Return the OptionSetting that the flag entered on the commandline corresponds to.
     * @param optionFlag A character representing a flag entered on the commandline that corresponds to an OptionSetting.
     * @return The OptionSetting corresponding to the entered flag.
     */
    public static OptionSetting getOptionSetting(char optionFlag) {

        OptionSetting returnedSetting = options.get(optionFlag);

        if (returnedSetting == null) {
            throw new IllegalArgumentException("Option is invalid:- '**" + optionFlag);
        }

        return returnedSetting;
    }

    @Override
    public Message duringIteration(Message message) {
        return null;
    }

    @Override
    public Conversation postIteration(Conversation conversation) {
        return null;
    }

    @Override
    public void setArgument(String argument) {}

    @Override
    public boolean argumentRequired() {
        return false;
    }
}