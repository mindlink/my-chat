package com.mindlinksoft.recruitment.mychat;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class Report {
	
	public static Map<String, Integer> getUserReport(Conversation conversation) {
		System.out.print(Const.MAKING_REPORT);
		
		Map<String, Integer> UserRecord = new TreeMap<String, Integer>();
		
		for(Message m: conversation.getMessages()) {
			String user = m.getSenderId();
			
			if(!UserRecord.containsKey(user)) {
				UserRecord.put(user, 1);
			}
			else {	
				UserRecord.replace(user, UserRecord.get(user) + 1);
			}
		}
		
		UserRecord = sortMapByValue(UserRecord);
		
		System.out.println(Const.COMPLETE);
		return UserRecord;
	}
	
	public static <K, V extends Comparable<V>> Map<K, V> sortMapByValue(final Map<K, V> map) {
	    Comparator<K> valueComparator =  new Comparator<K>() {
	        public int compare(K k1, K k2) {
	            int compare = map.get(k2).compareTo(map.get(k1));
	            if (compare == 0) return 1;
	            else return compare;
	        }
	    };
	    
	    Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
	    sortedByValues.putAll(map);
	    
	    return sortedByValues;
	}
}
