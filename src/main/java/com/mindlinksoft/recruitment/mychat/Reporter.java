package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a report generator.
 * Analyzes the conversation given using metrics.
 *
 * Does not mutate the given conversation
 */
public class Reporter {

    private Conversation conversation;
    private Report report;


    public Reporter(Conversation conversation) {
        this.conversation = conversation;
        this.report = new Report(new ArrayList<UserActivity>());
    }


    public void generateReport(){
        generateMetric1();
    }


    /**
     * Function that generates the {@code UserActivity} metric, by analyzing the {@code conversation}
     *
     * @return {@code loopList}
     *         Returns a list of UserActivity objects, each one containing
     *         a user name and the frequency of their respective messages.
     */
    protected void generateMetric1() {
        List<UserActivity> loopList = new ArrayList<>();

        //First add all unique users to list
        boolean loopFlag = false;
        for (Message msg : this.conversation.messages) {
            loopFlag = false;
            for (UserActivity item : loopList) {
                if (item.getSender().equals(msg.senderId)) {
                    loopFlag = true;
                    break;
                }
            }
            if (!loopFlag) {
                loopList.add(new UserActivity(msg.senderId, 0));
            }
        }

        //Then iterate through conversation to track frequency per user
        for (Message msg : this.conversation.messages) {
            for (UserActivity item : loopList) {
                if (item.getSender().equals(msg.senderId)) {
                    Integer count = item.getCount();
                    item.setCount(count + 1);
                }
            }
        }

        this.report.setUserActivityMetric(loopList);
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}
