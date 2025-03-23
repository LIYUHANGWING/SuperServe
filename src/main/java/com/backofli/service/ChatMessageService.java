package com.backofli.service;

import com.backofli.pojo.ChatMessage;
import java.util.List;

public interface ChatMessageService {
    // 保存消息
    void saveMessage(ChatMessage message);
    
    // 发送消息
    void sendMessage(String targetUserId, String message);
    
    // 处理接收消息
    void handleIncomingMessage(String senderId, String message);
    
    // 获取历史消息
    List<ChatMessage> getHistoryMessages(Integer userId, Integer targetUserId, int limit, int offset);
    
    // 更新消息状态
    void updateMessageStatus(Long messageId, ChatMessage.MessageStatus status);
    
    // 查询消息
    List<ChatMessage> searchMessages(Integer userId, String keyword, int limit, int offset);
    
    // 删除消息
    void deleteMessage(Long messageId, Integer userId);
    
    // 获取未读消息数量
    int getUnreadMessageCount(Integer userId);
    
    // 标记所有消息为已读
    void markAllAsRead(Integer userId, Integer senderId);
} 