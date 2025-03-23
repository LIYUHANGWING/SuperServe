package com.backofli.controller;

import com.backofli.pojo.ChatMessage;
import com.backofli.pojo.Result;
import com.backofli.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class ChatMessageController {

    @Autowired
    private ChatMessageService chatMessageService;

    // 获取历史消息
    @GetMapping("/history")
    public Result getHistoryMessages(
            @RequestParam Integer userId,
            @RequestParam Integer targetUserId,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "0") int offset) {
        List<ChatMessage> messages = chatMessageService.getHistoryMessages(userId, targetUserId, limit, offset);
        return Result.success(messages);
    }

    // 更新消息状态
    @PutMapping("/{messageId}/status")
    public Result updateMessageStatus(
            @PathVariable Long messageId,
            @RequestParam ChatMessage.MessageStatus status) {
        chatMessageService.updateMessageStatus(messageId, status);
        return Result.success();
    }

    // 搜索消息
    @GetMapping("/search")
    public Result searchMessages(
            @RequestParam Integer userId,
            @RequestParam String keyword,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "0") int offset) {
        List<ChatMessage> messages = chatMessageService.searchMessages(userId, keyword, limit, offset);
        return Result.success(messages);
    }

    // 删除消息
    @DeleteMapping("/{messageId}")
    public Result deleteMessage(
            @PathVariable Long messageId,
            @RequestParam Integer userId) {
        chatMessageService.deleteMessage(messageId, userId);
        return Result.success();
    }

    // 获取未读消息数量
    @GetMapping("/unread/count")
    public Result getUnreadMessageCount(@RequestParam Integer userId) {
        int count = chatMessageService.getUnreadMessageCount(userId);
        return Result.success(count);
    }

    // 标记所有消息为已读
    @PutMapping("/read-all")
    public Result markAllAsRead(
            @RequestParam Integer userId,
            @RequestParam Integer senderId) {
        chatMessageService.markAllAsRead(userId, senderId);
        return Result.success();
    }
} 