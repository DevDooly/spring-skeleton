# syntax=docker/dockerfile:1
# Stage 1: Build
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy all poms first
COPY pom.xml .
COPY avro-lib/pom.xml avro-lib/
COPY common/pom.xml common/
COPY core/pom.xml core/
COPY zombie-listener/pom.xml zombie-listener/
COPY gateway-server/pom.xml gateway-server/
COPY utils/pom.xml utils/

# 1. Cache dependencies using BuildKit mount
# This avoids re-downloading jars even if poms change slightly
RUN --mount=type=cache,target=/root/.m2 \
    mvn dependency:go-offline -B -T 1C

# 2. Copy source code
COPY . .

# 3. Build only the target module and its dependencies
# -pl :zombie-listener (target)
# -am (also make dependencies: core, common, avro-lib)
RUN --mount=type=cache,target=/root/.m2 \
    mvn clean package -DskipTests -T 1C -pl :zombie-listener -am

# Stage 2: Runtime
FROM eclipse-temurin:21-jre
WORKDIR /app

# Download blockhound jar for agent
ADD https://repo1.maven.org/maven2/io/projectreactor/tools/blockhound/1.0.16.RELEASE/blockhound-1.0.16.RELEASE.jar blockhound.jar

# Copy the built jar
COPY --from=build /app/zombie-listener/target/zombie-listener-*.jar app.jar

EXPOSE 10080

ENTRYPOINT ["java", "-XX:+AllowRedefinitionToAddDeleteMethods", "-javaagent:blockhound.jar", "--enable-preview", "-jar", "app.jar"]
