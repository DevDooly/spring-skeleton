# Authentication System Implementation Plan

본 문서는 마이크로서비스 환경에서의 사용자 인증(Authentication) 및 권한 부여(Authorization) 구축 계획 및 결과입니다.

## 1. 아키텍처 개요
가볍고 확장성이 뛰어난 **Stateless JWT(JSON Web Token)** 기반 인증 방식을 채택했습니다.

- **Gatekeeper**: Gateway Server (`gateway-server`)
  - 모든 외부 요청의 인증 여부를 검사하는 필터링 역할 수행 (`SecurityConfig` 적용)
  - `/auth/login` 요청을 처리하여 JWT 발급
- **Common Logic**: Core Module (`core`)
  - `User` 모델, `AuthService`, `JwtUtils`, `PasswordEncoder` 포함
- **Security**: Spring Security WebFlux
  - 리액티브 환경에 최적화된 보안 프레임워크 사용
- **Token**: JJWT (Java JWT)
  - 표준 JWT 생성 및 검증 라이브러리 사용

---

## 2. 단계별 구현 결과

### [Step 1] 기본 모델 및 의존성 설정 - [완료]
- `User` 클래스 및 `LoginRequest` DTO 작성 완료
- `spring-boot-starter-security` 및 `jjwt` (0.12.6) 의존성 추가 완료
- `BCryptPasswordEncoder` 빈 설정 완료

### [Step 2] 인증 서비스 구현 - [완료]
- `AuthService`: In-memory 기반 사용자(admin/admin123) 검증 로직 구현
- `JwtUtils`: 토큰 생성 및 파싱 로직 구현
- `AuthController`: Gateway에 로그인 엔드포인트 노출 완료

### [Step 3] Gateway 보안 필터 적용 - [완료]
- `SecurityWebFilterChain`을 통한 경로 보안 설정 완료
- 화이트리스트(`/auth/**`, `/actuator/**`, `/scalar/**` 등)를 제외한 모든 요청에 JWT 검증 필수 적용

### [Step 4] 통합 테스트 검증 - [완료]
- `test-api.sh`를 통한 로그인 -> 토큰 획득 -> 인증된 요청 테스트 완료

---

## 3. 주요 계정 정보 (Default)
- **Admin**: `admin` / `admin123`
- **User**: `user` / `user123`

---
*이 문서는 실제 배포 환경에 맞춰 최종 업데이트되었습니다.*
