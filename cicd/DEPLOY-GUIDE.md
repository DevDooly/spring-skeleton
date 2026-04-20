# Deployment Guide

본 문서는 Spring Boot 멀티 모듈 프로젝트를 빌드하고 Kubernetes(Kind) 환경에 배포하는 방법을 단계별로 설명합니다.

## 1. 전제 조건 (Prerequisites)
- **Java**: JDK 21 이상 (빌드 시 `--enable-preview` 사용)
- **Docker**: 컨테이너 이미지 빌드 및 실행
- **Kubernetes**: `kubectl` 명령 도구 및 `kind` 클러스터 환경

## 2. 단계별 배포 순서

### Step 1: Docker 이미지 빌드
프로젝트 루트 폴더에서 Docker Multi-stage 빌드를 실행합니다. 이 과정에서 Maven 빌드와 이미지 생성이 동시에 이루어집니다.

```bash
docker build -t localhost:5000/zombie-listener:latest -f cicd/docker/Dockerfile .
```

### Step 2: 클러스터에 이미지 로드 (Kind 사용 시)
빌드된 이미지를 `kind` 클러스터 내부에서 인식할 수 있도록 로드합니다.

```bash
kind load docker-image localhost:5000/zombie-listener:latest --name kind
```

### Step 3: Kubernetes 자원 배포
`cicd/k8s/` 폴더에 정의된 매니페스트를 적용합니다.

```bash
kubectl apply -f cicd/k8s/deployment.yaml
```

### Step 4: 배포 상태 확인
Pod이 정상적으로 `Running` 상태가 되었는지 확인합니다.

```bash
kubectl get pods -l app=zombie-listener
```

## 3. 서비스 접속 및 테스트

### API 접속을 위한 Port-Forward
서비스가 `LoadBalancer` 타입이지만 로컬 환경에서는 `port-forward`를 통해 접속해야 할 수 있습니다. (80포트를 로컬 10080으로 연결)

```bash
kubectl port-forward svc/zombie-listener 10080:80
```

### 자동 테스트 스크립트 실행
새 터미널을 열고 제공된 쉘 스크립트를 실행하여 API 동작을 검증합니다.

```bash
./test-api.sh
```

## 4. 유용한 명령어 (Troubleshooting)

- **로그 확인**: `kubectl logs -l app=zombie-listener -f`
- **재기동**: `kubectl rollout restart deployment zombie-listener`
- **리소스 삭제**: `kubectl delete -f cicd/k8s/deployment.yaml`

---
*참고: 실제 운영 환경(Jenkins)에서는 `Jenkinsfile`에 의해 위 과정이 자동으로 수행됩니다.*
