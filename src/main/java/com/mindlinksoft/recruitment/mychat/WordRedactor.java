package com.mindlinksoft.recruitment.mychat;



/**
 * Represents a word-redaction processor
 *
 * Responsibilities: Remove words specified in the blacklist from
 *                  the conversation given and store the result
 *
 * NB: Does not mutate given conversation. (Creates shallow copy)
 */

public class WordRedactor implements ProcessingCapable {

    private Conversation receivedConversation;
    private Conversation processedConversation;
    private String[] blackList;

    public WordRedactor(Conversation conversation, String[] blackList) {
        this.receivedConversation = conversation;
        this.blackList = blackList;
        this.processedConversation = new Conversation(conversation.name);
    }

    /**
     * Function that censors the given {@code conversation} by words in a blacklist, as found in {@code config}
     * The function is case sensitive
     */

    @Override
    public void process() {
        if(this.blackList != null){
                for(Message msg: this.receivedConversation.messages){
                    Message tempMessage = new Message(msg.timestamp, msg.senderId, msg.content);

                    for(String word:this.blackList) {
                        if (tempMessage.content.contains(word)) {
                            tempMessage.content = tempMessage.content.replaceAll("(?i)" + word, "*redacted*");
                        }
                    }
                    this.processedConversation.messages.add(tempMessage);
                }
        }
    }


    @Override
    public Conversation getProcessedConversation() {
        return this.processedConversation;
    }
}
