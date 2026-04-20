# CI/CD Pipeline Implementation Plan

본 문서는 Spring Boot 멀티 모듈 프로젝트의 자동화된 빌드, 배포 환경 구축 계획 및 결과입니다.

## 1. 아키텍처 개요
전형적인 **GitOps** 모델을 따르며, 빌드(CI)와 배포(CD)를 분리하여 운영 효율성과 보안성을 높입니다.

- **CI (Continuous Integration)**: Jenkins
  - 소스 코드 변경 감지 (Webhook)
  - Maven 빌드 및 테스트 (Java 21 환경)
  - Docker 이미지 빌드 및 Registry(Local: `localhost:5000`) Push
- **CD (Continuous Deployment)**: ArgoCD / kubectl
  - Git 저장소의 Kubernetes Manifest 감지
  - 실제 클러스터 상태와 Git 상태를 동기화
- **Containerization**: Docker
  - Multi-stage build를 통한 이미지 최적화
  - Java 21 LTS 기반

---

## 2. 단계별 구현 결과

### [Step 1] Docker 환경 구축 - [완료]
- 멀티 모듈 프로젝트 특성을 고려한 `Dockerfile` 작성 완료
- 위치: `cicd/docker/Dockerfile`
- Java 21 및 Maven 3.9.9 기반 Multi-stage build 적용
- 포트 설정: **10080** (기존 8080 충돌 회피)

### [Step 2] Jenkins 파이프라인 구성 - [완료]
- `Jenkinsfile` 정의 완료
- 위치: `cicd/jenkins/Jenkinsfile`
- 로컬 레지스트리(`localhost:5000`) 사용 설정

### [Step 3] Kubernetes Manifest & Deployment - [완료]
- `k8s` 매니페스트 작성 및 실제 배포 완료
- 위치: `cicd/k8s/deployment.yaml`
- Kind 클러스터 내 `zombie-listener` 서비스 가동 중
- 서비스 포트: 내부 `10080`, 외부(NodePort) `32626`

---

## 3. 주요 설정 정보
- **Java Version**: 21 LTS (`--enable-preview` 활성화)
- **Spring Boot**: 3.4.1
- **Application Port**: 10080
- **Database**: H2 (In-memory, MySQL Mode)
- **Kafka**: Localhost:9092 (연결 시도 중)

---
*이 문서는 실제 배포 환경에 맞춰 최종 업데이트되었습니다.*
