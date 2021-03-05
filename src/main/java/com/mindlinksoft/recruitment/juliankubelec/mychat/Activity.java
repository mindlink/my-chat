package com.mindlinksoft.recruitment.juliankubelec.mychat;


import java.util.*;

public final class Activity {
    public String getName() {
        return name;
    }

    public Collection<Report> getReports() {
        return reports;
    }

    private String name = "activity";
    private Collection<Report> reports;

    public void extractStats(Conversation conversation) {
        HashSet<Report> reportSet = new HashSet<>();
        List<Message> msgs = (List<Message>) conversation.getMessages();
        for (Message msg : msgs) {
            Conversation c = new Conversation(conversation.getName(), conversation.getMessages()); // copy conversation
            ConversationBuilder cb = new ConversationBuilder(c);
            c = cb.filter().byUser(msg.getSenderId()).build();
            if (c.getMessages().size() > 0) {
                Report r = new Report(msg.getSenderId(), c.getMessages().size());
                reportSet.add(r);
            }
        }
        reports = new ArrayList<>();
        reports.addAll(reportSet);

        ((List<Report>) reports).sort(new SortReport());
        Collections.reverse((List<Report>) reports);
    }

    /**
     * Inner class to provide way to sort the Activity
     *
     */
    static class SortReport implements Comparator<Report>{
        /**
         *
         * @param o1 first report to compare
         * @param o2 second report to compare
         * @return if result is positive, o1 has more messages.
         * If result is 0 they have the same amount. If it's negative
         * o2 is larger
         */
        @Override
        public int compare(Report o1, Report o2) {
            return o1.getCount() - o2.getCount();
        }
    }

}
