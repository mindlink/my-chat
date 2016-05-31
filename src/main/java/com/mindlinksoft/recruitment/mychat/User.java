/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindlinksoft.recruitment.mychat;

import java.util.Comparator;

/**
 *
 * @author Michael
 */
/**
 * Represents a User.
 */
public final class User implements Comparable {

    /**
     * The User name.
     */
    private String name;

    /**
     * The User activity.
     */
    private int activity;

    /**
     * Initialises a new instance of the {@link User} class.
     *
     * @param name The name of the User.
     */
    public User(String name) {
        this.name = name;
        activity = 1;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    public int getActivity() {
        return activity;
    }

    public String getName() {
        return name;
    }

    public int compareTo(Object o) {
        if (!(o instanceof User)) {
            throw new ClassCastException();
        }

        User e = (User) o;

        return name.compareTo(e.getName());
    }

    static class ActivityComparator implements Comparator {

    public int compare(Object o1, Object o2) {
        if (!(o1 instanceof User) || !(o2 instanceof User)) {
            throw new ClassCastException();
        }

        User e1 = (User) o1;
        User e2 = (User) o2;

        return (int) (e1.getActivity() - e2.getActivity());
    }
}
}
