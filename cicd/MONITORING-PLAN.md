# Monitoring System Implementation Plan

본 문서는 마이크로서비스 상태 및 리소스 모니터링을 위한 Prometheus 및 Grafana 구축 계획 및 결과입니다.

## 1. 아키텍처 개요
애플리케이션의 메트릭(Metric)을 수집하여 시각화하고 알람을 구성합니다.

- **Metric Producer**: Spring Boot (Zombie Listener, Gateway Server)
  - `Spring Boot Actuator`와 `Micrometer`를 사용하여 메트릭 생성
  - `/actuator/prometheus` 엔드포인트 노출
- **Collector**: Prometheus
  - Kubernetes 서비스 디스커버리를 통해 각 Pod의 메트릭을 주기적으로 Pull (Scraping)
- **Visualization**: Grafana
  - Prometheus를 데이터 소스로 사용하여 대시보드 구성 (CPU, Memory, Request Rate 등)

---

## 2. 단계별 구현 결과

### [Step 1] 애플리케이션 메트릭 노출 설정 - [완료]
- `spring-boot-starter-actuator` 및 `micrometer-registry-prometheus` 의존성 추가 완료
- `application.yml` 설정을 통해 Prometheus 포맷 메트릭 엔드포인트 활성화 완료

### [Step 2] Prometheus 배포 및 설정 - [완료]
- `cicd/monitoring/prometheus.yaml` 배포 완료
- Kubernetes Pod 자동 탐색(Scraping) 설정 적용

### [Step 3] Grafana 배포 및 시각화 - [완료]
- `cicd/monitoring/grafana.yaml` 배포 완료
- 익명 관리자 권한 활성화로 즉시 접속 가능 환경 구성

---

## 3. 주요 접속 정보
- **Prometheus**: `http://localhost:9090` (Port-forward 필요)
- **Grafana**: `http://localhost:3000` (Port-forward 필요)
- **메트릭 경로**: `/actuator/prometheus`

---
*이 문서는 실제 배포 환경에 맞춰 최종 업데이트되었습니다.*
