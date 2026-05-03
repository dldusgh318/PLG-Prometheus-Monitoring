package com.example.logdemo.controller;

import com.example.logdemo.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/normal")
    public ResponseEntity<String> normalOrder() {
        orderService.processNormalOrder();
        return ResponseEntity.ok("주문 성공");
    }

    @GetMapping("/slow")
    public ResponseEntity<String> slowOrder() {
        orderService.processSlowOrder();
        return ResponseEntity.ok("주문 성공 (단, 엄청 느림)");
    }

    @GetMapping("/error")
    public ResponseEntity<String> errorOrder() {
        orderService.processErrorOrder();
        // 서비스에서 Exception이 터지므로 500 에러가 반환됨
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문 실패");
    }
}
