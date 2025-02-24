package com.backofli.controller;
import com.backofli.pojo.User;
import com.backofli.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 用户注册
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        try {
            userService.createUser(user);
            return "注册成功";
        } catch (Exception e) {
            return "注册失败：" + e.getMessage();
        }
    }

    // 用户登录
    @PostMapping("/logins")
    public String login(@RequestBody User user) {
        User existingUser = userService.getUserById(user.getUserId());
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            return "登录成功";
        } else {
            return "登录失败：用户名或密码错误";
        }
    }

    // 获取所有用户
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}