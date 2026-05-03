
# 🚀 Monitoring Demo API 명세서 & 테스트 가이드

이 프로젝트는 **Spring Boot** 애플리케이션의 메트릭과 로그를 **Grafana Alloy, Loki, Prometheus**로 모니터링하는 실습을 위해 설계되었습니다.

---

## 📑 1. API 명세서

기본 Base URL: `http://localhost:8080`

### 🛒 주문 시스템 (Latency & Error 시뮬레이션)
| 기능 | Method | Endpoint | 설명 | 관전 포인트 |
| :--- | :---: | :--- | :--- | :--- |
| **정상 주문** | `GET` | `/api/orders/normal` | 즉시 주문 완료 (성공)[cite: 1] | TPS 상승, INFO 로그[cite: 1] |
| **지연 주문** | `GET` | `/api/orders/slow` | 1~3초 랜덤 지연 발생[cite: 1] | **p99 Latency** 스파이크[cite: 1] |
| **에러 주문** | `GET` | `/api/orders/error` | 500 내부 서버 에러 발생[cite: 1] | **Error Rate** 급증[cite: 1] |

### 🔍 유저 트래킹 (Activity Trace)
| 기능 | Method | Endpoint | 설명 | 관전 포인트 |
| :--- | :---: | :--- | :--- | :--- |
| **유저 등록** | `POST` | `/api/trace/user` | 특정 유저의 가입 시뮬레이션 | Loki에서 유저별 검색 |
| **상품 구매** | `GET` | `/api/trace/purchase` | 유저의 구매 활동 기록 | 한 유저의 전체 로그 Flow |

### 💥 부하 테스트 (Stress Test)
| 기능 | Method | Endpoint | 파라미터 | 설명 |
| :--- | :---: | :--- | :--- | :--- |
| **로그 폭격** | `GET` | `/api/stress/run` | `count` (기본 100) | 대량의 로그와 랜덤 에러 생성 |

---

## 🛠 2. 테스트 시나리오 가이드

### 시나리오 A: "평균의 함정" 확인하기
1. `/api/orders/normal`을 10번 호출합니다. (평균 10ms 이하)
2. `/api/orders/slow`를 2번 호출합니다. (3초 지연 발생)[cite: 1]
3. **Grafana 결과:**
    * 평균 응답 시간은 약간 상승하지만, **p99(상위 1%) 그래프**는 수직 상승하는 것을 확인합니다.

### 시나리오 B: 특정 유저의 활동 추적하기
1. 유저 가입 (Terminal 실행)
   ```bash
   curl -X POST http://localhost:8080/api/trace/user \
        -H "Content-Type: application/json" \
        -d '{"username":"ryan_99"}'
   ```
2. 구매 활동 생성
    * `http://localhost:8080/api/trace/purchase?username=ryan_99&item=iphone`
    * `http://localhost:8080/api/trace/purchase?username=ryan_99&item=bad-item`
3. **Loki 확인:**
    * Grafana Explore에서 `{app="log-demo"} |= "ryan_99"` 입력 시 해당 유저의 모든 히스토리가 출력됩니다.

### 시나리오 C: 대시보드 붉게 물들이기 (Stress)
1. 브라우저에서 아래 주소 접속:
   `http://localhost:8080/api/stress/run?count=500`
2. **관전 포인트:**
    * **Error Rate** 패널이 솟구치고, **Logs** 패널에 실시간으로 로그가 쏟아지는 것을 감상합니다.

