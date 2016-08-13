package main.java.com.mindlinksoft.recruitment.mychat.message.processors;

public interface IDataCollector<T> extends IMessageProcessor{
	public String getTitle();
	public T extractData();
}
