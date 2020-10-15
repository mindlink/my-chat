package com.mindlinksoft.recruitment.mychat;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the report that can be requested by the user.
 */
public class Report {

    /**
     * Represents a report metric, namely the activity of a user which is
     * a list of the activities of each user. By activity we mean frequency of messages.
     */
    private List<UserActivity> userActivityMetric;

    /**
     * The following two fields demonstrate the scalability of the report class.
     * They are not needed at current point of the program. 07.10.2020
     */
    private List<Object> ActivityMetric2;

    private List<Object> ActivityMetric3;

    /**
     * Initializes a new instance of the {@link Report} class.
     *
     * @param metric1 The first metric
     */
    public Report(List<UserActivity> metric1) {
        this.userActivityMetric = metric1;
    }


    public List<UserActivity> getUserActivityMetric() {
        return userActivityMetric;
    }

    public void setUserActivityMetric(List<UserActivity> userActivityMetric) {
        this.userActivityMetric = userActivityMetric;
    }

}


















