# Spring Boot Skeleton Project

This project is a multi-module Spring Boot 3 application skeleton designed for building modern, high-performance services. It provides a solid foundation for applications requiring reactive web services, database interaction, and Kafka messaging with Avro serialization.

## Technology Stack

- **Java:** 21
- **Spring Boot:** 3.4.1
- **Web:** Spring WebFlux (Reactive)
- **Database:** JDBI 3 with HikariCP for connection pooling
- **Messaging:** Apache Kafka with Confluent Avro Schema for data serialization
- **Build:** Apache Maven

## Project Structure

The project is divided into several modules, each with a specific responsibility:

<img src="./assets/structure_v01.png" title="feature" alt=""/>

### `skeleton` (root)
The parent POM that manages all sub-modules and shared dependency versions.

### `avro-lib`
- Manages Avro schemas (`.avsc`) located in `src/main/resources/avro`.
- Uses the `avro-maven-plugin` to automatically generate Java source files from the schemas during the build process.
- Ensures type-safe data contracts for Kafka messages.

### `common`
- A shared library containing common utilities and helper classes used across different modules.
- Includes functionalities like custom thread factories, logging decorators, and other general-purpose code.

### `core`
- The main application module containing the primary business logic.
- Implements a reactive web API using Spring WebFlux.
- Configures services, repositories (using JDBI for direct SQL control), and Kafka producers/consumers.
- Depends on `avro-lib` for data serialization and `common` for shared utilities.

### `zombie-listener`
- An example of a secondary, runnable Spring Boot application.
- It depends on the `core` module to reuse its components and configurations.
- Designed to run specific background tasks, such as listening to a Kafka topic and processing messages.

## Build

To build the entire project and generate all artifacts, run the following command from the root directory:

```bash
mvn clean install
```

## Running the Applications

You can run the two executable applications (`core` and `zombie-listener`) using either the Spring Boot Maven plugin or by executing the packaged JAR files.

### Core Service

**Using Maven:**
```bash
mvn spring-boot:run -pl core
```

**Using the JAR:**
```bash
java -jar core/target/core-*.jar
```

### Zombie Listener

**Using Maven:**
```bash
mvn spring-boot:run -pl zombie-listener
```

**Using the JAR:**
```bash
java -jar zombie-listener/target/zombie-listener-*.jar
```