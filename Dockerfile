# ── Stage 1: extract Spring Boot layers from the fat JAR ─────────────────────
FROM eclipse-temurin:25-jre-alpine AS builder
WORKDIR /build
COPY target/*.jar app.jar
RUN java -Djarmode=tools extract --destination extracted

# ── Stage 2: minimal runtime image ───────────────────────────────────────────
FROM eclipse-temurin:25-jre-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring
WORKDIR /app

# Dependency JARs change rarely → cached layer on most rebuilds
COPY --from=builder --chown=spring:spring /build/extracted/lib/ lib/
# Thin application JAR changes on every build → own layer at the top
COPY --from=builder --chown=spring:spring /build/extracted/app.jar ./

ENV SPRING_PROFILES_ACTIVE="h2"
ENTRYPOINT ["java", "-jar", "app.jar"]