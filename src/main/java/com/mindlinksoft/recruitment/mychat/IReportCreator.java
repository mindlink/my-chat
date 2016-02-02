package mychat;

import java.util.Collection;
import java.util.TreeMap;

public interface IReportCreator {

    public TreeMap<String, Integer> produceActiveUsersReport(Collection<Message> listOfMessages);

}
