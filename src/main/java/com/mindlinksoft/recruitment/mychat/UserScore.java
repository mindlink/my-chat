package com.mindlinksoft.recruitment.mychat;

/**
 * Represents a user's score in the rankings
 */
public class UserScore {
    /**
     * The user's name
     */
    public String name;

    /**
     * The user's score
     */
    public int score;

    /**
     * Initializes a new instance of the {@link UserScore} class.
     * @param name The name of the user
     * @param score The user's score
     */
    public UserScore(String name, int score) {
        this.name = name;
        this.score = score;
    }
}
