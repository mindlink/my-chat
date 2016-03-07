package com.mindlinksoft.recruitment.mychat.filters;

import java.util.ArrayList;
import java.util.Collection;

import com.mindlinksoft.recruitment.mychat.Message;

public class CreditCardPhoneFilter implements ConversationFilter {

	@Override
	public Collection<Message> useFilter(Collection<Message> conversation) {
		
		//Use method from keyword search to find credit card and phone numbers.
		
		//Replace the numbers with "\*redacted\*"
		
		Collection<Message> result = new ArrayList<Message>();
		//Throws exception
		//Make sure conversation and input are not null.
		if(conversation != null){
			for (Message message : conversation){
				String text = message.getContent().toLowerCase();
				//Credit card length = 16, phone number length in the UK = 11
				if (text.matches("\\d{16}") || text.matches("\\d{11}")){
					//Replace text with "\*redacted\*"
					String filteredContent = text.replaceAll("\\d{16}", "\\*redacted\\*");
					message.setContent(filteredContent);
				}
			result.add(message);
						
			}
		}
		return result;	
	}

}
