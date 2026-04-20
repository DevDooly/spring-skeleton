# Stage 1: Build
FROM eclipse-temurin:25-jdk AS build
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
RUN ./mvnw clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:25-jre
WORKDIR /app

# Copy the built jar from the build stage
# Assuming we are deploying zombie-listener
COPY --from=build /app/zombie-listener/target/zombie-listener-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
