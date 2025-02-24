package com.backofli.pojo;

import lombok.Data;
import java.util.Date;

@Data
public class Message {
    private Long id;
    private String userId;
    private String content;
    private Date timestamp;

    public Message(String userId, String content, Date timestamp) {
        this.userId = userId;
        this.content = content;
        this.timestamp = timestamp;
    }
}
