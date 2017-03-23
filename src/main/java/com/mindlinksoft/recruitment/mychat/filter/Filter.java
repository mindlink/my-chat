package com.mindlinksoft.recruitment.mychat.filter;

import java.util.regex.Pattern;

/**
 * Created by dpana on 3/22/2017.
 */
public class Filter {

    String message;
    String keyword;
    String user;
    String[] blacklist;

    public Filter(String message, String keyword, String user, String[] blacklist) {
        this.message = message;
        this.keyword = keyword;
        this.user = user;
        this.blacklist = blacklist;
    }

    public void filterBlacklist () {
        if (blacklist != null && blacklist.length > 0) {
            for (String badWord : blacklist) {
                message = message.replaceAll("(?i)" + "\\b" + badWord + "\\b","******");
            }
        }
    }

    public boolean filterByKeyword () {
        return keyword != null && !Pattern.compile("\\b" + Pattern.quote(keyword) + "\\b", Pattern.CASE_INSENSITIVE).matcher(message).find();
    }

    public boolean filterByUser(String currentUser) {
        return user !=null && !user.contentEquals(currentUser);
    }

    public String getMessage() {
        return message;
    }

}
