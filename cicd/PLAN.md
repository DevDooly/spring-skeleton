# CI/CD Pipeline Implementation Plan

본 문서는 Spring Boot 멀티 모듈 프로젝트의 자동화된 빌드, 배포 환경 구축 계획 및 결과입니다.

## 1. 아키텍처 개요
전형적인 **GitOps** 모델을 따르며, 확장성과 안정성을 위해 API Gateway와 BlockHound를 도입했습니다.

- **API Gateway**: Spring Cloud Gateway (`gateway-server`)
  - 외부 모든 요청의 단일 진입점 (Port: 9000)
  - `/zombie/**` 요청을 `zombie-listener`로 라우팅
- **Blocking Detection**: BlockHound
  - WebFlux 환경의 Non-blocking 스레드에서 Blocking 호출 발생 시 즉시 감지
  - Java Agent(`-javaagent`) 방식을 통해 컨테이너 환경에서 안정적으로 동작
- **Containerization**: Docker (Java 21 LTS 기반)

---

## 2. 단계별 구현 결과

### [Step 1] Docker 환경 구축 - [완료]
- `zombie-listener` 및 `gateway-server`용 Dockerfile 작성 완료
- BlockHound 동작을 위한 JVM 옵션 및 Java Agent 설정 적용

### [Step 2] API Gateway 및 로깅 구축 - [완료]
- Spring Cloud Gateway 도입 및 라우팅 설정 완료
- Spring Boot 3.4 Structured Logging (ECS) 적용

### [Step 3] Blocking 호출 감지 설정 (BlockHound) - [완료]
- `pom.xml` 의존성 추가 및 `dependencyManagement` 설정
- Docker `ENTRYPOINT`에 `-javaagent:blockhound.jar` 및 `-XX:+AllowRedefinitionToAddDeleteMethods` 추가

---

## 3. 주요 설정 정보
- **Gateway Port**: 9000 (Local Forward: 9001)
- **BlockHound**: Java Agent 방식 적용 완료
- **Java Version**: 21 LTS (`--enable-preview` 활성화)

---
*이 문서는 실제 배포 환경에 맞춰 최종 업데이트되었습니다.*
