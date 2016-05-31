/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindlinksoft.recruitment.mychat;

import static jdk.nashorn.internal.objects.NativeString.indexOf;

/**
 *
 * @author Michael
 */
public class FilterNumbers extends Filter {

    @Override
    void filterAction(Message message, Object words, Object replacement) {
        final int Amount = 10;  // minimum length of the number to find
        String r = (String) replacement;
        String numberFound;
        
        
        int indexStart = findNextNumber(0,message.content); //find first number

        while (indexStart < message.content.length() - 1) {

            String content = message.content.substring(indexStart, message.content.length()); // String to find the content of the number
            content = content.replaceAll("[ ().,{}_+-]", "");
            int numberIndex = 0;
            
            while (numberIndex<content.length() && content.charAt(numberIndex) >= '0' && content.charAt(numberIndex) <= '9') {  // get the char length of the number
                numberIndex++;
            }
            
            if (numberIndex > Amount) { //if the number is long enough
                numberFound = content.substring(0, numberIndex);
                
                numberIndex = 0;
                int indexEnd = indexStart;
                while (numberIndex < numberFound.length()) {    // find where the number starts/ends in the original String
                    if (numberFound.charAt(numberIndex) == message.content.charAt(indexEnd)) { // move onto the next digit of the found number once located in the original String
                        numberIndex++;
                    }
                    indexEnd++;
                }
                
                String contentToReplace = message.content.substring(indexStart, indexEnd);
                message.content = message.content.replace(contentToReplace, r); // replaces content
                numberIndex = r.length();
            }
            
            indexStart += numberIndex; // skip analysed content
            
            
            indexStart=findNextNumber(indexStart,message.content);
        }
    }

    // Finds the next number in a string starting a given index
    private int findNextNumber(int indexStart, String string) {
        while (!Character.isDigit(string.charAt(indexStart)) && indexStart < string.length() - 1) {
            indexStart++;
        }
        return indexStart;
    }
}
