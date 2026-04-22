# Stage 1: Build
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# 1. Copy only POM files to cache dependencies
COPY pom.xml .
COPY avro-lib/pom.xml avro-lib/
COPY common/pom.xml common/
COPY core/pom.xml core/
COPY zombie-listener/pom.xml zombie-listener/
COPY gateway-server/pom.xml gateway-server/
COPY utils/pom.xml utils/

# 2. Download dependencies (This layer is cached unless poms change)
RUN mvn dependency:go-offline -B -T 1C

# 3. Copy source code
COPY avro-lib/src avro-lib/src
COPY common/src common/src
COPY core/src core/src
COPY zombie-listener/src zombie-listener/src
COPY gateway-server/src gateway-server/src
COPY utils/src utils/src

# 4. Build the project using all available cores
RUN mvn clean package -DskipTests -T 1C -o || mvn clean package -DskipTests -T 1C

# Stage 2: Runtime
FROM eclipse-temurin:21-jre
WORKDIR /app

# Download blockhound jar for agent
ADD https://repo1.maven.org/maven2/io/projectreactor/tools/blockhound/1.0.16.RELEASE/blockhound-1.0.16.RELEASE.jar blockhound.jar

# Copy the built jar from the build stage
COPY --from=build /app/zombie-listener/target/zombie-listener-*.jar app.jar

EXPOSE 10080

ENTRYPOINT ["java", "-XX:+AllowRedefinitionToAddDeleteMethods", "-javaagent:blockhound.jar", "--enable-preview", "-jar", "app.jar"]
