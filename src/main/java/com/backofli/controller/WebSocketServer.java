package com.backofli.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/apis/websocket/{user_id}")
@Component
public class WebSocketServer {
    private static final Logger L = LoggerFactory.getLogger(WebSocketServer.class);

    // 存储所有用户的会话
    private static final Map<String, Session> userSessions = new ConcurrentHashMap<>();

    private String userId;
    private PreparedStatement setInteger;

    @OnOpen
    public void onOpen(Session session, @PathParam("user_id") String userId) {
        this.userId = userId;
        userSessions.put(userId, session); // 将用户会话加入到Map中
        System.out.println("用户 " + userId + " 连接建立");
    }

    @OnClose
    public void onClose() {
        userSessions.remove(userId); // 移除用户会话
        System.out.println("用户 " + userId + " 连接关闭");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            System.out.println("收到用户 " + userId + " 的消息: " + message);

            // 解析消息，假设消息格式为 "targetUserId:messageContent"
            String[] parts = message.split(":", 2);
            if (parts.length != 2) {
                session.getBasicRemote().sendText("消息格式错误。请使用 'targetUserId:messageContent'");
                return;
            }

            String targetUserId = parts[0]; // 目标用户ID
            String messageContent = parts[1]; // 消息内容

            // 查找目标用户的会话
            Session targetSession = userSessions.get(targetUserId);
            if (targetSession != null && targetSession.isOpen()) {
                // 发送消息给目标用户
                targetSession.getBasicRemote().sendText("来自用户 " + userId + " 的消息: " + messageContent);
            } else {
                session.getBasicRemote().sendText("用户 " + targetUserId + " 不在线。");
            }

            // 保存消息到数据库，无论目标用户是否在线
            insertMessageToDatabase(userId, targetUserId, messageContent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertMessageToDatabase(String senderId, String receiverId, String message) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            // 数据库连接信息
            String url = "jdbc:mysql://localhost:3306/acca";
            String username = "root";
            String password = "123456";

            // 建立连接
            conn = DriverManager.getConnection(url, username, password);

            // 创建SQL语句

            //这里的sender_id,receiver_id根据每个用户独立生成一个id
            String sql = "INSERT INTO messages (sender_id,receiver_id,sender_name, receiver_name, message_text, sent_at) VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,2333);
            pstmt.setInt(2,2343);

            pstmt.setString(3, senderId);
            pstmt.setString(4, receiverId);
            pstmt.setString(5, message);
            pstmt.setTimestamp(6, new java.sql.Timestamp(new Date().getTime()));

            // 执行插入操作
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
