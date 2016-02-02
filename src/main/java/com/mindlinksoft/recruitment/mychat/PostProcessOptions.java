package mychat;

import java.util.ArrayList;

public class PostProcessOptions {

    private ArrayList<String> hideWords;
    private boolean hideCreditCard;
    private boolean obfuscate;
    private String userIdToBeObfuscated;
    private String userIdToBeObfuscatedWith;

    public void addToHideWords (String newWord) {
        if (hideWords == null) {
            hideWords = new ArrayList<String>();
        }
        hideWords.add(newWord);
    }

    public void setHideCreditCard(boolean hideCreditCard) {
        this.hideCreditCard = hideCreditCard;
    }

    public void setObfuscate(boolean obfuscate) {
        this.obfuscate = obfuscate;
    }

    public void setUserIdToBeObfuscated(String userIdToBeObfuscated) {
        this.userIdToBeObfuscated = userIdToBeObfuscated;
    }

    public void setUserIdToBeObfuscatedWith(String userIdToBeObfuscatedWith) {
        this.userIdToBeObfuscatedWith = userIdToBeObfuscatedWith;
    }

    public ArrayList<String> getHideWords() {

        return hideWords;
    }

    public String getUserIdToBeObfuscatedWith() {
        return userIdToBeObfuscatedWith;
    }

    public String getUserIdToBeObfuscated() {
        return userIdToBeObfuscated;
    }

    public boolean isObfuscate() {
        return obfuscate;
    }

    public boolean isHideCreditCard() {
        return hideCreditCard;
    }



}
