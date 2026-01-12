# Spring Boot 스켈레톤 프로젝트

이 프로젝트는 현대적이고 고성능 서비스를 구축하기 위해 설계된 멀티 모듈 Spring Boot 3 애플리케이션 스켈레톤입니다. 반응형 웹 서비스, 데이터베이스 상호 작용 및 Avro 직렬화를 통한 Kafka 메시징이 필요한 애플리케이션을 위한 견고한 기반을 제공합니다.

## 기술 스택

- **Java:** 21
- **Spring Boot:** 3.4.1
- **웹:** Spring WebFlux (반응형)
- **데이터베이스:** JDBI 3 및 HikariCP (연결 풀링용)
- **메시징:** Confluent Avro Schema를 사용한 Apache Kafka (데이터 직렬화용)
- **빌드:** Apache Maven

## 프로젝트 구조

이 프로젝트는 **MSA(Microservices Architecture) 기반**으로 설계되어 여러 모듈로 나뉘며, 각 모듈은 특정 역할을 가집니다:

<img src="./assets/structure_v01.png" title="feature" alt=""/>

### `skeleton` (루트)
모든 하위 모듈과 공유 종속성 버전을 관리하는 부모 POM입니다.

### `avro-lib`
- `src/main/resources/avro`에 위치한 Avro 스키마(`.avsc`)를 관리합니다.
- `avro-maven-plugin`을 사용하여 빌드 프로세스 중에 스키마로부터 Java 소스 파일을 자동으로 생성합니다.
- Kafka 메시지에 대한 타입 안전한 데이터 계약을 보장합니다.

### `common`
- 여러 모듈에서 사용되는 공통 유틸리티 및 헬퍼 클래스를 포함하는 공유 라이브러리입니다.
- 사용자 정의 스레드 팩토리, 로깅 데코레이터 및 기타 일반적인 목적의 코드를 포함합니다.

### `core`
- 주요 비즈니스 로직을 포함하는 메인 애플리케이션 모듈입니다.
- Spring WebFlux를 사용하여 반응형 웹 API를 구현합니다.
- 서비스, 리포지토리(직접 SQL 제어를 위한 JDBI 사용) 및 Kafka 프로듀서/컨슈머를 구성합니다.
- 데이터 직렬화를 위해 `avro-lib`에, 공유 유틸리티를 위해 `common`에 의존합니다.

### `zombie-listener`
- 보조 실행 가능 Spring Boot 애플리케이션의 예시입니다.
- `core` 모듈에 의존하여 해당 구성 요소와 구성을 재사용합니다.
- Kafka 토픽을 수신하고 메시지를 처리하는 것과 같은 특정 백그라운드 작업을 실행하도록 설계되었습니다.

## 빌드

전체 프로젝트를 빌드하고 모든 아티팩트를 생성하려면 루트 디렉토리에서 다음 명령을 실행합니다:

```bash
mvn clean install
```

## 애플리케이션 실행

Spring Boot Maven 플러그인을 사용하거나 패키징된 JAR 파일을 실행하여 두 개의 실행 가능한 애플리케이션(`core` 및 `zombie-listener`)을 실행할 수 있습니다.

### 코어 서비스

**Maven 사용:**
```bash
mvn spring-boot:run -pl core
```

**JAR 사용:**
```bash
java -jar core/target/core-*.jar
```

### 좀비 리스너

**Maven 사용:**
```bash
mvn spring-boot:run -pl zombie-listener
```

**JAR 사용:**
```bash
java -jar zombie-listener/target/zombie-listener-*.jar
```
