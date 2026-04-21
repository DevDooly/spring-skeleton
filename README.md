# Spring Boot MSA Skeleton Project

이 프로젝트는 현대적이고 고성능 마이크로서비스를 구축하기 위한 멀티 모듈 Spring Boot 3 애플리케이션 스켈레톤입니다. 반응형 웹 서비스, 가상 스레드 기반 최적화, 데이터베이스 상호 작용 및 Kafka 메시징을 위한 견고한 기반을 제공합니다.

## 🚀 주요 기술 스택 및 특징

### 핵심 프레임워크
- **Java:** 21 LTS (Scoped Value 등 최신 기능을 위한 `--enable-preview` 활성화)
- **Spring Boot:** 3.4.1 (Structured Logging 지원)
- **Spring Cloud Gateway:** MSA 환경의 단일 진입점 및 라우팅 (Zuul 대체)

### 고성능 및 안정성
- **Concurrency:** Virtual Threads(가상 스레드) 및 Scoped Value를 통한 효율적인 컨텍스트 전파
- **Cache:** Caffeine Cache 기반 로컬 캐싱 적용
- **Blocking Detection:** BlockHound를 통한 Non-blocking 스레드 내 블로킹 호출 실시간 감지 (Java Agent 방식)

### 모니터링 및 문서화
- **Logging:** EFK 스택(Elasticsearch, Fluent-bit, Kibana) 통합 및 Spring Boot 3.4 Structured Logging(ECS Format) 적용
- **API Docs:** Scalar를 활용한 현대적이고 인터랙티브한 API 문서 자동화 (Swagger UI 대체)

## 📁 프로젝트 구조

<img src="./assets/structure_v01.png" title="Architecture" alt=""/>

- **`gateway-server` (New):** 모든 요청을 수신하고 각 서비스(`zombie-listener` 등)로 전달하는 API Gateway
- **`zombie-listener`:** Kafka 토픽 수신 및 비즈니스 로직 처리 예시 서비스 (Caffeine Cache, Scoped Value 적용)
- **`core`:** 핵심 비즈니스 로직, WebFlux 설정, JDBI 리포지토리 및 Kafka 공통 구성
- **`avro-lib`:** Avro 스키마 관리 및 Java 클래스 자동 생성
- **`common`:** 공통 유틸리티 및 가상 스레드 헬퍼
- **`utils`:** 각종 테스트 코드 및 예시 모음

## 🛠 CI/CD 및 운영 도구

`cicd/` 폴더에 배포 및 운영을 위한 자동화 설정이 포함되어 있습니다.

- **Docker:** Multi-stage build를 통한 최적화된 이미지 생성 (`cicd/docker/`)
- **Kubernetes:** `kind` 클러스터 기반 Deployment/Service 구성 (`cicd/k8s/`)
- **Jenkins:** CI 파이프라인 구성을 위한 `Jenkinsfile` 제공
- **EFK:** 로그 수집 및 시각화 환경 구성 매니페스트 (`cicd/efk/`)

## 🚦 시작하기 (Quick Start)

배포 및 테스트를 위한 상세 가이드는 [cicd/DEPLOY-GUIDE.md](./cicd/DEPLOY-GUIDE.md)를 참조하세요.

### 1. 포트 포워딩 설정
로컬 환경에서 Kubernetes 서비스에 접속하기 위해 제공된 스크립트를 사용합니다.
```bash
./setup-port-forward.sh
```

### 2. API 통합 테스트
Caffeine Cache, Scoped Value, Gateway 라우팅 기능을 한 번에 검증합니다.
```bash
./test-api.sh
```

### 3. 주요 접속 정보
- **API Gateway:** `http://localhost:9001`
- **API 문서 (Scalar):** `http://localhost:10080/scalar` (직접) 또는 `http://localhost:9001/zombie/scalar` (Gateway)
- **로그 시각화 (Kibana):** `http://localhost:5601`

---
*이 프로젝트는 지속적으로 최신 Java/Spring 기술 트렌드를 반영하여 업데이트됩니다.*
