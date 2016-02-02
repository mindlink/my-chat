package mychat;

import java.util.logging.Filter;

public class FilterHelper implements IFilterBehaviour {

    FilterOptions conversationFilterOptions;

    public FilterHelper(FilterOptions filter) {
        this.conversationFilterOptions = filter;
    }

    public boolean filterMessage(Message message) {
        if (checkUser(message.getSenderId(), conversationFilterOptions.getUserId()) && checkKeyword(message.getContent(), conversationFilterOptions.getKeyword())) {

            return true;
        }

        else  {
            return false;
        }
    }

    private boolean checkUser(String senderId, String user) {
        if (user == null) {
         return true;
        }

        else if (senderId.equals(user)) {
            return  true;
        }

        else {
            return false;
        }
    }

    private boolean checkKeyword(String content, String keyword) {
        if (keyword == null) {
            return true;
        }

        else if (content.contains(keyword)) {
            return true;
        }
        else {
            return false;
        }
    }
}
