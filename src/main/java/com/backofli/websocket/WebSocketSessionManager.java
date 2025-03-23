package com.backofli.websocket;

import jakarta.websocket.Session;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionManager {
    private static final Map<String, Session> userSessions = new ConcurrentHashMap<>();

    public void addSession(String userId, Session session) {
        userSessions.put(userId, session);
    }

    public void removeSession(String userId) {
        userSessions.remove(userId);
    }

    public Session getSession(String userId) {
        return userSessions.get(userId);
    }

    public boolean sendMessage(String userId, String message) {
        Session session = userSessions.get(userId);
        if (session != null && session.isOpen()) {
            try {
                session.getBasicRemote().sendText(message);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public boolean isUserOnline(String userId) {
        Session session = userSessions.get(userId);
        return session != null && session.isOpen();
    }
} 