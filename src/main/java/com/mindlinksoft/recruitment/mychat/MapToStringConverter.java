package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

//Converts a given String Int Map to a String ready for JSON
public class MapToStringConverter {
	public static String convertMapToString(Map<String, Integer> messageCountMap) {
        List<Tuple> sortedUsernames = convertMapToSortedList(messageCountMap);
        
        String frequencyList = "Most Active Users: ";
        
        for (Tuple user : sortedUsernames) {
        	if (sortedUsernames.indexOf(user) == sortedUsernames.size() - 1) {
        		frequencyList += String.format("%s (%d).", user.word, user.count);
        	} else {
        		frequencyList += String.format("%s (%d), ", user.word, user.count);
        	}
        }
        
        return frequencyList;
    }
    
    private static List<Tuple> convertMapToSortedList(Map<String, Integer> map) {
    	List<Tuple> al = new ArrayList<Tuple>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            al.add(new Tuple(entry.getValue(), entry.getKey()));
        }
        
        Collections.sort(al);
        return al;
    }   
    
    private static class Tuple implements Comparable<Tuple> {
        private int count;
        private String word;

        public Tuple(int count, String word) {
            this.count = count;
            this.word = word;
        }

        @Override
        public int compareTo(Tuple o) {
            return new Integer(o.count).compareTo(this.count);
        }
        public String toString() {
            return word + " " + count;
        }
    }
}
