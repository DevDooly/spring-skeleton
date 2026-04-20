# EFK Stack Implementation Plan

본 문서는 로그 중앙 집중화 및 시각화를 위한 EFK(Elasticsearch, Fluent-bit, Kibana) 스택 구축 계획 및 결과입니다.

## 1. 아키텍처 개요
애플리케이션에서 발생하는 로그를 정형화된 데이터(JSON)로 출력하고, 이를 수집하여 시각화합니다.

- **Log Producer**: Spring Boot (Zombie Listener)
  - Logback을 사용하여 JSON 형식으로 로그 출력 (`logstash-logback-encoder` 적용)
- **Log Shipper**: Fluent-bit (DaemonSet)
  - Kubernetes 노드의 로그 파일을 읽어 Elasticsearch로 전송
- **Storage & Search**: Elasticsearch
  - 로그 데이터 저장 및 인덱싱 (Single Node)
- **Visualization**: Kibana
  - Elasticsearch에 저장된 로그 탐색 및 대시보드 구성

---

## 2. 단계별 구현 결과

### [Step 1] 애플리케이션 로그 설정 변경 - [완료]
- `logstash-logback-encoder` 의존성 추가 완료
- `logback-spring.xml` 구성을 통해 `prod` 프로필에서 JSON 출력 적용 완료

### [Step 2] Elasticsearch & Kibana 배포 - [완료]
- `cicd/efk/elasticsearch.yaml` 배포 완료
- `cicd/efk/kibana.yaml` 배포 완료

### [Step 3] Fluent-bit 배포 및 설정 - [완료]
- `cicd/efk/fluent-bit.yaml` 배포 완료 (DaemonSet, ConfigMap 포함)
- 로그 수집 및 ES 전송 확인

### [Step 4] 통합 테스트 및 시각화 - [진행 중]
- Kibana 접속 정보: `localhost:5601` (Port-forward 필요)
- 인덱스 패턴(`fluent-bit-*`) 설정 필요

---
*이 문서는 실제 배포 환경에 맞춰 최종 업데이트되었습니다.*
