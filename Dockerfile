FROM openjdk:21

WORKDIR /app

COPY target/ServerAdd-0.0.1-SNAPSHOT.jar ServerAdd.jar

CMD ["java", "-jar", "ServerAdd.jar"]