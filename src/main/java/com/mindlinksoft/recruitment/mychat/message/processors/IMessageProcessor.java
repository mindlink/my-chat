package main.java.com.mindlinksoft.recruitment.mychat.message.processors;

import main.java.com.mindlinksoft.recruitment.mychat.message.Message;

public interface IMessageProcessor {
	public void process(Message message);
}