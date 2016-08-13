package main.java.com.mindlinksoft.recruitment.mychat.message.processors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ProcessorsCollection {
	private List<IMessageProcessor> allProcessors = new ArrayList<IMessageProcessor>();
	private List<IDataCollector<Map<String, Integer>>> stringToIntCollectors = 
			new ArrayList<IDataCollector<Map<String, Integer>>>();
	
	public boolean addPlainProcessor(IMessageProcessor processor){
		return allProcessors.add(processor);
	}

	public boolean addStringToIntCollector(IDataCollector<Map<String, Integer>> collector){
		boolean allSuccess = allProcessors.add(collector);
		boolean stringToIntSuccess = stringToIntCollectors.add(collector);
		return allSuccess && stringToIntSuccess;
	}
	
	public Collection<IMessageProcessor> getAllProcessors(){
		return this.allProcessors;
	}
	
	public Collection<IDataCollector<Map<String, Integer>>> getStringToIntCollectors(){
		return this.stringToIntCollectors;
	}
}
