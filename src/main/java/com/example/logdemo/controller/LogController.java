package com.example.logdemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("/api")
public class LogController {

    private final Random random = new Random();

    @GetMapping("/hello")
    public Map<String, String> hello(@RequestParam(defaultValue = "World") String name) {
        log.info("Hello endpoint called with name: {}", name);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello, " + name + "!");
        response.put("timestamp", String.valueOf(System.currentTimeMillis()));

        return response;
    }

    @PostMapping("/users")
    public Map<String, Object> createUser(@RequestBody Map<String, String> user) {
        String userId = "user_" + random.nextInt(10000);

        log.info("Creating new user: userId={}, username={}", userId, user.get("username"));
        log.debug("User details: {}", user);

        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("username", user.get("username"));
        response.put("status", "created");

        return response;
    }

    @GetMapping("/simulate/{level}")
    public Map<String, String> simulateLog(@PathVariable String level,
                                           @RequestParam(defaultValue = "Test message") String message) {
        switch (level.toUpperCase()) {
            case "TRACE":
                log.trace("TRACE level log: {}", message);
                break;
            case "DEBUG":
                log.debug("DEBUG level log: {}", message);
                break;
            case "INFO":
                log.info("INFO level log: {}", message);
                break;
            case "WARN":
                log.warn("WARN level log: {}", message);
                break;
            case "ERROR":
                log.error("ERROR level log: {}", message);
                break;
            default:
                log.info("Default INFO level log: {}", message);
        }

        Map<String, String> response = new HashMap<>();
        response.put("level", level);
        response.put("message", message);
        response.put("status", "logged");

        return response;
    }

    @GetMapping("/simulate-error")
    public Map<String, String> simulateError() {
        try {
            log.info("Attempting risky operation...");

            // 의도적으로 예외 발생
            if (random.nextBoolean()) {
                throw new RuntimeException("Simulated error occurred!");
            }

            log.info("Risky operation completed successfully");

            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            return response;

        } catch (Exception e) {
            log.error("Error occurred during operation: {}", e.getMessage(), e);

            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", e.getMessage());
            return response;
        }
    }

    @GetMapping("/stress-test")
    public Map<String, Object> stressTest(@RequestParam(defaultValue = "100") int count) {
        log.info("Starting stress test with {} iterations", count);

        int errorCount = 0;
        int warnCount = 0;

        for (int i = 0; i < count; i++) {
            int logType = random.nextInt(4);

            switch (logType) {
                case 0:
                    log.debug("Debug message #{}", i);
                    break;
                case 1:
                    log.info("Info message #{}", i);
                    break;
                case 2:
                    log.warn("Warning message #{}: potential issue detected", i);
                    warnCount++;
                    break;
                case 3:
                    log.error("Error message #{}: critical issue occurred", i);
                    errorCount++;
                    break;
            }
        }

        log.info("Stress test completed: total={}, errors={}, warnings={}",
                count, errorCount, warnCount);

        Map<String, Object> response = new HashMap<>();
        response.put("total", count);
        response.put("errors", errorCount);
        response.put("warnings", warnCount);
        response.put("status", "completed");

        return response;
    }
}