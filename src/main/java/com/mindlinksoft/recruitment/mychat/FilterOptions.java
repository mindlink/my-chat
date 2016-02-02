package mychat;

public class FilterOptions {

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {

        return keyword;
    }

    public String getUserId() {
        return userId;
    }

    private String userId;
    private String keyword;
}
