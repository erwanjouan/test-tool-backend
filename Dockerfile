FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY target/*.jar /app/app.jar
ENV SPRING_PROFILES_ACTIVE="h2"
CMD ["java", "-jar", "/app/app.jar"]