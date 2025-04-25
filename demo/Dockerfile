FROM openjdk:23-jdk-slim
LABEL authors="yozerpp"
WORKDIR /work
COPY target/*.jar /work/app.jar

EXPOSE 30080
EXPOSE 30051
EXPOSE 30055
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:30055","-jar", "/work/app.jar"]