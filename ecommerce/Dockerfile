FROM openjdk:23-jdk-slim
LABEL authors="yozerpp"
WORKDIR /app
COPY target/*.jar /app/app.jar
EXPOSE 8080
EXPOSE 5001
CMD ["java", "-jar", "app.jar"]