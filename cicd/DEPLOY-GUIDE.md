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

### API 문서 확인 (Scalar)
Swagger UI보다 현대적이고 깔끔한 UI를 제공하는 Scalar가 적용되어 있습니다.

```bash
kubectl port-forward svc/zombie-listener 10080:80
```
- **주소**: `http://localhost:10080/scalar`
- **OpenAPI Spec**: `http://localhost:10080/api-docs`

### 로그 모니터링 (Kibana)
EFK 스택을 통해 로그를 시각화할 수 있습니다. Kibana 대시보드에 접속하려면 포트 포워딩을 설정하세요.

```bash
kubectl port-forward -n kube-system svc/kibana 5601:5601
```
- **주소**: `http://localhost:5601`
- **초기 설정**: `Stack Management` -> `Index Patterns`에서 `fluent-bit-*` 패턴을 생성하면 로그를 조회할 수 있습니다.

### 리소스 및 메트릭 모니터링 (Prometheus & Grafana)
애플리케이션의 리소스 사용량과 성능 메트릭을 실시간으로 확인합니다.

```bash
# 한 번에 모든 포트 포워딩 설정 (추천)
./setup-port-forward.sh
```

- **Prometheus**: `http://localhost:9090` (데이터 수집 서버)
- **Grafana**: `http://localhost:3000` (시각화 대시보드)
  - **초기 데이터 소스 설정**: `Configuration` -> `Data Sources` -> `Prometheus` 선택 -> URL에 `http://prometheus-service.kube-system:8080` 입력 후 Save & Test.
  - **대시보드 추천**: `Import` 메뉴에서 ID `11378` (JVM Dashboard) 또는 `4701`을 입력하면 Spring Boot 전용 대시보드가 즉시 구성됩니다.

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
