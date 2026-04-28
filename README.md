# Spring Boot MSA Skeleton Project

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.1-brightgreen?logo=springboot)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2024.0.0-blue?logo=spring)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6.4-green?logo=springsecurity)
![JWT](https://img.shields.io/badge/JWT-JSON%20Web%20Token-black?logo=jsonwebtokens)
![Caffeine](https://img.shields.io/badge/Cache-Caffeine-red)
![BlockHound](https://img.shields.io/badge/Performance-BlockHound-blueviolet)
![Docker](https://img.shields.io/badge/Docker-Container-blue?logo=docker)
![Kubernetes](https://img.shields.io/badge/Kubernetes-K8S-blue?logo=kubernetes)
![Jenkins](https://img.shields.io/badge/CI%2FCD-Jenkins-orange?logo=jenkins)
![Elasticsearch](https://img.shields.io/badge/Logging-Elasticsearch-005571?logo=elasticsearch)
![Kibana](https://img.shields.io/badge/Logging-Kibana-005571?logo=kibana)
![Prometheus](https://img.shields.io/badge/Metrics-Prometheus-e6522c?logo=prometheus)
![Grafana](https://img.shields.io/badge/Monitoring-Grafana-F46800?logo=grafana)
![Kafka](https://img.shields.io/badge/Message-Kafka-black?logo=apachekafka)
![Scalar](https://img.shields.io/badge/API%20Docs-Scalar-lightgrey)

현대적이고 고성능 마이크로서비스를 구축하기 위한 멀티 모듈 Spring Boot 3.4 애플리케이션 스켈레톤입니다. 반응형 웹 서비스, 가상 스레드 최적화, 보안, 모니터링 및 자동화된 CI/CD 환경을 제공합니다.

<img src="./assets/structure_v01.png" title="Architecture" alt=""/>

## 🚀 주요 기능 및 기술 스택

### 1. 최신 자바 및 프레임워크
- **Java 21 LTS**: 가상 스레드(Virtual Threads) 및 Scoped Value 활용 (Preview 활성화)
- **Spring Boot 3.4.1**: 최신 안정 버전 기반
- **Spring Cloud Gateway**: 모든 요청의 단일 진입점 및 라우팅 (Zuul 대체)

### 2. 보안 및 인증 (Security)
- **JWT (JSON Web Token)**: Stateless 인증 시스템 구축
- **Spring Security WebFlux**: 리액티브 환경에 최적화된 보안 필터 체인
- **BCrypt**: 패스워드 암호화 및 안전한 사용자 검증

### 3. 성능 최적화 (Performance)
- **Caffeine Cache**: 고성능 로컬 캐싱 엔진 적용
- **BlockHound**: Non-blocking 스레드 내 블로킹 호출 실시간 감지 (Java Agent 방식)
- **Build Speed Optimization**: Docker BuildKit 캐시 마운트 및 Maven 병렬 빌드(-T 1C) 적용

### 4. 관측성 및 모니터링 (Observability)
- **Structured Logging**: Spring Boot 3.4 ECS(Elastic Common Schema) 형식의 JSON 로그 적용
- **EFK Stack**: Elasticsearch, Fluent-bit, Kibana를 이용한 로그 중앙 집중화
- **Prometheus & Grafana**: 리소스 사용량 및 성능 메트릭 시각화 (Spring Boot Actuator 통합)

### 5. API 문서화
- **Scalar**: Swagger UI를 대체하는 현대적이고 깔끔한 인터랙티브 API 문서 (주소: `/scalar`)

## 📁 프로젝트 구조

- **`gateway-server`**: API Gateway, JWT 인증, 라우팅 및 보안 필터 담당
- **`zombie-listener`**: 메인 비즈니스 로직, Kafka 컨슈머, Caffeine 캐시 적용 서비스
- **`core`**: 공통 엔티티, JDBI 리포지토리, 인증 서비스, Jwt 유틸리티 등 핵심 모듈
- **`common`**: 공통 유틸리티, 로깅 데코레이터, 가상 스레드 도구
- **`avro-lib`**: Avro 스키마 및 자동 생성된 자바 클래스 관리
- **`cicd`**: 인프라(K8S), 모니터링(Prometheus/Grafana), 로그(EFK), 빌드 최적화 설정 파일 모음

## 🛠 배포 및 테스트 가이드

상세한 단계별 배포 방법은 [cicd/DEPLOY-GUIDE.md](./cicd/DEPLOY-GUIDE.md)를 참조하세요.

### 1. 포트 포워딩 자동 설정
Kubernetes 클러스터에 배포된 서비스들을 로컬 환경과 즉시 연결합니다.
```bash
./setup-port-forward.sh
```

### 2. 통합 테스트 실행 (인증 포함)
로그인부터 인증 기반 API 호출까지 전체 프로세스를 검증합니다.
```bash
./test-api.sh
```

### 3. 주요 접속 주소
| 서비스 | 주소 | 비고 |
| :--- | :--- | :--- |
| **API Gateway** | `http://localhost:9001` | 모든 요청의 진입점 |
| **API 문서 (Scalar)** | `http://localhost:9001/zombie/scalar` | 인터랙티브 API 가이드 |
| **로그 (Kibana)** | `http://localhost:5601` | 실시간 로그 분석 |
| **메트릭 (Grafana)** | `http://localhost:3000` | 리소스 모니터링 대시보드 |

---
*이 프로젝트는 지속적으로 최신 기술 트렌드를 반영하여 업데이트됩니다.*
