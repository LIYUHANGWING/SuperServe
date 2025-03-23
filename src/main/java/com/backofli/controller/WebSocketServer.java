package com.backofli.controller;

import com.backofli.pojo.ChatMessage;
import com.backofli.service.ChatMessageService;
import com.backofli.websocket.WebSocketSessionManager;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@ServerEndpoint(value = "/apis/websocket/{user_id}")
@Component
public class WebSocketServer {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    
    private static WebSocketSessionManager sessionManager;
    private static ChatMessageService chatMessageService;
    
    private String userId;

    @Autowired
    public void setWebSocketSessionManager(WebSocketSessionManager manager) {
        WebSocketServer.sessionManager = manager;
    }

    @Autowired
    public void setChatMessageService(ChatMessageService service) {
        WebSocketServer.chatMessageService = service;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("user_id") String userId) {
        this.userId = userId;
        sessionManager.addSession(userId, session);
        logger.info("用户 {} 连接建立", userId);
    }

    @OnClose
    public void onClose() {
        sessionManager.removeSession(userId);
        logger.info("用户 {} 连接关闭", userId);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            logger.info("收到用户 {} 的消息: {}", userId, message);

            // 解析消息，格式为 "targetUserId:messageContent"
            String[] parts = message.split(":", 2);
            if (parts.length != 2) {
                session.getBasicRemote().sendText("消息格式错误。请使用 'targetUserId:messageContent'");
                return;
            }

            String targetUserId = parts[0];
            String messageContent = parts[1];

            // 创建消息对象
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setSenderId(Integer.parseInt(userId));
            chatMessage.setReceiverId(Integer.parseInt(targetUserId));
            chatMessage.setSenderName(userId);
            chatMessage.setReceiverName(targetUserId);
            chatMessage.setMessageText(messageContent);
            chatMessage.setSentAt(LocalDateTime.now());
            chatMessage.setStatus(ChatMessage.MessageStatus.SENT);

            // 保存消息
            chatMessageService.saveMessage(chatMessage);

            // 如果目标用户在线，发送消息
            if (sessionManager.isUserOnline(targetUserId)) {
                String formattedMessage = String.format("来自用户 %s 的消息: %s", userId, messageContent);
                sessionManager.sendMessage(targetUserId, formattedMessage);
                chatMessage.setStatus(ChatMessage.MessageStatus.DELIVERED);
            } else {
                session.getBasicRemote().sendText("用户 " + targetUserId + " 不在线。");
            }

        } catch (Exception e) {
            logger.error("处理消息时发生错误", e);
            try {
                session.getBasicRemote().sendText("消息处理失败：" + e.getMessage());
            } catch (Exception ex) {
                logger.error("发送错误消息失败", ex);
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("WebSocket错误: ", error);
        try {
            session.close();
        } catch (Exception e) {
            logger.error("关闭WebSocket session失败", e);
        }
    }
}
