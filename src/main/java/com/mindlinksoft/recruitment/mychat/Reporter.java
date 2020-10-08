package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;

public class Reporter {
    /**
     * Takes the conversation and adds activity report.
     * @param conversation The conversation to add activity
     */
    public Conversation recordActivity(Conversation convo) {
        List<Report> activityReports = new ArrayList<Report>();
        List<Message> messageArray = new ArrayList<Message>(convo.messages);

        for(Message message : messageArray) {
            boolean isReport = false;
            for(Report report : activityReports) {
                if(report.senderId.equals(message.senderId)) {
                    isReport = true;
                    report.count += 1;
                }
            }

            if(!isReport) {
                activityReports.add(new Report(message.senderId, 1));
            }
        }

        convo.activity = sortActivityReports(activityReports);

        return convo;
    }

    /**
     * Sorts the activity reports into descending order.
     * I did insertion sort here.
     * @param activityReport the list of reports to be sorted.
     */
    private List<Report> sortActivityReports(List<Report> activityReport) { 
        for (int i = 1; i < activityReport.size(); i++) 
        {  
            Report key = activityReport.get(i);  
            int j = i - 1;  
  
            while (j >= 0 && activityReport.get(j).count < key.count) 
            {  
                activityReport.set(j + 1, activityReport.get(j));  
                j = j - 1;  
            }  
            activityReport.set(j + 1, key);  
        }
        
        return activityReport;
    }
}