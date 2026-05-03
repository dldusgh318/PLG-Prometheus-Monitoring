package com.example.logdemo.controller;

import com.example.logdemo.service.UserActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/trace")
@RequiredArgsConstructor
public class UserActivityController {

    private final UserActivityService activityService;

    @PostMapping("/user")
    public String createUser(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        activityService.registerUser(username);
        return "User created: " + username;
    }

    @GetMapping("/purchase")
    public String buy(@RequestParam String username, @RequestParam String item) {
        activityService.purchaseItem(username, item);
        return "Purchase processed for " + username;
    }
}
