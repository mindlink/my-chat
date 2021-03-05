package com.mindlinksoft.recruitment.juliankubelec.mychat;

import java.util.Objects;

public final class Report {
    public String getSender() {
        return sender;
    }

    public int getCount() {
        return count;
    }

    private String sender;
    private int count;

    public Report(String sender, int count){
        this.sender = sender;
        this.count = count;
    }

    /**
     * Auto-generated method to determine if a report object has the same reference
     * @param o The report
     * @return boolean determining if the objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return count == report.count && sender.equals(report.sender);
    }

    /**
     * Auto-generated method to determine if a report object has the same hashcode
     * @return the hash of the object made with the sender name and the number of messages
     */
    @Override
    public int hashCode() {
        return Objects.hash(sender, count);
    }
}
