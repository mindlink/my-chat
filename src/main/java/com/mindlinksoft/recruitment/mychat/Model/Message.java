package com.mindlinksoft.recruitment.mychat.Model;

import java.time.Instant;

/**
 * Represents a chat message.
 */
public final class Message {

    //Note - I've made these properties final as I believe that there should be no reason to change.

    /**
     * The message content.
     */
    public final String content;

    /**
     * The message timestamp.
     */
    public final Instant timestamp;

    /**
     * The message sender.
     */
    public final String senderId;

    /**
     * Initializes a new instance of the {@link Message} class.
     * @param timestamp The timestamp at which the message was sent.
     * @param senderId The ID of the sender.
     * @param content The message content.
     */
    public Message(Instant timestamp, String senderId, String content) {
        this.content = content;
        this.timestamp = timestamp;
        this.senderId = senderId;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
				return true;
			if((obj == null) || (obj.getClass() != this.getClass()))
				return false;

			Message msg = (Message)obj;
			return (senderId == msg.senderId || (senderId != null && senderId.equals(msg.senderId)))
            && (content == msg.content || (content != null && content.equals(msg.content)))
            && (timestamp == msg.timestamp || (timestamp != null && timestamp.equals(msg.timestamp)));
    }

    @Override
    public int hashCode(){
        int hash = 17;
		hash = 31 * hash + (null == content ? 0 : content.hashCode());
		hash = 31 * hash + (null == timestamp ? 0 : timestamp.hashCode());
        hash = 31 * hash + (null == senderId ? 0 : senderId.hashCode());
		return hash;
    }


}
