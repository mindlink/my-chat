package com.mindlinksoft.recruitment.mychat.utils;

public class UtilOperations {
    public static String getCommandLineDataInput(String[] arr){
        StringBuilder result = new StringBuilder();
        for(int i = 2 ;i<arr.length; i++) result.append(" ").append(arr[i]);
        return result.toString().trim();
    }
}


