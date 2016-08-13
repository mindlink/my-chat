package main.java.com.mindlinksoft.recruitment.mychat.message.filters;

import main.java.com.mindlinksoft.recruitment.mychat.message.Message;

public interface IMessageFilter {
	boolean validate(Message candidate);
}