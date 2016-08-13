package main.java.com.mindlinksoft.recruitment.mychat.message.processors;

import main.java.com.mindlinksoft.recruitment.mychat.message.Message;

public class MessageSenderObfuscator implements IMessageProcessor{

	public void process(Message message) {
		String senderId = message.getSenderId();
		String result = Integer.toString(senderId.hashCode());
		message.setSenderId(result);
	}
	
}
