FROM openjdk:24-jdk-slim
LABEL authors="yozerpp"
WORKDIR /work
COPY target/*.jar /work/app.jar

EXPOSE 8080
EXPOSE 5005
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005","-jar", "/work/app.jar"]