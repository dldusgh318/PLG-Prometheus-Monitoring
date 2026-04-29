package com.example.logdemo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
@Service
public class UserService {

    private final Random random = new Random();

    public Map<String, Object> processUserData(String username) {
        log.debug("Processing user data for: {}", username);

        // 비즈니스 로직 시뮬레이션
        try {
            Thread.sleep(random.nextInt(100)); // 처리 시간 시뮬레이션

            if (random.nextInt(10) < 2) { // 20% 확률로 경고
                log.warn("User data processing slow for: {}", username);
            }

            Map<String, Object> result = new HashMap<>();
            result.put("username", username);
            result.put("processed", true);
            result.put("timestamp", System.currentTimeMillis());

            log.info("User data processed successfully: {}", username);
            return result;

        } catch (InterruptedException e) {
            log.error("Error processing user data for: {}", username, e);
            Thread.currentThread().interrupt();
            throw new RuntimeException("Processing failed", e);
        }
    }

    public void validateUser(String username) {
        log.debug("Validating user: {}", username);

        if (username == null || username.trim().isEmpty()) {
            log.error("Invalid username provided: null or empty");
            throw new IllegalArgumentException("Username cannot be empty");
        }

        if (username.length() < 3) {
            log.warn("Username too short: {}", username);
        }

        log.info("User validation passed: {}", username);
    }
}