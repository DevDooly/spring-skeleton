# CI/CD Pipeline Implementation Plan

본 문서는 Spring Boot 멀티 모듈 프로젝트의 자동화된 빌드, 배포 환경 구축 계획 및 결과입니다.

## 1. 아키텍처 개요
전형적인 **GitOps** 모델을 따르며, 확장성을 고려하여 API Gateway를 도입했습니다.

- **API Gateway**: Spring Cloud Gateway (`gateway-server`)
  - 외부 모든 요청의 단일 진입점 (Port: 9000)
  - `/zombie/**` 요청을 `zombie-listener`로 라우팅
- **CI (Continuous Integration)**: Jenkins
  - Maven 빌드 및 Docker 이미지 빌드/Push (Local Registry)
- **CD (Continuous Deployment)**: kubectl / ArgoCD
  - Kubernetes Manifest 기반 배포
- **Containerization**: Docker (Java 21 LTS 기반)

---

## 2. 단계별 구현 결과

### [Step 1] Docker 환경 구축 - [완료]
- `zombie-listener` 및 `gateway-server`용 Dockerfile 작성 완료
- Java 21 및 Maven 3.9.9 기반 Multi-stage build 적용

### [Step 2] API Gateway 구축 - [완료]
- Zuul 대신 현대적인 **Spring Cloud Gateway** 도입 (이유: `cicd/GATEWAY-DIRECTION.md` 참조)
- 위치: `gateway-server/`
- 라우팅 설정 완료 (`/zombie/**` -> `zombie-listener`)

### [Step 3] Kubernetes Manifest & Deployment - [완료]
- `zombie-listener` 및 `gateway-server` 서비스 가동 중
- 서비스 포트:
  - Gateway: 내부 `9000`, 외부 `80` (Kind Service)
  - Zombie: 내부 `10080`

---

## 3. 주요 설정 정보
- **Gateway Port**: 9000
- **Service Routing**:
  - `GET /zombie/api/cache/{key}` -> `zombie-listener:10080/api/cache/{key}`
- **Java Version**: 21 LTS (`--enable-preview` 활성화)

---
*이 문서는 실제 배포 환경에 맞춰 최종 업데이트되었습니다.*
