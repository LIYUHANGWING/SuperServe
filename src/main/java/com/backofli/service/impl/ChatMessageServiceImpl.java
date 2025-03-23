package com.backofli.service.impl;

import com.backofli.pojo.ChatMessage;
import com.backofli.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void saveMessage(ChatMessage message) {
        String sql = "INSERT INTO messages (sender_id, receiver_id, sender_name, receiver_name, message_text, sent_at, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        jdbcTemplate.update(sql,
            message.getSenderId(),
            message.getReceiverId(),
            message.getSenderName(),
            message.getReceiverName(),
            message.getMessageText(),
            message.getSentAt(),
            message.getStatus().name()
        );
    }

    @Override
    public void sendMessage(String targetUserId, String message) {
        // 实现发送消息的逻辑
    }

    @Override
    public void handleIncomingMessage(String senderId, String message) {
        // 实现处理接收消息的逻辑
    }

    @Override
    public List<ChatMessage> getHistoryMessages(Integer userId, Integer targetUserId, int limit, int offset) {
        String sql = "SELECT * FROM messages " +
                    "WHERE (sender_id = ? AND receiver_id = ?) " +
                    "   OR (sender_id = ? AND receiver_id = ?) " +
                    "ORDER BY sent_at DESC " +
                    "LIMIT ? OFFSET ?";
        
        return jdbcTemplate.query(sql,
            new BeanPropertyRowMapper<>(ChatMessage.class),
            userId, targetUserId, targetUserId, userId, limit, offset
        );
    }

    @Override
    @Transactional
    public void updateMessageStatus(Long messageId, ChatMessage.MessageStatus status) {
        String sql = "UPDATE messages SET status = ? WHERE id = ?";
        jdbcTemplate.update(sql, status.name(), messageId);
    }

    @Override
    public List<ChatMessage> searchMessages(Integer userId, String keyword, int limit, int offset) {
        String sql = "SELECT * FROM messages " +
                    "WHERE (sender_id = ? OR receiver_id = ?) " +
                    "AND message_text LIKE ? " +
                    "ORDER BY sent_at DESC " +
                    "LIMIT ? OFFSET ?";
        
        return jdbcTemplate.query(sql,
            new BeanPropertyRowMapper<>(ChatMessage.class),
            userId, userId, "%" + keyword + "%", limit, offset
        );
    }

    @Override
    @Transactional
    public void deleteMessage(Long messageId, Integer userId) {
        String sql = "DELETE FROM messages " +
                    "WHERE id = ? AND (sender_id = ? OR receiver_id = ?)";
        jdbcTemplate.update(sql, messageId, userId, userId);
    }

    @Override
    public int getUnreadMessageCount(Integer userId) {
        String sql = "SELECT COUNT(*) FROM messages " +
                    "WHERE receiver_id = ? AND status = 'SENT'";
        return jdbcTemplate.queryForObject(sql, Integer.class, userId);
    }

    @Override
    @Transactional
    public void markAllAsRead(Integer userId, Integer senderId) {
        String sql = "UPDATE messages " +
                    "SET status = 'READ' " +
                    "WHERE receiver_id = ? AND sender_id = ? AND status = 'SENT'";
        jdbcTemplate.update(sql, userId, senderId);
    }
} 