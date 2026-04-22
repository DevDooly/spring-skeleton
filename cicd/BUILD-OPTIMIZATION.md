# Build Performance Optimization Plan

본 문서는 빌드 시간을 최소화하기 위한 전략과 적용된 기술을 설명합니다.

## 1. 주요 최적화 전략

### A. Docker BuildKit Cache Mount 적용
- **문제**: 매 빌드마다 Docker 레이어가 깨지면 `mvn dependency:go-offline`이 동작하더라도 수백 MB의 라이브러리를 다시 확인하는 과정이 발생합니다.
- **해결**: `--mount=type=cache,target=/root/.m2`를 사용하여 호스트 머신의 Maven 로컬 저장소를 빌드 컨테이너 간에 공유합니다. 이는 레이어 캐시와 별개로 동작하여 의존성 다운로드 시간을 0에 수렴하게 만듭니다.

### B. Maven 선택적 빌드 (Project List & Also Make)
- **문제**: `zombie-listener`를 빌드할 때 관련 없는 `gateway-server`나 `utils` 모듈까지 전체 빌드되고 있습니다.
- **해결**: `-pl :zombie-listener -am` 옵션을 사용하여 대상 모듈과 그 의존성(`core`, `common`, `avro-lib`)만 선별하여 빌드합니다.

### C. 증분 빌드 활용
- Dockerfile 내에서 `mvn clean` 대신 상황에 따라 `mvn package`만 수행하도록 조절하여 이미 컴파일된 클래스를 최대한 재사용합니다. (현재는 안정성을 위해 clean을 유지하되 캐시 마운트로 속도 보완)

---

## 2. 적용 결과 (예상)
- **라이브러리 로딩**: 수 분 -> 수 초 (캐시 마운트 효과)
- **컴파일 대상**: 전체 모듈 -> 대상 모듈 및 의존 모듈 (약 40% 감소)
- **병렬 처리**: 16코어 풀 활용 (`-T 1C`)

---
*이 최적화 설정은 Dockerfile 및 Jenkinsfile에 즉시 반영됩니다.*
