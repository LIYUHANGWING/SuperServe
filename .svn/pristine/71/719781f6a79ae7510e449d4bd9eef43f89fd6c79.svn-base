package com.backofli.controller;

import com.backofli.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    @Autowired
    private RedisService redisService;

    @GetMapping("/set")
    public String set(@RequestParam String key, @RequestParam String value) {
        redisService.setValue(key, value);
        return "Set key: " + key + " with value: " + value;
    }

    @GetMapping("/get")
    public String get(@RequestParam String key) {
        String value = redisService.getValue(key);
        return "Value for key " + key + " is: " + value;
    }
}
