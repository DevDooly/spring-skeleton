# API Gateway 구축 방향 및 기술 선정

본 문서는 프로젝트의 확장성(여러 데몬으로 증가)을 고려한 API Gateway 구축 방향을 제시합니다.

## 1. Zuul vs Spring Cloud Gateway
사용자께서 Zuul을 언급하셨으나, 현재 프로젝트의 기술 스택(WebFlux, Java 21+, Virtual Threads)을 고려할 때 **Spring Cloud Gateway(SCG)**로의 구축을 강력히 권장하며 그 이유는 다음과 같습니다.

| 비교 항목 | Netflix Zuul (1.x) | Spring Cloud Gateway |
| :--- | :--- | :--- |
| **기반 기술** | Servlet (Blocking I/O) | WebFlux (Non-blocking I/O) |
| **성능** | 스레드당 요청 처리 (한계 존재) | 적은 리소스로 대량 요청 처리 최적화 |
| **유지보수** | Maintenance Mode (신규 기능 중단) | Spring 생태계 공식 표준 Gateway |
| **적합성** | Spring Boot 2.x 이하 | **Spring Boot 3.x / WebFlux 프로젝트** |

## 2. 구축 방향
현재 `zombie-listener` 하나만 존재하지만, 향후 여러 개의 데몬(Microservices)이 추가될 예정이므로 다음과 같은 구조로 설계합니다.

- **Gateway Module 신설**: `gateway-server` 모듈을 추가하여 외부 모든 요청의 단일 진입점(Single Entry Point) 역할을 수행합니다.
- **Dynamic Routing**: 서비스 이름에 따라 요청을 적절한 데몬으로 전달합니다.
- **Cross-cutting concerns**: 인증/인가, 로깅, Rate Limiting 등을 Gateway 계층에서 통합 관리합니다.

## 3. 향후 로드맵
1.  **[현재]** `gateway-server` 모듈 생성 및 `zombie-listener` 라우팅 설정.
2.  **[확장]** 데몬이 늘어날 경우 `application.yml`에 라우팅 규칙만 추가.
3.  **[고도화]** 서비스 디스커버리(Eureka) 또는 Kubernetes Service DNS를 통한 동적 부하 분산 적용.

---
*이 문서의 방향에 따라 `gateway-server` 모듈 구현을 시작합니다.*
