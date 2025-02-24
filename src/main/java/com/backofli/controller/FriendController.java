package com.backofli.controller;
import com.backofli.pojo.User;
import com.backofli.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class FriendController {

    @Autowired
    private UserService userService;

    // 添加好友
    @PostMapping("/add")
    public String addFriend(@RequestParam String userId, @RequestParam String friendId) {
        try {
            userService.addFriend(userId, friendId);
            return "好友添加成功";
        } catch (Exception e) {
            return "好友添加失败：" + e.getMessage();
        }
    }

    // 获取好友列表
    @GetMapping("/{userId}")
    public List<User> getFriends(@PathVariable String userId) {
        return userService.getFriends(userId);
    }
}
