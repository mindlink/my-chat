package mychat;

import java.util.Collection;
import java.util.TreeMap;

public class ReportCreator implements IReportCreator {
    ReportOptions userReportOptions;
    public ReportCreator(ReportOptions reportOptions) {
        this.userReportOptions = reportOptions;
    }

    public TreeMap<String, Integer> produceActiveUsersReport(Collection<Message> listOfMessages) {
        TreeMap<String, Integer> activeUsers = new TreeMap<String, Integer>();

        for(Message message: listOfMessages) {
            if (!activeUsers.containsKey(message.getSenderId())) {
                activeUsers.put(message.getSenderId(), 1);
            }
            else {
                int count = activeUsers.get(message.getSenderId());
                activeUsers.put(message.getSenderId(), count + 1);
            }
        }
        return activeUsers;
    }

}
