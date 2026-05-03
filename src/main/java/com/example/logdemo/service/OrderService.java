package com.example.logdemo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class OrderService {

    private final Random random = new Random();

    // 1. 정상 요청 (트래픽 및 정상 로그 확인용)
    public void processNormalOrder() {
        log.info("[ORDER-001] 정상 주문 처리가 완료되었습니다.");
    }

    // 2. 지연 요청 (p99 Latency 스파이크 확인용)
    public void processSlowOrder() {
        try {
            log.warn("[ORDER-002] DB 커넥션 풀 부족으로 인한 응답 지연 발생!");
            // 1초 ~ 3초 사이의 랜덤한 지연 발생
            Thread.sleep(1000 + random.nextInt(2000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // 3. 에러 요청 (5XX 에러율 및 Error 로그 확인용)
    public void processErrorOrder() {
        log.error("[ORDER-003] PG사(결제사) API 타임아웃으로 결제 실패!");
        throw new RuntimeException("외부 결제 API 응답 없음");
    }
}