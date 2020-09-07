package com.mindlinksoft.recruitment.mychat.Constructs;

import java.time.Instant;

public final class Message {

    public String content;
    public Instant timestamp;
    public String senderId;


    public Message(Instant timestamp, String senderId, String content) {
        this.content = content;
        this.timestamp = timestamp;
        this.senderId = senderId;
    }
}
