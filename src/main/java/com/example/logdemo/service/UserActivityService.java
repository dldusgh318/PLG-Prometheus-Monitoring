package com.example.logdemo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserActivityService {

    public void registerUser(String username) {
        // [중요] 모든 로그에 username을 포함하여 검색이 가능하게 합니다.
        log.info("[USER-TRACE] 유저 등록 시작: username={}", username);

        // 가상의 로직
        log.info("[USER-TRACE] 유저 권한 설정 완료: username={}", username);
        log.info("[USER-TRACE] 유저 등록 성공: username={}", username);
    }

    public void purchaseItem(String username, String item) {
        log.info("[USER-TRACE] 상품 구매 시도: username={}, item={}", username, item);

        if ("bad-item".equals(item)) {
            log.warn("[USER-TRACE] 구매 실패 - 재고 부족: username={}, item={}", username, item);
        } else {
            log.info("[USER-TRACE] 구매 완료: username={}, item={}", username, item);
        }
    }
}