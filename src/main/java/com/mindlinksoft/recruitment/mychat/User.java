/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindlinksoft.recruitment.mychat;

/**
 *
 * @author Sofoklis
 */
public class User {
    /**
     * 
     */
    private String username;

    /**
     * Initializes a new instance of the {@link User} class.
     * @param username The username.
     */
    public User(String username) {
        this.setUsername(username);
    }

    /**
     * Username getter method
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Username setter method.
     * @param username 
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    
}
