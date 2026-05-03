package com.example.logdemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@Slf4j
@RestController
@RequestMapping("/api/stress")
public class StressTestController {

    private final Random random = new Random();

    @GetMapping("/run")
    public String runStress(@RequestParam(defaultValue = "100") int count) {
        log.info("🚀 스트레스 테스트 시작: 총 {}건의 로그 생성", count);

        for (int i = 0; i < count; i++) {
            int type = random.nextInt(10); // 0~9 랜덤값

            if (type < 7) {
                // 70% 확률로 일반 로그
                log.info("[STRESS] 정상 처리 프로세스 #{}", i);
            } else if (type < 9) {
                // 20% 확률로 경고 로그 (응답 지연 시뮬레이션)
                log.warn("[STRESS] 시스템 부하 감지 - 처리 지연 중... #{}", i);
            } else {
                // 10% 확률로 에러 로그
                log.error("[STRESS] 알 수 없는 시스템 예외 발생! #{}", i);
            }
        }

        log.info("🏁 스트레스 테스트 종료");
        return count + " 개의 로그가 생성되었습니다. 그라파나를 확인하세요!";
    }
}