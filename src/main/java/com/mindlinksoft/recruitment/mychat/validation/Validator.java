package com.mindlinksoft.recruitment.mychat.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dpana on 3/21/2017.
 */

public class Validator {

    public static boolean checkValidCC(String ccNumber) {
        // Please check http://stackoverflow.com/questions/20740444/check-credit-card-validity-using-luhn-algorithm
        // This is the Luhn algorithm for checking valid credit cards
        int sum = 0;
        boolean alternate = false;
        for (int i = ccNumber.length() - 1; i >= 0; i--)
        {
            int n = Integer.parseInt(ccNumber.substring(i, i + 1));
            if (alternate)
            {
                n *= 2;
                if (n > 9)
                {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }

    public static boolean checkPhoneNo (String phoneNo) {
        Pattern pattern = Pattern.compile("\\d{3}-\\d{7}"); // checks for the XXX-XXXXXXX phone number format, e.g. 210-1234567
        Matcher matcher = pattern.matcher(phoneNo);

        if (matcher.matches())
            return true;
        return false;
    }

}
