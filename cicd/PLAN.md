# CI/CD Pipeline Implementation Plan

본 문서는 Spring Boot 멀티 모듈 프로젝트의 자동화된 빌드, 배포 환경 구축 계획을 담고 있습니다.

## 1. 아키텍처 개요
전형적인 **GitOps** 모델을 따르며, 빌드(CI)와 배포(CD)를 분리하여 운영 효율성과 보안성을 높입니다.

- **CI (Continuous Integration)**: Jenkins
  - 소스 코드 변경 감지 (Webhook)
  - Maven 빌드 및 테스트 (Java 25 환경)
  - Docker 이미지 빌드 및 Registry(Docker Hub/ECR 등) Push
- **CD (Continuous Deployment)**: ArgoCD
  - Git 저장소의 Kubernetes Manifest(Helm/Kustomize) 감지
  - 실제 클러스터 상태와 Git 상태를 동기화
- **Containerization**: Docker
  - Multi-stage build를 통한 이미지 최적화

---

## 2. 단계별 구현 결과

### [Step 1] Docker 환경 구축 - [완료]
- 멀티 모듈 프로젝트 특성을 고려한 `Dockerfile` 작성 완료
- 위치: `cicd/docker/Dockerfile`
- Java 25 기반 Multi-stage build 적용

### [Step 2] Jenkins 파이프라인 구성 - [완료]
- `Jenkinsfile` 정의 완료
- 위치: `cicd/jenkins/Jenkinsfile`
- 빌드, 테스트, Docker Image Build & Push 단계 포함

### [Step 3] Kubernetes Manifest & ArgoCD 설정 - [완료]
- `k8s` 매니페스트 작성 완료
- 위치: `cicd/k8s/deployment.yaml`
- ArgoCD에서 이 파일을 감지하여 자동 배포하도록 설정 가능

---

## 3. 향후 과제 (Next Steps)
1.  **ArgoCD 연동**: Kubernetes 클러스터에 ArgoCD를 설치하고 해당 Git 저장소를 연결합니다.
2.  **Secret 관리**: DB 접속 정보, Kafka 설정 등을 K8s Secrets 또는 Vault로 관리하도록 고도화합니다.
3.  **Helm Chart 전환**: 서비스 규모가 커질 경우 일반 Manifest에서 Helm Chart로 전환을 고려합니다.

---
*이 문서는 진행 상황에 따라 지속적으로 업데이트됩니다.*
