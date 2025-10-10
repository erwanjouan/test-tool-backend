FROM eclipse-temurin:17.0.17_10-jre-alpine-3.23
WORKDIR /app
COPY target/*.jar /app/app.jar
ENV SPRING_PROFILES_ACTIVE="h2"
CMD ["java", "-jar", "/app/app.jar"]