package com.mindlinksoft.recruitment.mychat;

/**
 * Created by dpana on 3/21/2017.
 */
// Please check http://stackoverflow.com/questions/20740444/check-credit-card-validity-using-luhn-algorithm
// This is the Luhn algorithm for checking valid credit cards

public class Luhn
{
    public static boolean checkValidCC(String ccNumber)
    {
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
}
