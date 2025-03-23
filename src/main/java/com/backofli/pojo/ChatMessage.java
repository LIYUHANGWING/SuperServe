package com.backofli.pojo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ChatMessage {
    private Long id;
    private Integer senderId;
    private Integer receiverId;
    private String senderName;
    private String receiverName;
    private String messageText;
    private LocalDateTime sentAt;
    private MessageStatus status;

    public enum MessageStatus {
        SENT,
        DELIVERED,
        READ
    }
} 