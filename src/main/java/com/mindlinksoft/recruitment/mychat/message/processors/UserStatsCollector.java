package main.java.com.mindlinksoft.recruitment.mychat.message.processors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.LinkedHashMap;

import main.java.com.mindlinksoft.recruitment.mychat.message.Message;

public class UserStatsCollector implements IDataCollector<Map<String, Integer>> {

	private final String title;
	private Map<String, Integer> sendersActivity = new HashMap<String, Integer>();
	
	public UserStatsCollector(String title){
		this.title = title;	
	}
	
	@Override
	public void process(Message message) {
		String sender = message.getSenderId();
		if (sendersActivity.containsKey(sender)){
			Integer count = sendersActivity.get(sender);
			sendersActivity.put(sender, count+1);
		} else {
			sendersActivity.put(sender, 1);
		}
	}
	
	@Override
	/**
	 * Sorts and returns the statistics gathered so far.
	 */
	public Map<String, Integer> extractData(){
		List<Entry<String,Integer>> entries = 
				new ArrayList<Entry<String,Integer>>(sendersActivity.entrySet());
		
		Collections.sort(entries, new SendersEntriesComparator());
		
		//putting into a map for clarity and for easier serialization
		Map<String, Integer> result = new LinkedHashMap<>(entries.size()); 
		for(Entry<String,Integer> entry : entries){
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	@Override
	public String getTitle() {
		return this.title;
	}
}

class SendersEntriesComparator implements Comparator<Entry<String, Integer>> {

	public int compare(Entry<String, Integer> arg0, Entry<String, Integer> arg1) {
		return Integer.compare(arg1.getValue(), arg0.getValue()); //descending
	}
}