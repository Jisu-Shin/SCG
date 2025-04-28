# 🚪 gateway

OO-SMS 프로젝트의 API Gateway 역할을 수행하는 서비스입니다.  
외부 요청을 각 내부 서비스(view-service, sms-service, cust-service, booking-service)로 라우팅하고,  
Spring Cloud Gateway를 기반으로 서비스명 기반 동적 라우팅을 지원합니다.

---

## 🛠 기술 스택

- Java 17
- Spring Boot 3.3
- Spring Cloud Gateway
- Spring Cloud (Eureka Client, Config Client)
- Docker, Docker Compose

---

## 🧩 주요 기능

- 외부 요청을 내부 서비스로 라우팅
- 서비스명 기반 동적 라우팅 (Eureka 서비스 디스커버리 활용)
- Java Config를 통한 라우팅 설정 (RouteLocator 사용)
- Gateway 자체에서 오케스트레이션을 수행하는 컨트롤러 구현
- API 요청 흐름 중앙 집중 관리
- 통합 진입점(Entry Point) 역할 수행

---

## 📚 라우팅 설정 예시

| 경로 (Path)      | 대상 서비스 (URI)        |
|:-----------------|:-------------------------|
| `/view/**`        | view-service              |
| `/api/sms/**`     | sms-service               |
| `/api/custs/**`   | cust-service              |
| `/api/bookings/**`| booking-service           |

