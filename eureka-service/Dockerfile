# ─── Stage 1: Build ──────────────────────────────────────────
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /build

# Cache parent pom dependencies first
COPY pom.xml ./
COPY eureka-service/pom.xml eureka-service/
RUN mvn dependency:go-offline -pl eureka-service -am -q

# Copy source and build
COPY eureka-service/src eureka-service/src
RUN mvn clean package -pl eureka-service -am -DskipTests -q

# ─── Stage 2: Runtime ────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

COPY --from=builder /build/eureka-service/target/eureka-service-*.jar app.jar
RUN chown appuser:appgroup app.jar

USER appuser

EXPOSE 8761

ENV JAVA_OPTS="-Xms128m -Xmx256m -XX:+UseContainerSupport"

HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
  CMD wget -qO- http://localhost:8761/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
