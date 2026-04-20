# Stage 1: Build
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY avro-lib/pom.xml avro-lib/
COPY common/pom.xml common/
COPY core/pom.xml core/
COPY zombie-listener/pom.xml zombie-listener/
COPY utils/pom.xml utils/

# Copy source code
COPY avro-lib/src avro-lib/src
COPY common/src common/src
COPY core/src core/src
COPY zombie-listener/src zombie-listener/src
COPY utils/src utils/src

# Build the project
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built jar from the build stage
# Assuming we are deploying zombie-listener
COPY --from=build /app/zombie-listener/target/zombie-listener-*.jar app.jar

EXPOSE 10080

ENTRYPOINT ["java", "--enable-preview", "-jar", "app.jar"]
